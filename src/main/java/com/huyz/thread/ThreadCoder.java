package com.huyz.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create at 2018年8月10日 下午3:35:38
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName JavaThreadAndJUC
 *
 *          Description:
 * 
 */
public class ThreadCoder {

	public static void main(String[] args) throws Exception {
//		threadYield();
<<<<<<< HEAD
//		threadJoin();
=======
		threadJoin();
>>>>>>> 1c9ea9d047c123139497e75ad3eefebb6213aba3
//		thread_multi_start();
//		threadSleep();
//		Thread_Circle_Print();
//		ThreadAndProductAndConsumerWithAtomatic();
<<<<<<< HEAD
		ThreadAndProductAndConsumerWithWaitNotify();
=======
//		ThreadAndProductAndConsumerWithWaitNotify();
>>>>>>> 1c9ea9d047c123139497e75ad3eefebb6213aba3
	}

	public static void thread_multi_start() {
		Thread thread = new Thread(() -> {
			System.out.println(11);
		}, "单线程");

		thread.start();// 两次启动 会异常
		thread.start();
	}

	/*
	 * thread.yield() 代表当前线程让步 本身有执行机会 但却放弃 重新进入 就绪状态 重新其他线程竞争
	 */
	public static void threadYield() throws InterruptedException {
		Runnable run = () -> {
			for (int i = 0; i < 50; i++) {
				System.out.println(Thread.currentThread().getName() + "->" + i);
				if (i == 30) {
					Thread.yield();
				}
			}
		};
		Thread thread1 = new Thread(run, "thread1");
		Thread thread2 = new Thread(run, "thread2");
		thread1.start();
		thread2.start();
	}

	/*
	 * join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
	 * 程序在main线程中调用thread1线程的join方法，则main线程放弃cpu控制权， 并返回thread1线程继续执行直到线程thread1执行完毕
	 * 所以结果是thread1线程执行完后，才到主线程执行，相当于在main线程中同步thread1线程，thread1执行完了，main线程才有执行的机会
	 */
	public static void threadJoin() throws InterruptedException {
		Runnable run = () -> {
			for (int i = 0; i < 50; i++) {
				System.err.println(Thread.currentThread().getName() + "->" + i);
			}
		};
		Thread thread1 = new Thread(run, "thread1");
		Thread thread2 = new Thread(run, "thread2");
		thread1.start();
		thread1.join();
		thread2.start();
		thread2.join();
		System.out.println("main");
	}

	/*
	 * sleep 和 sleep(0) 的区别 :sleep(0) 让系统重新分配cpu资源?
	 * 
	 */
	public static void threadSleep() throws InterruptedException {
		Thread thread = new Thread(() -> {
			System.out.println(1);
			try {
//				Thread.sleep(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Thread thread1 = new Thread(() -> {
			System.out.println(2);
		});
		thread.start();
		thread.setPriority(7);
		thread1.start();
		thread1.setPriority(5);

	}

	/*
	 * 线程循环打印
	 */
	public static void Thread_Circle_Print() {
		AtomicInteger index = new AtomicInteger(1);

		Thread thread1 = new Thread(() -> {
			for (;;) {
				if (index.get() == 1) {
					System.out.println(Thread.currentThread().getName() + "=> abc");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index.set(2);
				} else {
					Thread.yield();
				}
			}
		}, "1");
		Thread thread2 = new Thread(() -> {
			for (;;) {
				if (index.get() == 2) {
					System.out.println(Thread.currentThread().getName() + "=> abc");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index.set(3);
				} else {
					Thread.yield();
				}
			}
		}, "2");
		Thread thread3 = new Thread(() -> {
			for (;;) {
				if (index.get() == 3) {
					System.out.println(Thread.currentThread().getName() + "=> abc");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index.set(1);
				} else {
					Thread.yield();
				}
			}
		}, "3");
		thread1.start();
		thread2.start();
		thread3.start();

	}

	public static void ThreadAndProductAndConsumerWithAtomatic() {
		AtomicInteger index = new AtomicInteger(1);

		Thread product = new Thread(() -> {
			for (;;) {
				if (index.get() == 1) {
					System.out.println(Thread.currentThread().getName() + "=> product");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index.set(2);
				} else {
					Thread.yield();
				}
			}
		}, "product");
		Thread consumer = new Thread(() -> {
			for (;;) {
				if (index.get() == 2) {
					System.err.println(Thread.currentThread().getName() + "=> consumer");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index.set(1);
				} else {
					Thread.yield();
				}
			}
		}, "consumer");
		product.start();
		consumer.start();
	}

	static boolean flag = false;

	public static void ThreadAndProductAndConsumerWithWaitNotify() {
		Object obj = new Object();

		Thread product = new Thread(() -> {
			for (;;) {
				synchronized (obj) {
					if (!flag) {
						System.out.println(Thread.currentThread().getName() + "=> product");
						flag = true;
						obj.notifyAll();
					} else {
//						Thread.yield();
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "product");
		Thread consumer = new Thread(() -> {
			for (;;) {
				synchronized (obj) {
					if (flag) {
						System.err.println(Thread.currentThread().getName() + "=> consumer");
						flag = false;
						obj.notifyAll();
					} else {
						// Thread.yield();
						try {
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "consumer");
		product.start();
		consumer.start();
	}
}
