package swingfrontend.action;

import charts.backend.ChartsCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Andreas on 25.05.2017.
 */
public class CreateChartAction extends AbstractAction{

    private boolean isLineChart;
    private JTextField startText;
    private JTextField endText;

    public CreateChartAction(boolean isLineChart, JTextField startText, JTextField endText){
        this.isLineChart = isLineChart;
        this.startText = startText;
        this.endText = endText;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChartsCreator.createChart(startText.getText(),endText.getText(), isLineChart);
    }
}
