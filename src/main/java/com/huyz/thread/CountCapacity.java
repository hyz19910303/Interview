package com.huyz.thread;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Create at 2018年8月21日 上午9:43:58
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description: 多线程统计 每个盘的大小 并汇总 3个线程统计各自盘符下 然后交给 第四个线程 统计
 * 
 */
public class CountCapacity {

	static ExecutorService threadpool = Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws Exception {
//		LocalDateTime begin = LocalDateTime.now();
//		test();
		LocalDateTime end = LocalDateTime.now();
//		long between = ChronoUnit.SECONDS.between(begin, end);
//		System.out.println("多线程耗时："+between);  //52s  => 59945773530
														//  59946271201
		// 2
		String[] p = new String[] { "C:/", "D:/", "E:/" };
		long val = 0;
		for (String string : p) {
			val = val + count(new File(string));
		}
		LocalDateTime endsed = LocalDateTime.now();
		long between2 = ChronoUnit.SECONDS.between(end, endsed);
		System.out.println("单线程耗时" + between2 + "=>" + val);// 63=>59946271201
	}

	public static void test() throws InterruptedException {
		ExecutorService threadpool = Executors.newFixedThreadPool(4);
		Exchanger<Long> exchange = new Exchanger<>();
		AtomicLong longValue = new AtomicLong(0);
		CyclicBarrier barrir = new CyclicBarrier(3, () -> {
//		LocalDateTime end = LocalDateTime.now();
//		long between = ChronoUnit.SECONDS.between(begin, end);
//		System.out.println("多线程耗时：" + between); // 52s => 59945773530
		// 59946271201
		// 2
//		LocalDateTime start = LocalDateTime.now();
//		String[] p = new String[] { // "C:/",
//				"D:/", "E:/" };
//		long val = 0;
//		for (String string : p) {
//			val = val + count(new File(string));
//		}
//		LocalDateTime endsed = LocalDateTime.now();
//		long between2 = ChronoUnit.SECONDS.between(start, endsed);
//		System.out.println("单线程耗时" + between2 + "=>" + val);// 63=>59946271201
		});
	}

	/*
	 * 使用Phaser 分段统计 各个磁盘的大小
	 */
	public static void parse_countCapaicty() throws Exception {
		final String[] diskFiles = new String[] { "E:\\Repositorys", "E:\\项目资料", "E:\\资料" };
		AtomicLong longValue = new AtomicLong(0);
		// 启动时候：：三个注册数
		Phaser phaser = new Phaser(diskFiles.length) {
			// 每完成一个阶段 调用一次
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.err.println("阶段" + phase + "的容量" + longValue.get());
				return super.onAdvance(phase, registeredParties);
			}
		};

		for (int i = 0; i < diskFiles.length; i++) {
			final int diskIndex = i;
			new Thread(() -> {
				System.out.println("线程启动完毕");
				phaser.arriveAndAwaitAdvance();// 阶段0结束 线程全部启动完毕 进入阻塞状态 等待下一阶段
				// 进入阶段1
				// 进行计算 磁盘容量
				long capacity = count(new File(diskFiles[diskIndex]));
				System.out.println(diskFiles[diskIndex] + "::" + capacity);
				longValue.getAndAdd(capacity);
				phaser.arrive();
			}, diskFiles[i] + "盘线程").start();
		}

	}


	/**
	 * 
	 * <p>
	 * MethodName: count
	 * </p>
	 * <p>
	 * 描述: 统计磁盘使用的大小
	 * </p>
	 * <tt>huyz<tt>
<<<<<<< HEAD
	 * 
	 * @param p 盘符
	 * 
	 * @author huyz
	 * @date 2018年8月21日
	 * @version 1.0
	 *
	 */
	public static long count(File p) {
		long value = 0;
		try {
			if (p.isFile()) {
				long length = p.length();
				value = value + length;
				return value;
			} else {
				File[] filelist = p.listFiles();
				if (filelist != null) {
					for (File filename : filelist) {
						long count = count(filename);
						value = value + count;
					}
				}
				return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new IllegalAccessError();
	}
}
