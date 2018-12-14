package com.huyz.arithmetic;

/**
 * Create at 2018年8月22日 上午10:53:48
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description: 插入排序
 * 
 */
public class InsertSort {

	public static void main(String[] args) {
		int[] array = new int[] { 2, 4, 7, 2, 3, 6, 32, 12, 42, 11, 7 };
		int[] sort = sort(array);
		for (int i : sort) {
			System.out.print(i + ",");
		}
		System.out.println();
	}

	public static int[] sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int index = i;
			int value = array[index];
			while (index > 0 && value < array[index - 1]) {
				array[index] = array[index - 1];
				index--;
			}
			array[index] = value;
		}
		return array;
	}

	@Deprecated
	public static int[] sort1(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int index = i;
			int value = array[index];
			while (index < array.length-1 && value < array[index + 1]) {
				array[index] = array[index+1];
				index++;
			}
			array[index] = value;
		}
		return array;
	}

}
