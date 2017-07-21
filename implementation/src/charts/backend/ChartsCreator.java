package charts.backend;

import cells.Cell;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import swingfrontend.SwingFrame;

import java.util.ArrayList;

import static utilities.R.getR;

/**
 * This class is regarded as the backend of the Swing frame that pops up
 * once the create charts option is chosen.
 */
public class ChartsCreator {

    public ChartsCreator() {}

    static public void createChart(String startCell, String endCell, boolean isLineChart) {

        SwingFrame mFrame = SwingFrame.getSwingFrame();
        ArrayList<Cell> mList;
        int rowL;
        int colCount;

        int[] intRowOptions = new int[1];
        mList = mFrame.getCellValuesInRange(startCell, endCell, intRowOptions);
        rowL = intRowOptions[0];

        if(false) {
            //error with data
            return;
        }
        else {
            //jfree charts require DefaultCategoryDataset objects
            DefaultCategoryDataset mData = new DefaultCategoryDataset();

            if (rowL == -1) { // magic numbers^^ this means we have a row
                rowL = mList.size();
                colCount = 1;
            }
            else { //this means we have several rows
                if(!isLineChart){ //bar charts are only supported for rows
                    return;
                }
                colCount = mList.size() / rowL;
            }

            for (int i = 0; i < colCount; i++) {
                for(int j = 0; j < rowL; j++) {
                    int index = j + (i * rowL);
                    double castedValue = Double.parseDouble(mList.get(index).toString());
                    if(isLineChart){
                        mData.addValue(castedValue, i + "", getR().label("Values") + " " + j);
                    }
                    else{
                        mData.addValue(castedValue, j + "", getR().label("Values"));
                    }
                }
            }

            if (isLineChart) {
                LineChartsCreator mLineChart = new LineChartsCreator(getR().label("AppName"), getR().label("LineChart"), mData);
                mLineChart.pack();
                RefineryUtilities.centerFrameOnScreen(mLineChart);
                mLineChart.setVisible(true);
            } else {
                BarChartsCreator mBarChart = new BarChartsCreator(getR().label("AppName"), getR().label("BarChart"), mData);
                mBarChart.pack();
                RefineryUtilities.centerFrameOnScreen(mBarChart);
                mBarChart.setVisible(true);
            }
        }
    }
}
