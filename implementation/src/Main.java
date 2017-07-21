import csvparser.CSVParser;
import swingfrontend.SwingFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Andreas on 18.05.2017.
 */
public class Main {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwingFrame.createAndShowGUI(); //this is the entry point to the app
            }
        });
    }
}
