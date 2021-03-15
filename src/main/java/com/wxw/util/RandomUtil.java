package com.wxw.util;
import java.util.Random;

/**
 * @author l
 */
public class RandomUtil {

	public static String randomFor6() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
	
}
