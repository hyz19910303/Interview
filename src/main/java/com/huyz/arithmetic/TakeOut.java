package com.huyz.arithmetic;

/**
 * Create at 2018年8月31日 下午1:15:22
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName interview
 *
 *          Description:
 * 
 */
public class TakeOut {

	public static void main(String[] args) {

	}

	public static void take(int capacity, int out) {
		int[] array = initArray(capacity);
		int length = array.length;
		int num = 1;// 喊话人喊的数字
		while (length > 0) {
			if (array[num] == out) {

			}
		}
	}

	private static int[] initArray(int capacity) {
		int[] arr = new int[capacity];
		for (int i = 0; i < capacity; i++) {
			arr[i] = i + 1;
		}
		return arr;
	}
}
