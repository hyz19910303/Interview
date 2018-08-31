package com.huyz.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Create at 2018年8月27日 下午3:38:11
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
public class BlockQueue_Test {

	static volatile BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1);
	static CountDownLatch latch = new CountDownLatch(1);
	
	public static void main(String[] args) throws InterruptedException {
		providerAndConsumer();
	}
	static native void test();

	
	/*
	 * linkedBlockingDequen双向队列
	 */
	public static void deque() {
		LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<>(1);
	}
	
	/*
	 * 使用队列 实现生产消费者
	 */
	public static void providerAndConsumer() {
		new Thread(() ->{
			int index=0;
			while(true) {
				try {
//					Thread.sleep(50);
					String ele =index+"=> element";
					queue.put(ele);
					System.out.println("生产"+ele);
					index ++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"provider").start();
		new Thread(() ->{
			while(true) {
				try {
					Thread.sleep(200);
					String ele = queue.take();
					System.err.println("消费"+ele);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"consumer").start();
	}
	
	public static BlockingQueue<String> queue() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			// linkedBlockingQueue.add(i+"=>element");
			queue.put(i + "=>element");
		}
		// LinkedBlockingDeque<String> linkedBlockingDeque = new
		// LinkedBlockingDeque<String>(20);
		return queue;
	}

}
