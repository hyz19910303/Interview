package com.huyz.classload;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Create at 2018年8月29日 下午2:20:22
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName interview
 *
 *          Description: 自定义加载器 打破双亲委派 重写 loadClass 和 findClass;不打破 就重写
 *          findClass
 * 
 */
public class Custom_ClassLoad {

	public static void main(String[] args) {

		try {
			ClassLoader classLoader = new ClassLoader() {

				@Override
				protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
					synchronized (getClassLoadingLock(name)) {
						Class<?> c = findLoadedClass(name);
						if (c == null) {
							if ("com.huyz.classload.Custom_ClassLoad".equals(name)) {
								c = findClass(name);
							} else {
								c = super.loadClass(name, resolve);
							}
						}
						if (resolve) {
							resolveClass(c);
						}
						return c;
					}
				}

				@Override
				protected Class<?> findClass(String name) throws ClassNotFoundException {
					FileInputStream is = null;
					try {
						String pathname = name.replaceAll("\\.", "/");
						String basepath = "E:/WorkSpaces/workspace01/interview/target/classes";
						String path = basepath + "/" + pathname + ".class";
						is = new FileInputStream(path);
						// InputStream is = getClass().getResourceAsStream(filename);
						byte[] byte1 = new byte[is.available()];
						is.read(byte1);
						return defineClass(name, byte1, 0, byte1.length);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException();
					} finally {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			String filepath = "E:/WorkSpaces/workspace01/interview/target/classes/com/huyz/classload/Custom_ClassLoad.class";
			filepath = "com.huyz.classload.Custom_ClassLoad";
			Object newInstance2 = classLoader.loadClass(filepath).newInstance();
			System.out.println(newInstance2.getClass().getClassLoader());
			System.err.println(newInstance2 instanceof Custom_ClassLoad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 打破双亲委派 重写 loadClass 和 findClass;不打破 就重写 findClass
	 */
	public static class MyClassLoad extends ClassLoader {

	}

}
