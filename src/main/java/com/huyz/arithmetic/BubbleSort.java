package com.huyz.arithmetic;

/**
 * Create at 2018年8月22日 上午10:12:19
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description: 冒泡排序
 *
 * 
 */
public class BubbleSort {

	public static void main(String[] args) {
		int[] array = new int[] { 2, 4, 7, 2, 3, 6, 32, 12, 42, 11, 7 };
		int[] sort = sort(array);
		for (int i : sort) {
			System.out.print(i+",");
		}
		System.out.println();
	}

	public static int[] sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i; j < array.length; j++) {
				if (array[i] > array[j]) {
					int max = array[i];
					array[i] = array[j];
					array[j] = max;
				}
			}
		}
		return array;
	}
}
