package csvparser;

/**
 * Created by Aldin Bradaric.
 *
 */

import java.io.*;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import swingfrontend.NAPATableModel;


/**
 * The idea of the CSVParser class is to both read and save CSV files, utilizing our NAPATableModel.
 *
 */
public class CSVParser {

    private File csvPath = new File("");

    private CSVReader reader = null;


    public CSVParser(File path){
        this.csvPath = path;
    }

    /**
     * This method gets a path to a CSV files and inputs its cells' value inside out NAPATableModel.
     * @param filePath
     * @param separator
     * @return
     * @throws IOException
     */
    public ArrayList<NAPATableModel> readCSV(File filePath, char separator) throws IOException {
                             //
        reader = new CSVReader(new FileReader(filePath), separator, '\"');
        int rows = 0;
        int columns = 0;

        ArrayList<NAPATableModel> tables = new ArrayList<>();

        for (String[] line : reader){
            columns = line.length;
            rows++;
        }


        NAPATableModel mTableModel = new NAPATableModel(rows + 20, columns + 20);
        int newRow = 1;

        reader = new CSVReader(new FileReader(filePath), separator, '\"');

        for (String[] line : reader) {
            for (int i = 0; i < columns; i++) {
                try{//requires Object, row, column
                    mTableModel.setValueAt(line[i], newRow-1, i);
                }catch (IndexOutOfBoundsException e){
                    System.out.println("out of bounds at row: ");
                }
            }
            newRow++;
        }

        tables.add(mTableModel);
        return tables;
    }

    /**
     * The save method on the other hand gets a NAPATableModel, or rather, an ArrayList thereof and,
     * using the CSVWriter class, saves its content in the location provided by the user.
     * @param tableSheet
     * @param filePath
     * @param separator
     * @throws IOException
     */
    public void saveCSV (ArrayList<NAPATableModel> tableSheet, File filePath, char separator) throws IOException {

        NAPATableModel mModel = tableSheet.get(0);
        CSVWriter mWriter = new CSVWriter(new FileWriter(filePath), separator);

        int columns = tableSheet.get(0).getColumnCount();
        int rows = tableSheet.get(0).getRowCount();
        String[] rowValues = new String[columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Object temp = mModel.getRawValue(i, j);
                if (temp != null) {
                    rowValues[j] = temp.toString();
                }
                else {
                    rowValues[j] = "";
                }
            }
            mWriter.writeNext(rowValues);
        }

        mWriter.close();
    }


}