package com.huyz.designmodel.proxy.cglib;

import java.lang.reflect.Method;

import com.huyz.designmodel.proxy.common.BindReturnType;

import net.sf.cglib.proxy.InvocationHandler;

/**
 * Create at 2018年8月30日 下午1:41:12
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
public class ProxyHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("interfacne");
		return BindReturnType.bindInvokeResult(method.getDeclaringClass(), method);
	}

}
