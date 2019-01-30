package com.huyz.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Create at 2018年9月12日 上午11:30:26
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
public class THreadPool {
	static ScheduledExecutorService threadpool = Executors.newScheduledThreadPool(1);
	static int index = 1;

	public static void main(String[] args) {

//		threadpool.schedule(() -> {
//			System.out.println(1);
//		}, 3, TimeUnit.SECONDS);

		threadpool.scheduleAtFixedRate(() -> {
			System.out.println("计划任务" + index++);

		}, 0, 2, TimeUnit.SECONDS);
	}
}
