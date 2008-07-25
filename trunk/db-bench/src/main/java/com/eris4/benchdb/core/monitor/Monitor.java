package com.eris4.benchdb.core.monitor;

public abstract class Monitor {

	public void start(){}

	public void stop(){}
	
	public void update(){}

	
	public abstract long getValue();
	
	public abstract String getDescription();
	
	public String getFormattedValue() {
		String tmp = String.valueOf(getValue());		
		StringBuilder builder = new StringBuilder();		
		int length = tmp.length();
		for (int i = 1; i <= length; i++) {
			if (i > 1 && i % 3 == 1)
				builder.insert(0,'.');
			builder.insert(0, tmp.charAt(length - i));
			
		}
		return builder.toString();
	}

	public void warmUp() {}

}
