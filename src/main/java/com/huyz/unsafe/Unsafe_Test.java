package com.huyz.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Create at 2018年8月24日 上午10:18:31
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description:
 * 
 */
public class Unsafe_Test {

	public static void main(String[] args) throws Exception {
		// jDK10 提供
//		Unsafe uu = Unsafe.getUnsafe();

		Class<?> forName = Class.forName("sun.misc.Unsafe");
		Field field = forName.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		final Unsafe u = (Unsafe) field.get(null);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					u.park(false, 3000000000l);// 挂起
					System.out.println(222);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
//				u.park(false, 3000000000l);//挂起  纳秒
		System.err.println("success");

	}

}
