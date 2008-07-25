package com.eris4.benchdb.core.reporter;

import org.jfree.data.xy.XYSeries;

import com.eris4.benchdb.core.monitor.Monitor;

class GraphLine {

	private Monitor xAxis;
	private Monitor yAxis;
	private XYSeries xySeries;
	private double xValue = -1;
	private double yValue = -1;
	private String name;
	
	public GraphLine(String name,Monitor xAxis,Monitor yAxis){
		this.name = name;
		xySeries = new XYSeries(name,false,true);
		this.xAxis = xAxis;
		this.yAxis = yAxis;	
	}
	
	public void registerCoord(){
		yValue = new Long(yAxis.getValue()).doubleValue();
		xValue = new Long(xAxis.getValue()).doubleValue();
		xySeries.add(xValue,yValue);
	}
	
	public XYSeries getXYSeries(){
		return xySeries;
	}

	public void reset() {
		xySeries = new XYSeries(name,false,true);
	}
		
}
