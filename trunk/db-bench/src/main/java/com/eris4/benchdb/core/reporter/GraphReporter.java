package com.eris4.benchdb.core.reporter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.util.Resource;
import com.eris4.benchdb.core.util.ThreadUtils;

public class GraphReporter extends Reporter {

	protected static final long PERIOD_CHECK = 100;
	private boolean stop;
	private String xAxisLabel;
	private String yAxisLabel;
	private List<GraphLine> list = new LinkedList<GraphLine>();
	private List<XYSeries> xySeriesList = new LinkedList<XYSeries>();
	private String graphName = "Grafico senza nome";
	
	public GraphReporter(String graphName) {
		this.graphName = graphName;
	}

	private void registerCoord() {
		for (GraphLine graph : list) {
			graph.registerCoord();
		}	
	}
		
	public void add(String name,Monitor xAxis,Monitor yAxis){
		xAxisLabel = xAxis.getDescription();
		yAxisLabel = yAxis.getDescription();
		list.add(new GraphLine(name,xAxis,yAxis));

		yAxis.registerReporter(this);
	}

	@Override
	public void start() {	
		for (GraphLine line : list) {
			line.reset(getDatabase().getClass().getSimpleName());
		}	
		stop  = false;		
		new Thread(){
			public void run() {
				while(!stop) {		
					registerCoord();	
					ThreadUtils.sleep(PERIOD_CHECK);									
				}
			}							
		}.start();
	}

	@Override
	public void stop() {
		stop = true;
		for (GraphLine line : list) {
			xySeriesList.add(line.getXYSeries());
		}		
	}

	@Override
	public void report() {
		try {
			XYSeriesCollection dataset = new XYSeriesCollection();
			for (XYSeries series : xySeriesList) {
				dataset.addSeries(series);
			}
			JFreeChart chart = ChartFactory.createXYLineChart(graphName ,xAxisLabel,yAxisLabel,dataset,PlotOrientation.VERTICAL,true,true,false);
			ChartUtilities.saveChartAsPNG(Resource.getNewFile(graphName+".png"), chart, 1000, 700);
			System.out.println(">>>>>>>> ho stampato il grafico!!! <<<<<<<");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
