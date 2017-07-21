package swingfrontend.action;

import charts.frontend.ChartsFrontend;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Andreas on 25.05.2017.
 */
public class ShowCreateChartWindowAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        ChartsFrontend.createTabbedFrame();
    }
}
