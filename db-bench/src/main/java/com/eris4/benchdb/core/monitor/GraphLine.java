package com.eris4.benchdb.core.monitor;

import org.jfree.data.xy.XYSeries;

class GraphLine {

	private Monitor xAxis;
	private Monitor yAxis;
	private XYSeries xySeries;
	private double xValue = -1;
	private double yValue = -1;
	
	public GraphLine(String name,Monitor xAxis,Monitor yAxis){
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
		
}
