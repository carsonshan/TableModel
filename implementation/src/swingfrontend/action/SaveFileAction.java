// Created by Andreas on 18.05.2017.

package swingfrontend.action;

import swingfrontend.SwingFrame;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 *
 */
public class SaveFileAction extends AbstractAction {

    SwingFrame swingFrame;

    public SaveFileAction(SwingFrame owner){
        super();
        swingFrame = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        swingFrame.saveFile();
    }
}
