//Created by Andreas on 18.05.2017.

package swingfrontend.action;

import swingfrontend.SwingFrame;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

import static utilities.R.getR;

/**
 *
 */
public class AboutInfoAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {

        JOptionPane.showConfirmDialog(SwingFrame.getFrame(),
                getR().label("AboutInfo"),
                getR().label("MenuAbout"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }
}
