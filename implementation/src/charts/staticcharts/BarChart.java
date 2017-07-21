package charts.staticcharts;

import csvparser.CSVParser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import swingfrontend.NAPATableModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The functionality is the same as the LineChart basically, ie. creating the chart, filling it with data and displaying it
 */
public class BarChart extends ApplicationFrame {

    //the BarChart constructor requires both the application and the chart title
    public BarChart(String applicationTitle, String chartTitle, ArrayList<NAPATableModel> tables) {
        super( applicationTitle );
        //using the abstract ChartFactory class to create the respective chart type
        //different charts require different parameter, which can be found in the ChartFactory class
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Districts",
                "Area",
                createDataset(tables.get(0)),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        //size is adjustable
        chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset(NAPATableModel table) {
        NAPATableModel mModel = table;

        int rows = 17;

        final String area = "Area";

        ArrayList<String> mCities = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            mCities.add(mModel.getValueAt(i, 1).toString());
        }

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        //Number and double value can added, depending on the method used
        int j = 0;
        for (int i = 1; i < rows; i++) {
            double value = Double.parseDouble(mModel.getValueAt(i, 2).toString());
            if (mCities.get(j) != null) {
                dataset.addValue(value, mCities.get(j), area);
            }
            j++;
        }

        return dataset;
    }

    public static void main(String[] args) {
        ArrayList<NAPATableModel> mTabels = new ArrayList<>();
        File file = new File("C:\\Users\\Andreas Lenz\\Desktop\\UniWien\\SWE2\\Taks2\\CSV-Files\\Flaechen_V2014.csv");
        CSVParser mParser = new CSVParser(file);
        try {
            mTabels = mParser.readCSV(file, ';');
        } catch(IOException e) {
            e.printStackTrace();
        }

        BarChart mChart = new BarChart("Areas",
                "Area sizes of the respective cities", mTabels);
        mChart.pack( );
        RefineryUtilities.centerFrameOnScreen(mChart);
        mChart.setVisible(true);
    }
}
