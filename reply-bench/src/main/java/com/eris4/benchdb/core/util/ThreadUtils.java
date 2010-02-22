package com.eris4.benchdb.core.util;

public class ThreadUtils {
	
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// do nothing
		}
	}

}
