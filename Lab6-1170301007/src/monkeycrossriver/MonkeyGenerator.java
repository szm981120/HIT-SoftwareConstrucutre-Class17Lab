package monkeycrossriver;

import crossstrategy.CrossContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MonkeyGenerator {

  private final List<Monkey> monkeys = Collections.synchronizedList(new ArrayList<Monkey>());
  private final Set<Monkey> waitingMonkeys = Collections.synchronizedSet(new HashSet<Monkey>());
  private final Ladder[] ladders;
  private final int laddersNum;
  private final int rungsNum;
  private final int produceInterval;
  private final int monkeyNumProduce;
  private final int total;
  private final int maxSpeed;

  private static final boolean VISUALIZE_WAITING = false; // waiting monkeys visualizing trigger
  private static Logger logger = Logger.getLogger("cross log");

  private static final int FRAME_LENGTH = 1400;
  private static final int FRAME_HEIGHT = 800;

  // Representation invariant:
  // - laddersNum should be a natural number
  // - rungsNum should be a natural number
  // - produceInterval should be a natural number
  // - monkeyNumProduce should be a natural number
  // - total should be a natural number
  // - maxSpeed should be a natural number
  // - ladders' length should be laddersNum
  // Abstract function:
  // - AF(monkeys) = synchronized list of all monkeys
  // - AF(waitingMonkeys) = synchronized set of all waiting monkeys who has been born
  // - AF(ladders) = an array of Ladder instances which preserve every monkey's position on each
  // ladder
  // - AF(laddersNum) = the number of ladders
  // - AF(rungsNum) = the number of rungsNum on each ladder
  // - AF(produceInterval) = produce monkeyNumProduce monkeys per produceInterval seconds
  // - AF(monkeyNumProduce) = produce monkeyNumProduce monkeys per produceInterval seconds
  // - AF(total) = the number of all monkeys
  // - AF(maxSpeed) = the mas speed of all monkeys
  // Safety from rep exposure:
  // - All reps are declared as private and final.
  // - No observers or mutators.
  // Thread safety argument:
  // - monkeys is a final synchronized list
  // - waitingMonkeys is a final synchronized set
  // - ladders is a final array
  // - laddersNum, rungsNum, produceInterval, monkeyNumProduce, total and maxSpeed are int type
  // - Every time there's operation on ladders, it's synchronized
  /**
   * Constructor.
   * 
   * @param n        number of ladders
   * @param h        number of rungs in each ladder
   * @param t        action interval, unit is second
   * @param k        number of monkeys produced per t seconds
   * @param total    number of all monkeys
   * @param maxSpeed the max speed of all monkeys
   */
  public MonkeyGenerator(int n, int h, int t, int k, int total, int maxSpeed) {
    this.ladders = new Ladder[n];
    for (int i = 0; i < n; i++) {
      this.ladders[i] = new Ladder(h + 1);
    }
    this.laddersNum = n;
    this.rungsNum = h;
    this.produceInterval = t;
    this.monkeyNumProduce = k;
    this.total = total;
    this.maxSpeed = maxSpeed;
    Locale.setDefault(new Locale("en", "EN"));
    logger.setLevel(Level.INFO);
    File log = new File("log/crossLog.log");
    if (log.exists()) {
      log.delete();
    }
    LogHandler.write(logger, "Cross Begin.");
  }

  /**
   * Generate monkeyNumProduce monkeys per produceInterval seconds. As soon as a monkey is
   * generated, its thread activates, which means this monkey starts choosing ladder according to
   * strategy and cross the river, per produceInterval seconds.
   * 
   * @param crossContext cross strategy context
   */
  public synchronized void generate(CrossContext crossContext) {
    int duration = 1;
    int id = 1;
    Random random = new Random();
    long start = System.currentTimeMillis();
    while (duration * this.monkeyNumProduce <= total) {
      for (int i = 0; i < this.monkeyNumProduce; i++) {
        int speed = random.nextInt(this.maxSpeed) + 1;
        String direction = random.nextBoolean() ? "L->R" : "R->L";
        Monkey monkey = new Monkey(id, speed, direction);
        this.waitingMonkeys.add(monkey);
        CrossExecute crossExecute =
            new CrossExecute(ladders, monkey, crossContext, this.monkeys, this.waitingMonkeys, 0);
        crossExecute.start();
        id++;
      } // END for
      try {
        wait(this.produceInterval * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      duration++;
    } // END while
    try {
      wait(this.produceInterval * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    /* Generate left monkeys */
    int leftNum = this.total % this.monkeyNumProduce;
    for (int i = 0; i < leftNum; i++) {
      int speed = random.nextInt(this.maxSpeed) + 1;
      String direction = random.nextBoolean() ? "L->R" : "R->L";
      Monkey monkey = new Monkey(id, speed, direction);
      CrossExecute crossExecute =
          new CrossExecute(ladders, monkey, crossContext, this.monkeys, this.waitingMonkeys, 0);
      crossExecute.start();
      id++;
    }
    while (this.monkeys.size() != this.total) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    logger.setUseParentHandlers(true);
    long end = System.currentTimeMillis();
    double runtime = ((double) (end - start)) / 1000;
    LogHandler.write(logger, "Throughput: " + (this.total / runtime));
    int fairSum = 0;
    for (int i = 0; i < this.total - 1; i++) {
      for (int j = i + 1; j < this.total; j++) {
        long a = this.monkeys.get(i).getArrived() - this.monkeys.get(j).getArrived();
        long b = this.monkeys.get(i).getBorn() - this.monkeys.get(j).getBorn();
        fairSum += a * b >= 0 ? 1 : -1;
      }
    }
    double fairIndex = ((double) fairSum) / (((double) this.total * (this.total - 1)) / 2);
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

    private static final int DELAY_MS = 33;

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

    private static final int BOTTOM_STATISTICS_Y =
        MonkeyGenerator.FRAME_HEIGHT - UPPER_STATISTICS_Y - 20;

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
      canvas.drawString("n=" + MonkeyGenerator.this.laddersNum, 200, 50);
      canvas.drawString("h=" + MonkeyGenerator.this.rungsNum, 200 + PARAMETER_DISTANCE * 1, 50);
      canvas.drawString("t=" + MonkeyGenerator.this.produceInterval, 200 + PARAMETER_DISTANCE * 2,
          50);
      canvas.drawString("N=" + MonkeyGenerator.this.total, 200 + PARAMETER_DISTANCE * 3, 50);
      canvas.drawString("k=" + MonkeyGenerator.this.monkeyNumProduce, 200 + PARAMETER_DISTANCE * 4,
          50);
      canvas.drawString("MV=" + MonkeyGenerator.this.maxSpeed, 200 + PARAMETER_DISTANCE * 5, 50);
      Image monkeyImage = null;
      try {
        monkeyImage = ImageIO.read(new File("monkey.jpg"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      canvas.setFont(new Font("Consolas", Font.BOLD, 16));
      synchronized (MonkeyGenerator.this.ladders) {
        /* Waiting monkeys visualize */
        if (VISUALIZE_WAITING) {
          int waitingDrawI = 0;
          int waitingDrawJ = 0;
          for (Monkey waitingMonkey : MonkeyGenerator.this.waitingMonkeys) {
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
        for (int i = 0; i < MonkeyGenerator.this.laddersNum; i++) {
          Monkey[] monkeysInLadder = MonkeyGenerator.this.ladders[i].getLadder();
          for (int j = 1; j <= MonkeyGenerator.this.rungsNum; j++) {
            int x = CENTRAL_SIDE_BLANK + (MonkeyGenerator.FRAME_LENGTH - CENTRAL_SIDE_BLANK * 2)
                / (MonkeyGenerator.this.rungsNum - 1) * (j - 1);
            int y = STATISTICS_HEIGHT + CENTRAL_UPDOWN_BLANK
                + (MonkeyGenerator.FRAME_HEIGHT - STATISTICS_HEIGHT * 2 - CENTRAL_UPDOWN_BLANK * 2)
                    / (MonkeyGenerator.this.laddersNum - 1) * i;
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
              x = CENTRAL_SIDE_BLANK + (MonkeyGenerator.FRAME_LENGTH - CENTRAL_SIDE_BLANK * 2)
                  / (MonkeyGenerator.this.rungsNum - 1) * (MonkeyGenerator.this.rungsNum - j);
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
      if (MonkeyGenerator.this.monkeys.size() != MonkeyGenerator.this.total) {
        long end = System.currentTimeMillis();
        double runtime = ((double) (end - startTime)) / 1000;
        canvas.drawString("Throughput: " + (MonkeyGenerator.this.total / runtime),
            STATISTICS_LEFT_BLANK, BOTTOM_STATISTICS_Y);
        int fairSum = 0;
        for (int i = 0; i < MonkeyGenerator.this.total - 1; i++) {
          for (int j = i + 1; j < MonkeyGenerator.this.total; j++) {
            long a = MonkeyGenerator.this.monkeys.get(i).getArrived()
                - MonkeyGenerator.this.monkeys.get(j).getArrived();
            long b = MonkeyGenerator.this.monkeys.get(i).getBorn()
                - MonkeyGenerator.this.monkeys.get(j).getBorn();
            fairSum += a * b >= 0 ? 1 : -1;
          }
        }
        double fairIndex = ((double) fairSum)
            / (((double) MonkeyGenerator.this.total * (MonkeyGenerator.this.total - 1)) / 2);
        canvas.drawString("Fair index: " + fairIndex,
            STATISTICS_LEFT_BLANK + MonkeyGenerator.FRAME_LENGTH / 2, BOTTOM_STATISTICS_Y);
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
