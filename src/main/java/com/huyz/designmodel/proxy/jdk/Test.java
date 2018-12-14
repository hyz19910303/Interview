package com.huyz.designmodel.proxy.jdk;

import com.huyz.designmodel.proxy.common.TaskInterfance;

/**
 * Create at 2018年8月30日 上午10:48:01
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
		TaskInterfance newInstance = ProxyFactory.newInstance(TaskInterfance.class);
		System.out.println(newInstance);
		System.err.println(newInstance.doQueueTask());

	}

}
