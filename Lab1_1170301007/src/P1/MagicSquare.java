package P1;

import java.io.*;
import java.util.ArrayList;

public class MagicSquare {

	public static void main(String[] args) {
		String fileName;
		for (int i = 1; i <= 6; i++) {
			fileName = "src/P1/txt/" + String.valueOf(i) + ".txt";
			boolean flag = isLegalMagicSquare(fileName);
			System.out.println(fileName + " " + flag);
		}
		generateMagicSquare(-1);
	}

	public static boolean isLegalMagicSquare(String fileName) {
		try {
			ArrayList<ArrayList<Integer>> square = new ArrayList<ArrayList<Integer>>();
			String line; // 读取每行
			int rowN = 0, columnN = 0; // 用来测试矩阵是否方阵，每行元素个数是否相等
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			/* 读取文件 */
			while ((line = br.readLine()) != null) {
				ArrayList<Integer> row = new ArrayList<Integer>();
				String[] splitedLine = line.split("\t");
				/* 判断是否\t分割 */
				for (String s : splitedLine) {
					if (s.contains(" ")) {
						System.out.println("ERROR! For " + fileName + ".There is a space!");
						br.close();
						return false;
					}
				}
				/* 判断是否为矩阵 */
				if (rowN != 0 && splitedLine.length != columnN) {
					System.out.println("ERROR! For " + fileName + ".The number of each row is not the same!");
					br.close();
					return false;
				}
				columnN = splitedLine.length; // 这里是用每行元素数去和上一行比较是否相等
				/* 接下来判断是否有非整数元素 */
				for (int i = 0; i < splitedLine.length; i++) {
					try {
						row.add(Integer.valueOf(splitedLine[i]));
					} catch (Exception e) {
						System.out.println("ERROR! For " + fileName + ".There is a non-integer number!");
						br.close();
						return false;
					}
				}

				square.add(row);	// 在读文件的过程中，没有问题，即可保存为一行
				rowN++; // 记录行数
			} // end while
			/* 判断是否为方阵 */
			if (rowN != columnN) {
				System.out.println("ERROR! For " + fileName + ".It's not a square!");
				br.close();
				return false;
			}
			/* 求和过程中的正确和值 */
			int rowRightSum = 0, colRightSum = 0, diaRightSum = 0;
			/* 求和过程中各行实际和值 */
			int rowSum = 0, colSum = 0, diaSum = 0;
			/* 判断各行和是否相等 */
			for (int i = 0; i < rowN; i++) {
				/* 出现不相等的时刻，直接否定幻方 */
				if (i > 0 && rowRightSum != rowSum) {
					System.out.println("ERROR! For " + fileName + ".The sum of each row is not the same!");
					br.close();
					return false;
				}
				rowSum = 0; // 和值归零
				/* 每行求和 */
				for (int j = 0; j < columnN; j++)
					rowSum += square.get(i).get(j);
				rowRightSum = rowSum;
			}
			/* 判断各列和是否相等 */
			for (int j = 0; j < columnN; j++) {
				/* 出现不相等的时刻，直接否定幻方 */
				if (j > 0 && colRightSum != colSum) {
					System.out.println("ERROR! For " + fileName + ".The sum of each column is not the same!");
					br.close();
					return false;
				}
				colSum = 0; // 和值归零
				/* 每列求和 */
				for (int i = 0; i < rowN; i++)
					colSum += square.get(i).get(j);
				colRightSum = colSum;
			}
			/* 判断对角线和是否相等 */
			for (int i = 0; i < rowN; i++) {
				diaRightSum += square.get(i).get(i);
				diaSum += square.get(rowN - i - 1).get(columnN - i - 1);
			}
			/* 如果不等，否定幻方 */
			if (diaRightSum != diaSum) {
				System.out.println("ERROR! For " + fileName + ".The sum of each diagonal is not the same!");
				br.close();
				return false;
			}
			
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean generateMagicSquare(int n) {
		/* 处理n为负数的异常 */
		if (n < 0) {
			System.out.println("The arraysize must be non-negative!");
			return false;
		}
		int magic[][] = new int[n][n]; // n阶方阵
		/* 初始化，第0行，取中间列，原代码中n取偶数或负数都会出现异常 */
		int row = 0, col = n / 2, i, j, square = n * n;
		/* 循环n*n次，向矩阵中添加元素 */
		for (i = 1; i <= square; i++) {
			/* 这里面用步长为1的循环来填充方阵，按下面的填充规律，可以实现一个magic square */
			/* 按下述if-else模式填充，填充模式是螺旋状的 */
			/* 处理n为偶数引发的索引越界异常 */
			try {
				magic[row][col] = i;
			} catch (Exception e) {
				System.out.println("There may be some index exception here!");
				return false;
			}
			/* 以n为步长，循环填充 */
			if (i % n == 0)
				row++;
			else { // 每轮填充过程中
				if (row == 0) // 行触底反弹，回到末行
					row = n - 1;
				else
					row--; // 行递减
				if (col == (n - 1)) // 列触顶反弹，回到首行
					col = 0;
				else
					col++; // 列递增
			}
		}

		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}

		/* 扩展：写入文件/src/P1/txt/6.txt */
		try {
			File newSquare = new File("src/P1/txt/6.txt");
			newSquare.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(newSquare));
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++) {
					writer.write(magic[i][j] + "\t");
				}
				writer.write("\n");
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
