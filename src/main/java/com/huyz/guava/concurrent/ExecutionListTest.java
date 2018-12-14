package com.huyz.guava.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Create at 2018年9月25日 下午2:42:31
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
public class ExecutionListTest {

	public static void main(String[] args) {
//		executionCallBack_ListenFuture();
		executionCallBack_Futures();
	}

	public static void executionCallBack_ListenFuture() {
		// 装饰
		ListeningExecutorService listeningDecorator = MoreExecutors
				.listeningDecorator(Executors.newSingleThreadExecutor());
		// 任务函数
		final ListenableFuture<String> listens = listeningDecorator.submit(() -> {
			System.err.println("回调函数：hello world");
			TimeUnit.SECONDS.sleep(5);
			return "hello world";
		});
		// ListenableFuture注册监听器,即异步调用完成时会在指定的线程池中执行注册的监听器的任务
		listens.addListener(() -> {
			System.out.println("注册监听器");
		}, Executors.newSingleThreadExecutor());

	}

	public static void executionCallBack_Futures() {
		// 装饰
		ListeningExecutorService listeningDecorator = MoreExecutors
				.listeningDecorator(Executors.newSingleThreadExecutor());
		// 任务函数
		final ListenableFuture<String> listens = listeningDecorator.submit(() -> {
			System.err.println("回调函数：hello world");
//			TimeUnit.SECONDS.sleep(3);
			throw new RuntimeException();
//			return "hello world";
		});
		// 添加回调
		Futures.addCallback(listens, new FutureCallback<String>() {

			@Override
			public void onSuccess(String result) {
				System.err.println("回调成功");
			}

			@Override
			public void onFailure(Throwable t) {
				System.err.println("回调失败");
			}

		});
	}
}
