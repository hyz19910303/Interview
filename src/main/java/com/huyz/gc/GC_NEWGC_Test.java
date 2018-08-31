package com.huyz.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * Create at 2018年8月24日 下午2:43:29
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description:
 * 
 */
public class GC_NEWGC_Test {

	// -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintHeapAtGC
	// XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:/dump.hprof
	public static void main(String[] args) throws InterruptedException {
		List<gc> list = new LinkedList<>();
//		Thread.sleep(10000);
		while (true) {
//			Thread.sleep(10);
			gc newGC = newGC();
			list.add(newGC);
		}

	}

	public static gc newGC() {
		gc gc = new gc();
		return gc;
	}

	static class gc {
		byte[] file = new byte[1024 * 100];

	}

}
