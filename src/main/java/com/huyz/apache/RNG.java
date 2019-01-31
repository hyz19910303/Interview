package com.huyz.apache;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

public class RNG {

	public static void main(String[] args) {
		int[] range = new int[] { 666, 888 };
		UniformRandomProvider rng = RandomSource.create(RandomSource.SPLIT_MIX_64, range);
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(rng.nextInt());
		}
	}
}
