package com.huyz.designmodel.proxy.cglib;

import com.huyz.designmodel.proxy.common.Task;
import com.huyz.designmodel.proxy.common.TaskInterfance;

/**
 * Create at 2018年8月30日 下午1:33:09
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
public class Test {

	public static void main(String[] args) {
		doInvocationProxy();
	}

	/*
	 * cglib 对类 代理
	 */
	public static void doMethodProxy() {
		Task newInstance = CgLibProxyFactory.newMethodProxyInstance(Task.class);
		newInstance.doQuery();
	}

	/*
	 * cglib 对接口增强
	 */
	public static void doInvocationProxy() {
		TaskInterfance newInstance = CgLibProxyFactory.newInvocationInstance(TaskInterfance.class);
//		newInstance.doQuery();
		System.out.println(newInstance.doQueueTask());
	}
}
