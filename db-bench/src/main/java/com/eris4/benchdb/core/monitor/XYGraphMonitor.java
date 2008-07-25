package com.eris4.benchdb.core.monitor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import com.eris4.benchdb.core.util.ThreadUtils;

public class XYGraphMonitor extends Monitor {
	
	private boolean stop;
	private long PERIOD_CHECK = 90;	
	private String graphName;
	private List<GraphLine> list = new LinkedList<GraphLine>();
	private String xAxisLabel;
	private String yAxisLabel;	
	
	public XYGraphMonitor(String name,Monitor xAxis,Monitor yAxis){
		this.graphName = name;
		add(name,xAxis,yAxis);	
	}
	
	public XYGraphMonitor(String graphName){
		this.graphName = graphName;
	}
	
	public void add(String name,Monitor xAxis,Monitor yAxis){
		xAxisLabel = xAxis.getDescription();
		yAxisLabel = yAxis.getDescription();
		list.add(new GraphLine(name,xAxis,yAxis));
	}
	
	private void registerCoord() {
		for (GraphLine graph : list) {
			graph.registerCoord();
		}	
	}	
	
	public void begin(){
		stop = false;
		new Thread(){
			public void run() {
				while(!stop) {		
					registerCoord();	
					ThreadUtils.sleep(PERIOD_CHECK);									
				}
			}					
		}.start();
	}
	
	public void end(){
		stop = true;
	}	

	@Override
	public String getDescription() {
		return "Graph File";
	}

	@Override
	public long getValue() {		
		createChart();
		return 0;
	}
	
	@Override
	public String getFormattedValue() {
		createChart();
		return graphName+".png";
	}

	private void createChart() {
		try {
			XYSeriesCollection dataset = new XYSeriesCollection();
			for (GraphLine graph : list) {
				dataset.addSeries(graph.getXYSeries());
			}			
			JFreeChart chart = ChartFactory.createXYLineChart(graphName,xAxisLabel,yAxisLabel,dataset,PlotOrientation.VERTICAL,true,true,false);
			ChartUtilities.saveChartAsPNG(new File(graphName+".png"), chart, 1000, 700);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
