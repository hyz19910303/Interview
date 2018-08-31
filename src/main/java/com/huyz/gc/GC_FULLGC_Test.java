package com.huyz.gc;

import java.io.IOException;

/**
*Create at 2018年8月23日 下午3:05:38
*
*@autuor huyz
*
*@version 1.0
*
*ProjectName Interview
*
*Description: 
*        
*/
public class GC_FULLGC_Test {
		static Block block =null;
	//-XX:+PrintGC -XX:+PrintGCDetails  -XX:+PrintHeapAtGC
	public static void main(String[] args) throws IOException, InterruptedException {
		block = new Block();	
		block=null;
		System.gc();
		Thread.sleep(1000);//垃圾回收线程优先级低 等待1秒 执行垃圾回收线程
		if(block!=null) {
			System.err.println("yell  i'm still alive");
		}else {
			System.err.println("i'm  dead");
		}
		
		block=null;
		System.gc();
		Thread.sleep(1000);//垃圾回收线程优先级低 等待1秒 执行垃圾回收线程
		if(block!=null) {
			System.err.println("yell  i'm still alive");
		}else {
			System.err.println("i'm  dead");
		}
	}
	
	
	
	
	
	static class Block {
		byte[] memory = new byte[200*1024*1024];
		
		//此方法指挥被执行一次
		@Override
		protected void finalize() throws Throwable {
			System.err.println("垃圾回收");
			block= this;//自救 
		}
	}

}
