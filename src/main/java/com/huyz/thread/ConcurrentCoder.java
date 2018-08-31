package com.huyz.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create at 2018年8月10日 下午4:35:28
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName JavaThreadAndJUC
 *
 *          Description: juc包 下多线程测试 了解
 * 
 */
public class ConcurrentCoder {

	static ExecutorService threadPool = Executors.newFixedThreadPool(20);

	public static void concumerAndProviderBySemaphore() {
		Semaphore semaphore = new Semaphore(1);// 控制一次只能一个线程工作
		AtomicInteger val = new AtomicInteger(0);
		try {
			while (true) {
				new Thread(() -> {
					try {
						semaphore.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (val.get() > 0) {
						System.err.println("消费者");
						val.decrementAndGet();
					} else {
						System.err.println("生产者");
						val.incrementAndGet();
					}
					semaphore.release();
				}).start();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	/*
	 * 并发辅助类 闭锁 场景： 线程A需等待其他几个线程计算完结果 才可以进行下一步计算
	 */
	public static void concurrent_CountDownLatch() {
		final AtomicInteger value = new AtomicInteger(0);
		try {
			// 计数器 通过线程任务完成 当减至0 即可进行下一步
			final CountDownLatch await = new CountDownLatch(50);
			for (int i = 0; i < 50; i++) {
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						value.getAndIncrement();
						await.countDown();
					}
				});

			}
			await.await();
			System.err.println(value.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 并发辅助类 篱栅 和countdownlatch 技术相反 该类是当计数的次数达到指定值 触发。并且计数器是可重复用
	 * CyclicBarrier支持一个可选的Runnable命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），
	 * 该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作很有用
	 */
	public static void concurrent_CycicBarrier() {
		AtomicInteger val = new AtomicInteger(0);
		// new CyclicBarrier(10);
		CyclicBarrier barrier = new CyclicBarrier(20, () -> {
			// 回调函数 在一组线程中的最后一个线程到达之后（但在释放所有线程之前）触发
			// System.err.println("回调函数");
			System.err.println(val.get());
		});

		for (int i = 0; i < 20; i++) {
			threadPool.execute(() -> {
				try {
					val.incrementAndGet();
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}
		// 返回需要跨越此障碍的各方的数量。
		System.out.println(barrier.getParties());
	}

	/*
	 * 信号量 ：限制最大并行的线程数 场景： 3个坑 用完了 空出了一个，另外一个才能继续使用
	 */
	public static void concurrent_Semaphore() {
		// 信号量 代表最多多少线程同时执行
		Semaphore semaphore = new Semaphore(5);
		AtomicInteger val = new AtomicInteger(1);
		for (int i = 1; i <= 20; i++) {
			new Thread(() -> {
				int value = val.getAndIncrement();
				try {
					// semaphore.tryAcquire(val.getAndIncrement()%3+1, TimeUnit.SECONDS);
					semaphore.acquire();// 获取一个计数器
					// Thread.sleep(100);//睡眠
					// Thread.sleep((value%3+1)*1000);//睡眠
					System.err.println(value + "人占用第" + value % 3 + "坑");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.err.println(value + "人释放第" + value % 3 + "坑");
					semaphore.release();// 释放一个计数器
				}
			}).start();
		}
	}

	public static void main(String[] args) throws Exception {
//		for (int i = 0; i < 10; i++) {
//			//concurrent_CountDownLatch();
////			concurrent_CycicBarrier();
//		}
//		concurrent_Semaphore();
//		concumerAndProviderBySemaphore();

//		concurrent_Exchanger();
//		threadPool.shutdown();
		phaser_Test();
	}

	/*
	 * 阶段 任务 phaser
	 */
	public static void phaser_Test() {
		Phaser phaser = new Phaser(5) {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("阶段=》" + phase);
				System.err.println("注册数=》" + registeredParties);
				return super.onAdvance(phase, registeredParties);
			}
		};
//		phaser.register();
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				int oldregister = phaser.getRegisteredParties();
				int val = (int) Math.random() * 100;
				try {
					Thread.sleep(val);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("线程" + Thread.currentThread().getName() + "第一次数据库查询");
				phaser.arriveAndAwaitAdvance();
				synchronized (phaser) {
					phaser.register();
					int newregister = phaser.getRegisteredParties();
					if (oldregister != newregister) {
						for (; oldregister < newregister; oldregister++) {
							new Thread(() -> {
								// 新加的线程
								phaser.arriveAndAwaitAdvance();
								System.err.println("新起线程" + Thread.currentThread().getName() + "数据库查询");
							}, "newT" + oldregister).start();
						}
						oldregister = newregister;
					}
				}
				System.err.println("线程" + Thread.currentThread().getName() + "第二次数据库查询");
				// System.out.println("到达数：" + phaser.getArrivedParties());
				phaser.arriveAndAwaitAdvance();
			}, "T " + i).start();
		}
	}

	/*
	 * 线程之间交换数据 exchange.exchange(name1); 必须成对的出现
	 * 
	 */
	public void concurrent_Exchanger() throws InterruptedException {
		Exchanger<String> exchange = new Exchanger<>();
		new Thread(() -> {
			String name1 = "乔治";
			try {
				String exchange2 = exchange.exchange(name1);
				System.out.println(Thread.currentThread().getName() + "获得" + exchange2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "雷霆").start();

		new Thread(() -> {
			String name1 = "詹姆斯";
			try {
				String exchange2 = exchange.exchange(name1);
				System.out.println(Thread.currentThread().getName() + "获得" + exchange2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "湖人").start();

	}

}
