//Created by Andreas on 20.05.2017.

package swingfrontend.filestrategy;

import csvparser.CSVParser;
import odfparser.DocumentParser;
import swingfrontend.NAPATableModel;
import swingfrontend.SwingFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

import static utilities.R.getR;

/**
 *
 */
public class FileSaver {
    private static FileSaver ourInstance = new FileSaver();

    private JFileChooser fileChooser;

    public static FileSaver getInstance() {
        return ourInstance;
    }

    private FileSaver() {
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter(getR().label("TableFiles"), "csv", "ods"));
    }

    /**
     * Opens a File chooser and then uses the strategy pattern to decide how to save the file
     * @param tableModels The ArrayList of tableModels to save (the sheets of the spreadsheet )
     * @return returns true if saved and false if the save failed
     */
    public boolean saveFile(ArrayList<NAPATableModel> tableModels){

        try{


            int fileChooserResult = fileChooser.showDialog(SwingFrame.getFrame(), getR().label("OK"));

            if(fileChooserResult == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                int indexOf = file.getName().lastIndexOf('.');
                String fileType = file.getName().substring(indexOf +1 );

                if(fileChooser.getFileFilter().accept(file)){
                    //file accepted
                    System.out.println("File accepted");

                    switch (fileType){

                        case "csv":
                            System.out.println("Saving CSV");
                            CSVParser mParser = new CSVParser(file);
                            mParser.saveCSV(tableModels, file, ';');
                            return true;
                        case "ods":
                            System.out.println("Saving");
                            DocumentParser parser = new DocumentParser(""); // the path is irrelevant here
                            parser.saveODF(tableModels, file);
                            return true;

                        default:

                            //throw some kind of error
                            return false;
                    }
                }
                else {
                    System.out.println("File rejected");
                    return false;
                }
            }

        }catch (Exception e){

            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false; //something went wrong
        }

        return false;


    }
}
