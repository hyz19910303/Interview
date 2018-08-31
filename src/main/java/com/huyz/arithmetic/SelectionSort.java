package com.huyz.arithmetic;

/**
 * Create at 2018年8月22日 上午10:31:13
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description: 选择排序
 * 
 */
public class SelectionSort {

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
			int index = i;
			for (int j = i; j < array.length; j++) {
				if(array[j]>array[index]) {
					index=j;
				}
			}
			if(i!=index) {
				int min=array[i];
				array[i]=array[index];
				array[index]=min;
			}
		}
		return array;
	}

}
