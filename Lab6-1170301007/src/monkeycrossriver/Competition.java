package monkeycrossriver;

import crossstrategy.CrossContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Competition {

  private final List<Monkey> monkeys = Collections.synchronizedList(new ArrayList<Monkey>());
  private final Set<Monkey> waitingMonkeys = Collections.synchronizedSet(new HashSet<Monkey>());
  private final Ladder[] ladders;
  private int laddersNum = 0;
  private int rungsNum = 0;
  private int total = 0;

  private static final boolean VISUALIZE_WAITING = false; // waiting monkeys visualizing trigger
  private static Logger logger = Logger.getLogger("cross log");

  private static final int FRAME_LENGTH = 1400;
  private static final int FRAME_HEIGHT = 800;

  private static final String N_REGEX = "n=([0-9]+)";
  private static final String H_REGEX = "h=([0-9]+)";
  private static final String MONKEY_REGEX = "monkey=<(\\d+),(\\d+),(R->L|L->R),(\\d+)>";

  // Representation invariant:
  // - laddersNum should be a natural number
  // - rungsNum should be a natural number
  // - ladders' length should be laddersNum
  // Abstract function:
  // - AF(monkeys) = synchronized list of all monkeys
  // - AF(waitingMonkeys) = synchronized set of all waiting monkeys who has been born
  // - AF(ladders) = an array of Ladder instances which preserve every monkey's position on each
  // ladder
  // - AF(laddersNum) = the number of ladders
  // - AF(rungsNum) = the number of rungsNum on each ladder
  // Safety from rep exposure:
  // - All reps are declared as private.
  // - No observers or mutators.
  // Thread safety argument:
  // - monkeys is a final synchronized list
  // - waitingMonkeys is a final synchronized set
  // - ladders is a final array
  // - laddersNum and rungsNum are int type.
  // - laddersNum and rungsNum are not final. But they are initialized in the constructor and never
  // be mutated somewhere else.
  /**
   * Constructor. Construct a Competition instance from the file.
   * 
   * @param file competition data file
   */
  public Competition(File file) {
    Locale.setDefault(new Locale("en", "EN"));
    logger.setLevel(Level.INFO);
    File log = new File("log/crossLog.log");
    if (log.exists()) {
      log.delete();
    }
    LogHandler.write(logger, "Cross Begin.");
    FileReader reader = null;
    try {
      reader = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    BufferedReader br = new BufferedReader(reader);
    String line = "";
    Pattern pattern1;
    Pattern pattern2;
    Matcher matcher1;
    Matcher matcher2;
    try {
      while ((line = br.readLine()) != null) {
        pattern1 = Pattern.compile(N_REGEX);
        matcher1 = pattern1.matcher(line);
        while (matcher1.find()) {
          this.laddersNum = Integer.valueOf(matcher1.group(1));
        }
        pattern2 = Pattern.compile(H_REGEX);
        matcher2 = pattern2.matcher(line);
        while (matcher2.find()) {
          this.rungsNum = Integer.valueOf(matcher2.group(1));
        }
        if (this.laddersNum > 0 && this.rungsNum > 0) {
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.ladders = new Ladder[this.laddersNum];
    for (int i = 0; i < this.laddersNum; i++) {
      this.ladders[i] = new Ladder(this.rungsNum + 1);
    }
  }

  /**
   * Read from the competition file. Create monkey threads according to the file data. Let monkeys
   * choose ladders according to crossContext strategy.
   * 
   * @param file         the competition file
   * @param crossContext crossing strategy context
   */
  public synchronized void readFromFile(File file, CrossContext crossContext) {
    FileReader reader = null;
    try {
      reader = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    BufferedReader br = new BufferedReader(reader);
    String line = "";
    Pattern pattern;
    Matcher matcher;
    long start = System.currentTimeMillis();
    try {
      while ((line = br.readLine()) != null) {
        pattern = Pattern.compile(MONKEY_REGEX);
        matcher = pattern.matcher(line);
        while (matcher.find()) {
          Monkey monkey = new Monkey(Integer.valueOf(matcher.group(2)),
              Integer.valueOf(matcher.group(4)), matcher.group(3));
          this.total++;
          int delay = Integer.valueOf(matcher.group(1));
          this.monkeys.add(monkey);
          CrossExecute crossExecute = new CrossExecute(ladders, monkey, crossContext, this.monkeys,
              this.waitingMonkeys, delay);
          crossExecute.start();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (this.monkeys.size() != this.total) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    long end = System.currentTimeMillis();
    logger.setUseParentHandlers(true);
    double runtime = ((double) (end - start)) / 1000;
    LogHandler.write(logger, "Throughput: " + (this.total / runtime));
    int fairSum = 0;
    for (int i = 0; i < total - 1; i++) {
      for (int j = i + 1; j < total; j++) {
        long a = this.monkeys.get(i).getArrived() - this.monkeys.get(j).getArrived();
        long b = this.monkeys.get(i).getBorn() - this.monkeys.get(j).getBorn();
        fairSum += a * b >= 0 ? 1 : -1;
      }
    }
    double fairIndex = ((double) fairSum) / (((double) total * (total - 1)) / 2);
    LogHandler.write(logger, "Fair index: " + fairIndex);
  }

  /**
   * Monkey crossing GUI.
   */
  public synchronized void visualize() {
    JFrame frame = new JFrame("Monkey Crossing GUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new VisualPanel());
    frame.setSize(FRAME_LENGTH, FRAME_HEIGHT);
    frame.setVisible(true);
  }

  private class VisualPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int DELAY_MS = 20;

    private static final int POSITION_SIZE = 30;

    private static final int ID_DISTANCE = 15;

    private static final int SPEED_DISTANCE = 25;

    private static final int DIRECTION_DISTANCE = 5;

    private static final int PARAMETER_DISTANCE = 100;

    private static final int CENTRAL_SIDE_BLANK = 200;

    private static final int CENTRAL_UPDOWN_BLANK = 50;

    private static final int STATISTICS_HEIGHT = 70;

    private static final int STATISTICS_LEFT_BLANK = 200;

    private static final int UPPER_STATISTICS_Y = 50;

    private static final int BOTTOM_STATISTICS_Y = FRAME_HEIGHT - UPPER_STATISTICS_Y - 20;

    private long startTime;

    javax.swing.Timer timer;

    /**
     * Constructor. In this constructor, a timer is set up and startTime is initialize which can be
     * used to calculate how long it has passed in this simulation. Besides, a panel is created for
     * showing the animation. And start time is recorded for calculating running time.
     * 
     * @param c a stellar system
     */
    VisualPanel() {
      timer = new javax.swing.Timer(DELAY_MS, new VisualListener());
      JPanel panel = new JPanel();
      add(panel);
      timer.start();
      startTime = System.currentTimeMillis();
    }

    /**
     * Every time repaint() method is called, so is this method. It is used to draw all planets'
     * position and tracks in the current time.
     */
    public void paintComponent(Graphics canvas) {
      super.paintComponent(canvas);
      // DecimalFormat df = new DecimalFormat("######0");
      canvas.setColor(Color.BLACK);
      canvas.setFont(new Font("Consolas", Font.BOLD, 30));
      canvas.drawString("n=" + Competition.this.laddersNum, 200, 50);
      canvas.drawString("h=" + Competition.this.rungsNum, 200 + PARAMETER_DISTANCE * 1, 50);
      canvas.drawString("N=" + Competition.this.monkeys.size(), 200 + PARAMETER_DISTANCE * 2, 50);
      Image monkeyImage = null;
      try {
        monkeyImage = ImageIO.read(new File("monkey.jpg"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      canvas.setFont(new Font("Consolas", Font.BOLD, 16));
      synchronized (Competition.this.ladders) {
        /* Waiting monkeys visualize */
        if (VISUALIZE_WAITING) {
          int waitingDrawI = 0;
          int waitingDrawJ = 0;
          for (Monkey waitingMonkey : Competition.this.waitingMonkeys) {
            if (waitingMonkey.getDirection().equals("L->R")) {
              canvas.drawImage(monkeyImage, 20 - POSITION_SIZE / 2 + waitingDrawJ * POSITION_SIZE,
                  100 - POSITION_SIZE / 2 + waitingDrawI * POSITION_SIZE * 2, POSITION_SIZE,
                  POSITION_SIZE, this);
              canvas.drawString(String.valueOf(waitingMonkey.getId()),
                  20 - POSITION_SIZE / 2 + waitingDrawJ * POSITION_SIZE,
                  100 - POSITION_SIZE / 2 + waitingDrawI * POSITION_SIZE * 2 + POSITION_SIZE + 10);
            }
            if (waitingMonkey.getDirection().equals("R->L")) {
              canvas.drawImage(monkeyImage, 1220 - POSITION_SIZE / 2 + waitingDrawJ * POSITION_SIZE,
                  100 - POSITION_SIZE / 2 + waitingDrawI * POSITION_SIZE * 2, POSITION_SIZE,
                  POSITION_SIZE, this);
              canvas.drawString(String.valueOf(waitingMonkey.getId()),
                  1220 - POSITION_SIZE / 2 + waitingDrawJ * POSITION_SIZE,
                  100 - POSITION_SIZE / 2 + waitingDrawI * POSITION_SIZE * 2 + POSITION_SIZE + 10);
            }
            waitingDrawJ++;
            if (waitingDrawJ % 5 == 0) {
              waitingDrawI++;
              waitingDrawJ = 0;
            }
          }
        }
        for (int i = 0; i < Competition.this.laddersNum; i++) {
          Monkey[] monkeysInLadder = Competition.this.ladders[i].getLadder();
          for (int j = 1; j <= Competition.this.rungsNum; j++) {
            int x = CENTRAL_SIDE_BLANK + (FRAME_LENGTH - CENTRAL_SIDE_BLANK * 2)
                / (Competition.this.rungsNum - 1) * (j - 1);
            int y = STATISTICS_HEIGHT + CENTRAL_UPDOWN_BLANK
                + (FRAME_HEIGHT - STATISTICS_HEIGHT * 2 - CENTRAL_UPDOWN_BLANK * 2)
                    / (Competition.this.laddersNum - 1) * i;
            if (monkeysInLadder[j] == null) {
              canvas.drawOval(x - POSITION_SIZE / 2, y - POSITION_SIZE / 2, POSITION_SIZE,
                  POSITION_SIZE);
            } else if (monkeysInLadder[j].getDirection().equals("L->R")) {
              canvas.drawImage(monkeyImage, x - POSITION_SIZE / 2, y - POSITION_SIZE / 2,
                  POSITION_SIZE, POSITION_SIZE, this);
              canvas.drawString(String.valueOf(monkeysInLadder[j].getId()), x,
                  y + POSITION_SIZE / 2 + ID_DISTANCE);
              canvas.drawString("v= " + String.valueOf(monkeysInLadder[j].getSpeed()), x,
                  y + POSITION_SIZE / 2 + SPEED_DISTANCE);
              canvas.drawString("->>", x, y - POSITION_SIZE / 2 - DIRECTION_DISTANCE);
            } else {
              x = CENTRAL_SIDE_BLANK + (FRAME_LENGTH - CENTRAL_SIDE_BLANK * 2)
                  / (Competition.this.rungsNum - 1) * (Competition.this.rungsNum - j);
              canvas.drawImage(monkeyImage, x - POSITION_SIZE / 2, y - POSITION_SIZE / 2,
                  POSITION_SIZE, POSITION_SIZE, this);
              canvas.drawString(String.valueOf(monkeysInLadder[j].getId()), x,
                  y + POSITION_SIZE / 2 + ID_DISTANCE);
              canvas.drawString("v= " + String.valueOf(monkeysInLadder[j].getSpeed()), x,
                  y + POSITION_SIZE / 2 + SPEED_DISTANCE);
              canvas.drawString("<<-", x, y - POSITION_SIZE / 2 - DIRECTION_DISTANCE);
            }
          } // END for
        } // END for
      }
      canvas.setFont(new Font("Consolas", Font.BOLD, 25));
      if (Competition.this.monkeys.size() != Competition.this.total) {
        long end = System.currentTimeMillis();
        double runtime = ((double) (end - startTime)) / 1000;
        int total = Competition.this.monkeys.size();
        canvas.drawString("Throughput: " + (total / runtime), STATISTICS_LEFT_BLANK,
            BOTTOM_STATISTICS_Y);
        int fairSum = 0;
        for (int i = 0; i < total - 1; i++) {
          for (int j = i + 1; j < total; j++) {
            long a = Competition.this.monkeys.get(i).getArrived()
                - Competition.this.monkeys.get(j).getArrived();
            long b = Competition.this.monkeys.get(i).getBorn()
                - Competition.this.monkeys.get(j).getBorn();
            fairSum += a * b >= 0 ? 1 : -1;
          }
        }
        double fairIndex = ((double) fairSum) / (((double) total * (total - 1)) / 2);
        canvas.drawString("Fair index: " + fairIndex, STATISTICS_LEFT_BLANK + FRAME_LENGTH / 2,
            BOTTOM_STATISTICS_Y);
        this.timer.stop();
      }
    }

    /**
     * VisualListener implements ActionListener.
     * 
     * @author Shen
     *
     */
    private class VisualListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    }
  }
}
