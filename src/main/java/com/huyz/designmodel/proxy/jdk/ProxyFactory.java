package com.huyz.designmodel.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * Create at 2018年8月30日 上午10:42:30
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
public class ProxyFactory {

	private ProxyFactory() {
	};

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> interfance) {
		ProxyHandler handler = new ProxyHandler();
		T proxyInstance = (T) Proxy.newProxyInstance(interfance.getClassLoader(), new Class[] { interfance }, handler);
		return proxyInstance;
	}

}
