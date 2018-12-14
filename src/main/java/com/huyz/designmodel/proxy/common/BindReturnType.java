package com.huyz.designmodel.proxy.common;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Create at 2018年8月30日 下午1:50:12
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
public class BindReturnType {

	public static Object bindInvokeResult(Class<?> interfance, Method method) {
		Object result = new Object();
		if (ReturnTypeResolver.returnsVoid(interfance, method)) {
			result = null;
		} else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
			result = 0;
		} else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
			result = 0L;
		} else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
			result = false;
		} else {
			throw new IllegalStateException("Mapper method '" + method.getName() + "' has an unsupported return type: "
					+ method.getReturnType());
		}
		return result;
	}

	public static Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
		final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
				.getDeclaredConstructor(Class.class, int.class);
		if (!constructor.isAccessible()) {
			constructor.setAccessible(true);
		}
		final Class<?> declaringClass = method.getDeclaringClass();
		return constructor
				.newInstance(declaringClass,
						MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE
								| MethodHandles.Lookup.PUBLIC)
				.unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
	}

	public static boolean isDefaultMethod(Method method) {
		return ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC)
				&& method.getDeclaringClass().isInterface();
	}
}
