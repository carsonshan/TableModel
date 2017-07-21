package charts.staticcharts;

import csvparser.CSVParser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import swingfrontend.NAPATableModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The functionality is the same as the BarChart basically, ie. creating the chart, filling it with data and displaying it
 */
public class LineChart extends ApplicationFrame {

    public LineChart(String applicationTitle, String chartTitle, ArrayList<NAPATableModel> tables) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Years (1981 - 2015)","Temperature",
                createDataset(tables.get(0)),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1120 , 734));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(NAPATableModel table) {
        NAPATableModel mModel = table;

        int rows = mModel.getRowCount() - 20;
        int columns = mModel.getColumnCount() - 20;
        //use the NAPATableModel to supply the chart with data

        ArrayList<String> mMonths = new ArrayList<>();
        ArrayList<Integer> mYears = new ArrayList<>();

        //add months
        for (int i = 1; i < columns; i++) {
            mMonths.add(mModel.getValueAt(1, i).toString());
        }

        //add years
        for (int i = 2; i < rows; i++) {
            double value = Double.parseDouble(mModel.getValueAt(i, 0).toString());
            int castedV = (int) value;
            mYears.add(castedV);
        }

        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        int monthIndex = 0;
        int yearIndex = 0;
        for (int i = 2; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                double value = Double.parseDouble(mModel.getValueAt(i, j).toString());
                mDataset.addValue(value, mMonths.get(monthIndex), mYears.get(yearIndex));
                monthIndex++;
            }
            yearIndex++;
            monthIndex = 0;
        }

        return mDataset;
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

        LineChart mChart = new LineChart(
                "Line chart for temperature differences" ,
                "Temperature over the years", mTabels);
        mChart.pack();
        RefineryUtilities.centerFrameOnScreen(mChart);
        mChart.setVisible(true);
    }
}
