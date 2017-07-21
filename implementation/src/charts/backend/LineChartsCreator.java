package charts.backend;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import javax.swing.*;

/**
 * Line chart specific creation class
 */
public class LineChartsCreator extends JDialog{

    public LineChartsCreator(String applicationTitle, String chartTitle, CategoryDataset mDataset) {
        super();
        //different charts require different parameter, which can be found in the ChartFactory class
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "opt",
                "opt",
                mDataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        //size is adjustable
        chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
        setContentPane(chartPanel);
    }

}
