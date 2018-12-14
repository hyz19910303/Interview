package com.huyz.arithmetic;

/**
 * Create at 2018年8月27日 上午10:40:02
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description: 八皇后
 *          国际象棋中的皇后，可以横向、纵向、斜向移动。如何在一个8X8的棋盘上放置8个皇后，使得任意两个皇后都不在同一条横线、竖线、斜线方向上？
 *          如何遵循规则，同时放置这8个皇后呢?
 */
public class EightQueen {

	static final int MAX_NUM = 8;

	static int[][] CHESSBOARD = new int[MAX_NUM][MAX_NUM];
	
	/*
	 *  10000000
		00000010
		00001000
		00000001
		01000000
		00010000
		00000100
		00100000

	 */
	public static void main(String[] args) throws InterruptedException {
		setQueen(0);
		for (int i = 0; i < MAX_NUM; i++) {
			for (int j = 0; j < MAX_NUM; j++) {
				System.err.print(CHESSBOARD[i][j]+"\t");
			}
//			Thread.sleep(200);
			System.out.println();
		}
		
	}

	/**
	 * 
	 * <p>
	 * MethodName: setQueen
	 * </p>
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @param y
	 * @return
	 * 
	 * @author huyz
	 * @date 2018年8月27日
	 * @version 1.0
	 *
	 */
	public static boolean setQueen(int y) {
		// 行数超过8 找到答案
		if (y == MAX_NUM) {
			return true;
		}
		// 遍历当前行
		for (int i = 0; i < MAX_NUM; i++) {
			for (int x = 0; x < MAX_NUM; x++) {
				CHESSBOARD[x][y] = 0;// 设置初始值以及回溯时候避免脏数据
			}
			// 检查合法性
			if (check(i, y)) {
				// 如果合法 设置为1
				CHESSBOARD[i][y] = 1;
				if (setQueen(y + 1)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * 检查合法性
	 */
	// 值不是0表示落子
	public static boolean check(int x, int y) {
		for (int i = 0; i < y; i++) {
			// 检查行
			if (CHESSBOARD[x][i] != 0) {
				return false;
			}
			// 检测 右斜上
			// 下一行 x+1+i
			// 右一列 y-i-1
			if (x + 1 + i < MAX_NUM && CHESSBOARD[x + 1 + i][y - i - 1] != 0) {
				return false;
			}

			// 检查 左上
			if (x - i - 1 >= 0 && CHESSBOARD[x - i - 1][y - i - 1] != 0) {
				return false;
			}

		}
		return true;
	}
}
