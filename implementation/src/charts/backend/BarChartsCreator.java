package charts.backend;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import swingfrontend.NAPATableModel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Barchart specific creation class
 */
public class BarChartsCreator extends JDialog {

    public BarChartsCreator(String applicationTitle, String chartTitle, CategoryDataset mDataset) {
        super();
        //different charts require different parameter, which can be found in the ChartFactory class
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "opt",
                "opt",
                mDataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        //size is adjustable
        chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
        setContentPane(chartPanel);
    }



}
