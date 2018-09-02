package com.huyz.arithmetic;

/**
 * Create at 2018年9月2日 下午12:52:40
 *
 * @autuor EVIL
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description:
 * 
 */
public class TakeOut {

	public static void main(String[] args) {
		out(10, 3);
	}

	public static void out(int capacity, int out) {
		int[] initArray = initArray(capacity);
		int length = initArray.length;
		int takeNum = 1;// 喊话的数字1,2,3,1,2,3,1,2,3......
		int index = 0;// 喊话的位置0,1,2,3,4...<length
		while (length >= 0) {
			if (takeNum % out == 0) {
				int outval = initArray[index % length];
				for (int i = index; i < length - 1; i++) {
					initArray[i] = initArray[i + 1];
				}
				initArray[length - 1] = outval;
				System.out.println(outval);
				length--;
			}
			index++;
			if (takeNum == 3) {
				takeNum = 1;
			} else {
				takeNum++;
			}
		}

	}

	private static int[] initArray(int capacity) {
		int[] array = new int[capacity];
		for (int i = 0; i < capacity; i++) {
			array[i] = i + 1;
		}
		return array;
	}
}
