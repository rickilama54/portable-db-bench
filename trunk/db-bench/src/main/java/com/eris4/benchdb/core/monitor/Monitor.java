package com.eris4.benchdb.core.monitor;

import java.util.LinkedList;
import java.util.List;

import com.eris4.benchdb.core.reporter.Reporter;

public abstract class Monitor {

	public void start(){
		notifyStartReporters();
		begin();
	}

	public abstract void reset();

	public abstract void begin();

	public void stop(){
		notifyStopReporters();
		end();
	}
	
	public abstract void end();

	public void update(){}
	
	public abstract long getValue();
	
	public abstract String getDescription();
	
	public void warmUp() {}
	
	private List<Reporter> reporters = new LinkedList<Reporter>();
	
	public void registerReporter(Reporter reporter){
		reporters.add(reporter);
	}
	
	public void notifyStartReporters(){
		for (Reporter reporter : reporters) {
			reporter.notifyStart();
		}
	}
	
	public void notifyStopReporters(){
		for (Reporter reporter : reporters) {
			reporter.notifyStop();
		}
	}
	
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

	

}
