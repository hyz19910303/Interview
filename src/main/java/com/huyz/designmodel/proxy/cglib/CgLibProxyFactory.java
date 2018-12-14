package com.huyz.designmodel.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Create at 2018年8月30日 下午1:21:40
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
public class CgLibProxyFactory {

	private CgLibProxyFactory() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T newMethodProxyInstance(Class<T> clazz) {
		ProxyMethod callBackHandler = new ProxyMethod();
		T create = (T) Enhancer.create(clazz, callBackHandler);
		return create;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInvocationInstance(Class<T> clazz) {
		ProxyHandler proxyHandler = new ProxyHandler();
		T create = (T) Enhancer.create(clazz, proxyHandler);
		return create;
	}
}
