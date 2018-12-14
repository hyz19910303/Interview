package com.huyz.list;

import java.util.List;

/**
 * Create at 2018年8月29日 下午5:01:47
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
public class Test {

	public static void main(String[] args) {
		List<String> li = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			li.add("ele " + i);
		}
		System.out.println(li.size());
		System.err.println(li.get(3));
		System.err.println(li);

//		System.out.println(3 >> 3);
	}

}
