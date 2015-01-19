package SPARQLAnfragen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import java.util.*;

public class BarChartInstanceBelongsToClass extends ApplicationFrame  {

	   class CustomRenderer extends BarRenderer {

	        /** The colors. */
	        private Paint[] colors;

	        /**
	         * Creates a new renderer.
	         *
	         * @param colors  the colors.
	         */
	        public CustomRenderer(final Paint[] colors) {
	            this.colors = colors;
	        }

	        /**
	         * Returns the paint for an item.  Overrides the default behaviour inherited from
	         * AbstractSeriesRenderer.
	         *
	         * @param row  the series.
	         * @param column  the category.
	         *
	         * @return The item color.
	         */
	        public Paint getItemPaint(final int row, final int column) {
	            return this.colors[column % this.colors.length];
	        }
	    }
	
public BarChartInstanceBelongsToClass(String title) {

    super(title);
    CategoryDataset dataset = createDataset();
    JFreeChart chart = createChart(dataset);
    ChartPanel chartPanel = new ChartPanel(chart, false);
    chartPanel.setPreferredSize(new Dimension(500, 270));
    setContentPane(chartPanel);

}

/**
 * Returns a sample dataset.
 * 
 * @return The dataset.
 */
private static CategoryDataset createDataset() {
    
    // row keys...
    String series1 = "First";

    // column keys...
    /*String category1 = "Category 1";
    String category2 = "Category 2";
    String category3 = "Category 3";
    String category4 = "Category 4";
    String category5 = "Category 5";*/

    // create the dataset...
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    List<String> x2 = Reader.readFile("Instances_belonging_to_Classes_x.txt");
	String[] x = x2.toArray(new String[x2.size()]);	
	
	List<String> y2 = Reader.readFile("Instances_belonging_to_Classes_y.txt");
	String[] y = y2.toArray(new String[y2.size()]);	
	
    for (int i=0; i<= x.length-1;i=i+1) {
    	double x3=Double.parseDouble(x[i]);
    	double y3=Double.parseDouble(y[i]);
    	
    	dataset.addValue(y3, series1, x[i]);
    }
    
    
    return dataset;
    
}

/**
 * Creates a sample chart.
 * 
 * @param dataset  the dataset.
 * 
 * @return The chart.
 */
private static JFreeChart createChart(CategoryDataset dataset) {
    
    // create the chart...
    JFreeChart chart = ChartFactory.createBarChart(
        "Bar Chart Demo",         // chart title
        "Anzahl Klassenzugeh�rogkeiten",               // domain axis label
        "Anzahl Instanzen",                  // range axis label
        dataset,                  // data
        PlotOrientation.VERTICAL, // orientation
        true,                     // include legend
        true,                     // tooltips?
        false                     // URLs?
    );

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

    // set the background color for the chart...
    chart.setBackgroundPaint(Color.white);

    // get a reference to the plot for further customisation...
    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setDomainGridlinePaint(Color.white);
    plot.setDomainGridlinesVisible(true);
    plot.setRangeGridlinePaint(Color.white);

    // set the range axis to display integers only...
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    // disable bar outlines...
    plot.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    plot.getRenderer().setBaseItemLabelsVisible(true);
    renderer.setDrawBarOutline(false);
  
 
    // set up gradient paints for series...
    GradientPaint gp0 = new GradientPaint(
        0.0f, 0.0f, Color.blue, 
        0.0f, 0.0f, new Color(0, 0, 64)
    );
    GradientPaint gp1 = new GradientPaint(
        0.0f, 0.0f, Color.green, 
        0.0f, 0.0f, new Color(0, 64, 0)
    );
    GradientPaint gp2 = new GradientPaint(
        0.0f, 0.0f, Color.red, 
        0.0f, 0.0f, new Color(64, 0, 0)
    );
    renderer.setSeriesPaint(0, gp0);
    renderer.setSeriesPaint(1, gp1);
    renderer.setSeriesPaint(2, gp2);

    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setCategoryLabelPositions(
        CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
    );
    // OPTIONAL CUSTOMISATION COMPLETED.
    
    return chart;
    
}

/**
 * Starting point for the demonstration application.
 *
 * @param args  ignored.
 */
public static void main(String[] args) {

	BarChartInstanceBelongsToClass demo = new BarChartInstanceBelongsToClass("Bar Chart Demo");
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);

}
}
