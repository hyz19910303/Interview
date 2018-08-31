package com.huyz.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Create at 2018年8月24日 上午10:54:10
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
public class RandomAccessFile_Test {

	// 设置堆大小 -Xms=30M -Xmx=30M
	public static void main(String[] args) {
		File file = new File("E:\\data\\logs\\debug.log");
		try {
			Map<String, Integer> readAndCount = readAndCount(file,"rw");
			System.out.println(readAndCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Integer> readAndCount(File file, String mode) throws IOException {
		Map<String, Integer> map = new HashMap<>();
		RandomAccessFile randomfile = new RandomAccessFile(file, mode);
		byte[] by = new byte[1024];
		int read = 0;
		try {
			while ((read = randomfile.read(by)) != -1) {
				String str = new String(by);
				String[] split = str.split(":");
				for (String string : split) {
					map.compute(string, (k, v) -> {
						if(v==null) {
							return 1;
						}
						return v + 1;
					});
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			randomfile.close();
		}
		return map;
	}
	
	

}
