package com.eris4.benchdb.core.reporter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.eris4.benchdb.core.Printer;
import com.eris4.benchdb.core.monitor.Monitor;
import com.eris4.benchdb.core.util.ThreadUtils;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Section;

public class LinearGraphReporter extends Reporter {

	protected static final long PERIOD_CHECK = 100;
	private boolean stop;
	private String xAxisLabel;
	private String yAxisLabel;
	private List<GraphLine> list = new LinkedList<GraphLine>();
	private List<XYSeries> xySeriesList = new LinkedList<XYSeries>();
	private List<String> descriptions = new LinkedList<String>();
	private String name = "GraphReporter";
	protected boolean logarithmic = false;
	
	private void registerCoord() {
		for (GraphLine graph : list) {
			graph.registerCoord();
		}	
	}
		
	public void add(String name,Monitor xAxis,Monitor yAxis){
		xAxisLabel = xAxis.getDescription();
		yAxisLabel = yAxis.getDescription();
		list.add(new GraphLine(name,xAxis,yAxis));
	}

	
	public void start() {	
		for (GraphLine line : list) {
			line.reset(getDatabase().getClass().getSimpleName());
		}	
		registerCoord();
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

	
	public void stop() {
		stop = true;
		registerCoord();
		for (GraphLine line : list) {
			xySeriesList.add(line.getXYSeries());
		}		
	}

	
	public void report(Section section) throws DocumentException {
		try {
			XYSeriesCollection dataset = new XYSeriesCollection();
			for (XYSeries series : xySeriesList) {
				dataset.addSeries(series);
			}
			String title = name;
			for (String description : descriptions) {
				title += "\n" + description;
			}			
			JFreeChart chart = ChartFactory.createXYLineChart(title ,xAxisLabel,yAxisLabel,dataset,PlotOrientation.VERTICAL,true,true,false);
			if (logarithmic  == true){
				LogAxis logAxis = new LogAxis(yAxisLabel);
				logAxis.setSmallestValue(1);
				chart.getXYPlot().setRangeAxis(0,logAxis);
			}
			BufferedImage chartImage = chart.createBufferedImage(1000, 700);
			Image image = Image.getInstance(ChartUtilities.encodeAsPNG(chartImage));
			image.scaleToFit(Printer.IMAGE_WIDTH, 400);
			section.add(image);
			
//			ChartUtilities.saveChartAsPNG(Resource.getNewFile(graphName+".png"), chart, 1000, 700);
//			System.out.println(">>>>>>>> ho stampato il grafico: "+graphName+"!!! <<<<<<<");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	public void addDescription(String description) {		
		descriptions.add(description);
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	

}
