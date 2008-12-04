package com.eris4.benchdb.core.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.LinkedList;
import java.util.List;

import com.eris4.benchdb.core.util.ThreadUtils;

public class TotalCpuMonitor extends Monitor {
	
	private static final int PERIOD_CHECK = 300;
	private boolean stop;
	private int cpuUsage;
	private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	private long startIntervalTime;
	private long startIntervalUserTime;
	private List<ThreadMonitor> threadMonitors = new LinkedList<ThreadMonitor>();

	
	public void begin() {
		stop = false;
		long[] ids = threadMXBean.getAllThreadIds();
		threadMonitors = new LinkedList<ThreadMonitor>();
		for (long id : ids) {
			threadMonitors.add(new ThreadMonitor(id));
		}		
		new Thread(){

			public void run() {
				int tmp;
				while (!stop) {
					tmp = 0;
					for (ThreadMonitor threadMonitor : threadMonitors) {
						tmp += threadMonitor.getValue();
					}
					synchronized (this) {
						cpuUsage = tmp;
					}
					ThreadUtils.sleep(PERIOD_CHECK);
				}
			}
		}.start();
	}

	
	public void end() {
		stop = true;
	}

	
	public String getDescription() {
		return "Cpu usage";
	}
	
	

	
	public long getValue() {
		synchronized (this) {
			return cpuUsage;
		}
	}

	
	public void reset() {
		stop = false;
		cpuUsage = 0;
	}

}
