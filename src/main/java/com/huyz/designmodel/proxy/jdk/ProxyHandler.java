package com.huyz.designmodel.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.huyz.designmodel.proxy.common.BindReturnType;

/**
 * Create at 2018年8月30日 上午10:19:23
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
	private Class<?> targetClazz;

	public ProxyHandler() {
	}

	public ProxyHandler(Class<?> target) {
		this.targetClazz = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (Object.class.equals(method.getDeclaringClass())) {
			return method.invoke(this, args);
		} else if (BindReturnType.isDefaultMethod(method)) {
			return BindReturnType.invokeDefaultMethod(proxy, method, args);
		}
		System.out.println("invoke");
		return BindReturnType.bindInvokeResult(method.getDeclaringClass(), method);

	}

}
