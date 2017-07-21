// Created by Andreas on 18.05.2017.

package swingfrontend.action;

import swingfrontend.SwingFrame;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

import static utilities.R.getR;

/**
 *
 */
public class LoadFileAction extends AbstractAction {

    SwingFrame swingFrame;

    public LoadFileAction(SwingFrame owner){
        super();
        swingFrame = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] options = {getR().label("Yes"),
                getR().label("No"),
                getR().label("Cancel")};

        int n = JOptionPane.showOptionDialog(SwingFrame.getFrame(),
                getR().label("NewFileSaveFirstPrompt"),
                getR().label("MenuItemLoadFile"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        switch (n){
            case JOptionPane.CANCEL_OPTION:
                System.out.println("option chosen: Canel" + n);
                break;
            case JOptionPane.CLOSED_OPTION:
                System.out.println("option chosen: close" + n);
                break;
            case JOptionPane.YES_OPTION:
                System.out.println("option chosen: yes" + n);

                swingFrame.saveFile();
                swingFrame.loadFile();
                break;
            case JOptionPane.NO_OPTION:
                System.out.println("option chosen: no" + n);
                swingFrame.loadFile();
                break;
            default:
                System.out.print("option chosen: default- error abort, launch all nukes and abandon ship! " + n);
                break;
        }
    }
}
