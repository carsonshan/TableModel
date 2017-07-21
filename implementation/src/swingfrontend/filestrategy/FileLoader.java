// Created by Andreas on 20.05.2017.

package swingfrontend.filestrategy;

import csvparser.CSVParser;
import odfparser.DocumentParser;
import swingfrontend.NAPATableModel;
import swingfrontend.SwingFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

import static utilities.R.getR;

/**
 *
 */
public class FileLoader {


    private static FileLoader ourInstance = new FileLoader();

    private JFileChooser fileChooser;

    /**
     *  Singleton access
     * @return returns the FileLoader
     */
    public static FileLoader getInstance() {
        return ourInstance;
    }

    /**
     * Private constructor
     */
    private FileLoader() {
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter(getR().label("TableFiles"), "csv", "ods"));
    }

    /**
     * Opens a File chooser and then uses the strategy pattern to decide how to load the file (Yes yes, we know there should be a factory involved and stuff, but it´s the best we got right now)
     * @return returns an ArrayList of NAPATableModels that contain the spreadsheet data
     */
    public ArrayList<NAPATableModel> loadFile(){
        try{


            int fileChooserResult = fileChooser.showDialog(SwingFrame.getFrame(), getR().label("OK"));

            if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                int indexOf = file.getName().lastIndexOf('.');
                String fileType = file.getName().substring(indexOf +1 );

                if(fileChooser.getFileFilter().accept(file)){
                    //file accepted

                    switch (fileType){

                        case "csv":

                            String[] delimiterOptions = {";", ","};
                            String chosenSeparator = JOptionPane.showInputDialog(SwingFrame.getFrame(),
                                    getR().label("ChooseDelimiter"),
                                    getR().label("ChooseDelimiterTitle"), JOptionPane.PLAIN_MESSAGE, null, delimiterOptions, delimiterOptions[0]).toString();

                            CSVParser mParser = new CSVParser(file);
                            return mParser.readCSV(file, chosenSeparator.charAt(0));
                        case "ods":
                            DocumentParser parser = new DocumentParser(""); // the path is irrelevant here
                            return parser.readFromFile(file);

                        default:

                            //throw some kind of error
                            return null;
                    }
                }
                else {
                    System.out.println("File rejected");
                    return null;
                }
            }

        }catch (Exception e){
            return null; //something went wrong
        }
        return null;
    }
}
