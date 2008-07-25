package com.eris4.benchdb.core.monitor;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Monitors {
	
	private List<Monitor> monitors = new LinkedList<Monitor>();
	
	public  void add(Monitor monitor){
		monitors.add(monitor);
	}
	
	public void start(){
		for (Monitor monitor : monitors) {
			monitor.start();
			System.out.println(monitor.getDescription());
		}
	}
	
	public void stop(){
		for (Monitor monitor : monitors) {
			monitor.stop();
		}
	}

	public void printValue(PrintStream out) {
		out.println();
		for (Monitor monitor : monitors) {
			out.println(" "+monitor.getDescription()+": " + monitor.getFormattedValue());			
		}
		
	}
	
	public void update() {
		for (Monitor monitor : monitors) {
			monitor.update();
		}
	}

	public void warmUp() {
		for (Monitor monitor : monitors) {
			monitor.warmUp();
		}
	}	
	

}
