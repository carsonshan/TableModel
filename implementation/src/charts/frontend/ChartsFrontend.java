package charts.frontend;

import swingfrontend.action.CreateChartAction;

import javax.swing.*;
import java.awt.*;

import static utilities.R.getR;

/**
 * The frontend class that sets up the Swing chart frame presented to the user
 */
public class ChartsFrontend {

    public ChartsFrontend() {

    }

    public static void createTabbedFrame() {
        JDialog  dialog = new JDialog();

        //tabbed pane
        JTabbedPane mTabs = new JTabbedPane();

        //textFields
        JTextField lineChartRow = new JTextField();
        JTextField lineChartCol = new JTextField();
        JTextField barChartCol = new JTextField();
        JTextField barChartCompareParam = new JTextField();

        //buttons
        JButton lineChartCreate = new JButton(getR().label("CreateChart"));
        lineChartCreate.setAction(new CreateChartAction(true,lineChartRow, lineChartCol));
        lineChartCreate.setText(getR().label("CreateChart"));

        JButton barChartCreate = new JButton(getR().label("CreateChart"));
        barChartCreate.setAction(new CreateChartAction(false, barChartCol, barChartCompareParam));
        barChartCreate.setText(getR().label("CreateChart"));

        //labels
        JLabel startCellLC = new JLabel(getR().label("StartCell"));
        JLabel endCellLC = new JLabel(getR().label("EndCell"));
        JLabel chooseColumnBC = new JLabel(getR().label("StartCell"));
        JLabel chooseCompareParamBC = new JLabel(getR().label("EndCell"));

        //panels
        JPanel lineChartPanel = new JPanel();
        lineChartPanel.setLayout(new GridLayout(3, 1));
        JPanel barChartPanel = new JPanel();
        barChartPanel.setLayout(new GridLayout(3, 1));

        //line chart content
        lineChartPanel.add(lineChartRow);
        lineChartPanel.add(startCellLC);
        lineChartPanel.add(lineChartCol);
        lineChartPanel.add(endCellLC);
        lineChartPanel.add(lineChartCreate);

        //bar chart content
        barChartPanel.add(barChartCol);
        barChartPanel.add(chooseColumnBC);
        barChartPanel.add(barChartCompareParam);
        barChartPanel.add(chooseCompareParamBC);
        barChartPanel.add(barChartCreate);

        //adding tabs
        mTabs.add(getR().label("LineChart"), lineChartPanel);
        mTabs.add(getR().label("BarChart"), barChartPanel);

        dialog.setPreferredSize(new Dimension(400,250));
        dialog.add(mTabs);
        dialog.pack();
        dialog.setVisible(true);
    }
}
