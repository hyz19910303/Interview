package com.huyz.thread;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
			try {
				Long exchange2 = exchange.exchange(longValue.get());
				System.err.println(exchange2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			threadpool.shutdown();
		});
		final String[] p = new String[] { "C:/", "D:/", "E:/" };
		for (String pName : p) {
			threadpool.execute(() -> {
				long useSpace = count(new File(pName));
				try {
//					System.out.println(useSpace);
					longValue.addAndGet(useSpace);
					barrir.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}
		Long exchange2 = exchange.exchange(0l);
		System.out.println("总的占用大小" + exchange2);
		threadpool.shutdown();
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
