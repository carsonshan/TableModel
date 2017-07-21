package odfparser;


import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.spreadsheet.Sheet;
import swingfrontend.NAPATableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by nikola on 20.05.17.
 */
public class DocumentParser {

    String path = "";

    public DocumentParser(String path){
        this.path = path;
    }

    /*
        Takes values from file and set them in new table
     */
    public ArrayList<NAPATableModel> readFromFile(File file) throws IOException {

        SpreadSheet worksheet = SpreadSheet.createFromFile(file);

        Set<String> rangesNames = worksheet.getRangesNames();

        ArrayList<NAPATableModel> tables = new ArrayList<NAPATableModel>();

        System.out.println("rangesNames:\n" + rangesNames.toString());
        System.out.println("sheets count:\n" + worksheet.getSheetCount());

        int sheetCount = worksheet.getSheetCount();

        for(int i = 0; i < sheetCount; i++){

            Sheet sheet = worksheet.getSheet(i);

            int rows    = sheet.getRowCount();
            int columns = sheet.getColumnCount();

            System.out.println("rows: " + rows);
            System.out.println("columns: " + columns);

            NAPATableModel newTableModel = new NAPATableModel(rows + 50, columns + 50);

            for(int j = 0; j < rows; j++){
                for(int k = 0; k < columns; k++){
                    //System.out.println("row: " + j + ", column: " + k + sheet.getCellAt(j,k).toString());
                    try{
                        newTableModel.setValueAt(sheet.getCellAt(k,j).getValue(), j,k);
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("out of bounds at row: " + j + ", column: " + k);
                    }

                }
            }

            tables.add(newTableModel);

        }

        return tables;
    }

    public void saveODF (ArrayList<NAPATableModel> tableModel, File file) throws IOException {

        SpreadSheet spreadSheet = SpreadSheet.create(tableModel.size(),
                tableModel.get(0).getColumnCount(),
                tableModel.get(0).getRowCount());

        int i = 0;

        for(NAPATableModel model: tableModel){

            Sheet sheet = spreadSheet.getSheet(i);

            for(int j = 0; j < sheet.getRowCount(); j++) {
                for (int k = 0; k < sheet.getColumnCount(); k++) {
                    sheet.setValueAt((Object) model.getRawValue(j,k),k,j);
                }
            }

            i++;

        }

        spreadSheet.saveAs(file);

    }
}
