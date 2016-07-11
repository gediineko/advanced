package com.test.advanced.util;
import java.util.Random;
public class RandomUtil{
	private static final int MIN = 33;
	private static final int MAX = 126;
	public static String generate(int length){
		String randomStr = "";
		Random rand = new Random();
		for (int x = 0; x < length; x++){
			randomStr += (char)(rand.nextInt((MAX - MIN) + 1) + MIN);	
		}
		return randomStr;
	}
}