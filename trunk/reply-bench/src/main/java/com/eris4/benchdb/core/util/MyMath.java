package com.eris4.benchdb.core.util;

public class MyMath {

	/*
	 * converte i millisecondi in secondi approssimando per eccesso
	 */
	public static long getMillisToNextSecond(long displayTime) {
		long seconds = displayTime/1000;
		if ((seconds*1000) < displayTime){
			seconds ++;
		}
		seconds *= 1000;
		return seconds-displayTime;
	}

}
