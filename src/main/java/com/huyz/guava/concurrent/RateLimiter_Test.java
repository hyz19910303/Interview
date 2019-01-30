package com.huyz.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Create at 2018年9月25日 下午3:31:48
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
public class RateLimiter_Test {

	public static void main(String[] args) {
		limit();
	}

	/*
	 * QPS RateLimiter.create(1) 每秒产生多少个
	 */
	public static void limit() {
		RateLimiter rateLimiter = RateLimiter.create(0.5);
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				double acquire = rateLimiter.acquire(1);
				System.out.println(acquire);
			}).start();
		}

	}

}
