//Created by Andreas on 17.05.2017.

package swingfrontend.action;

import swingfrontend.SwingFrame;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.Locale;

import static utilities.R.getR;

/**
 *
 */
public class LanguageChangedAction extends AbstractAction {


    @Override
    public void actionPerformed(ActionEvent e) {

        //get the desired language from a pop up menu
        Locale selectedLocale = (Locale)JOptionPane.showInputDialog(SwingFrame.getFrame(),
                getR().label("ChangeLanguage"),
                getR().label("PickLanguage"),
                JOptionPane.PLAIN_MESSAGE,
                null,
                getR().getSupportedLocales(),
                getR().getCurrentLocale()
                );

        if(selectedLocale != null){
            getR().changeLocale(selectedLocale);
        }

    }
}
