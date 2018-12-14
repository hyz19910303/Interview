package com.huyz.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

/**
 * Create at 2018年8月16日 下午3:54:29
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
public class ReentrantLock_Test {

	static ExecutorService service = Executors.newFixedThreadPool(20);

	static Integer account = new Integer(1000);
	
	public static void main(String[] args) {
		//reentrantLock();
		for (int i = 0; i < 1; i++) {
			reentrantReadWriteLock_Test();
		}
		System.out.println(account);
//		service.shutdown();
	}

	/*
	 * 重入锁
	 */
	public static void reentrantLock() {
		AtomicInteger val = new AtomicInteger(0);
		Semaphore semaphore = new Semaphore(1);
		for (int i = 0; i < 100; i++) {
			ReentrantLock lock = new ReentrantLock();
			Condition condition = lock.newCondition();
			service.execute(() -> {
				try {
//					lock.tryLock();
					lock.lock();
					semaphore.acquire();
					if (val.get() == 0) {
						System.out.println("生产");
						val.incrementAndGet();
					} else {
						System.err.println("消费");
						val.decrementAndGet();
					}
					Thread.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					semaphore.release();
					if (lock.isLocked()) {
						lock.unlock();
					}
				}
			});
		}
	}

	/*
	 * 读写锁 与数据库的读写分离类似
	 */
	public static void reentrantReadWriteLock_Test() {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		//AtomicInteger account = new AtomicInteger(1000);//代表一个账户
		
		new  Thread(()->{
			//读取账户  加锁
			lock.readLock().lock();
			try {
				Thread.sleep(3000);
				System.err.println(Thread.currentThread().getName()+"读取账户"+account);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//释放读锁
			lock.readLock().unlock();
		},"张三") .start();// 代表一个用户
		new  Thread(()->{
			//读取账户  加锁
			lock.readLock().lock();
			try {
				Thread.sleep(3000);
				System.err.println(Thread.currentThread().getName()+"读取账户"+account);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//释放读锁
			lock.readLock().unlock();
		},"李四") .start();// 代表一个用户
		new  Thread(()->{
			//存取账户  加锁
			lock.writeLock().lock();
			//System.err.println(Thread.currentThread().getName()+"读取账户"+account);
			System.err.println(Thread.currentThread().getName()+"存取"+1000);
			account=account+1000;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//释放写锁
			lock.writeLock().unlock();
		},"王五") .start();// 代表一个用户
		
	}
	
	
	
	
	
	
	
	

}
