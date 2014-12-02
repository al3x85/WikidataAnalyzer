package SPARQLAnfragen;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.util.ArrayList;
import java.util.*;
//import org.jfree.ui.Spacer;

public class LineChartDemo6 extends ApplicationFrame {

	 /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public LineChartDemo6(final String title) {

        super(title);

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     */
    private XYDataset createDataset() {
        
    	List<String> x2 = Reader.readFile("Distribution_Classes_with_Instances_Draw_x.txt");
		String[] x = x2.toArray(new String[x2.size()]);	
		
		List<String> y2 = Reader.readFile("Distribution_Classes_with_Instances_Draw_y.txt");
		String[] y = y2.toArray(new String[y2.size()]);	
		
		
		
    	
        final XYSeries series1 = new XYSeries("First");
        for (int i=0; i<= x.length-1;i=i+1) {
        	double x3=Double.parseDouble(x[i]);
        	double y3=Double.parseDouble(y[i]);
        	
        	series1.add(x3, y3);
        }
        

        final XYSeries series2 = new XYSeries("Second");
        series2.add(1.0, 8038.0);
        series2.add(100.0, 867.0);
        series2.add(250.0, 539.0);
        series2.add(500.0, 358.0);
        series2.add(1000.0, 4.0);
        series2.add(10000.0, 4.0);
        series2.add(25000.0, 2.0);
        series2.add(8.0, 1.0);

        final XYSeries series3 = new XYSeries("Third");
        series3.add(3.0, 4.0);
        series3.add(4.0, 3.0);
        series3.add(5.0, 2.0);
        series3.add(6.0, 3.0);
        series3.add(7.0, 6.0);
        series3.add(8.0, 3.0);
        series3.add(9.0, 4.0);
        series3.add(10.0, 3.0);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
                
        return dataset;
        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6",      // chart title
            "Anzahl Instanzen",                      // x axis label
            "Anzahl Klassen",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final LineChartDemo6 demo = new LineChartDemo6("Line Chart Demo 6");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

	
}
