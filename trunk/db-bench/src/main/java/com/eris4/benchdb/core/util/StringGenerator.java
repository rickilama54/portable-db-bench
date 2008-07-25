package com.eris4.benchdb.core.util;

import java.util.Random;

public class StringGenerator {
	
	private Random random;

	public StringGenerator(){
		this.random = new Random();
	}
	
	public String getRandomString(){
		return getRandomString(random.nextInt(20));
	}
	
	public String getRandomString(int length){
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = (char) random.nextInt(250);
			
		}
		return new String(chars);
	}

}
