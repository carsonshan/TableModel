//Created by Andreas on 20.05.2017.

package swingfrontend;

import cells.Cell;
import cells.ECellType;
import cells.FormulaCell;
import cells.NumberCell;
import factory.AbstractFactory;
import factory.FactoryCreator;

import javax.swing.event.MouseInputListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;

/**
 *
 */
public class NAPATableModel extends AbstractTableModel {

    private HashMap<Integer,Cell> cells;
    private int rows;
    private int cols;
    private AbstractFactory factory;
    private boolean isUpdating; // we need this to block operations while updating

    /**
     *  Creates a new table model with the specified number of rows and columns
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public NAPATableModel(int rows, int cols){

        factory = FactoryCreator.getFactoryCreator().getCellFactory();

        cells = new HashMap<>(rows * cols);

        this.rows = rows;
        this.cols = cols;
        isUpdating = false;
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return cols;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        if(isUpdating){
            System.out.println("updating...returning...");
            return;
        }

        isUpdating = true;


        if(aValue.toString().isEmpty()){
            Cell tempCell = getCellAT(rowIndex, columnIndex);
            isUpdating = false;
            if(tempCell != null){
                //continue
                setCellAt(rowIndex,columnIndex,null);
                return;
            }
            else{
                return;
            }
        }

        ECellType cellToCreateType;

        if(aValue.toString().charAt(0) == '='){
            cellToCreateType = ECellType.FORMULA;
        }
        else {
            try{
                Double.parseDouble(aValue.toString()); // we can safely ignore the result here, because we only do this to check what cell type we must create.

                cellToCreateType = ECellType.NUMBER;
            }catch (NumberFormatException e){
                cellToCreateType = ECellType.TEXT;
            }
        }

        //now check if there is a cell at that index
        Cell cellAtIndex = getCellAT(rowIndex, columnIndex);
        boolean mustReplaceCell = false;

        if(cellAtIndex != null){//cell exists

            //check if we need to replace it, or create a new cell type
            if(cellAtIndex.getCellType() == cellToCreateType){
                cellAtIndex.setContent(aValue);

            }
            else {
                mustReplaceCell = true;

            }
        }else {

            Cell tempCell = factory.createCell(cellToCreateType);
            tempCell.setContent(aValue);
            setCellAt(rowIndex, columnIndex, tempCell);
        }

        if(mustReplaceCell){

            Cell newCell = factory.createCell(cellToCreateType);
            newCell.setContent(aValue.toString());


            setCellAt(rowIndex, columnIndex, newCell );

            switch (cellAtIndex.getCellType()){

                case FORMULA:

                    ((FormulaCell) cellAtIndex).notifyCellObserver();
                    break;
                case NUMBER:

                    ((NumberCell) cellAtIndex).notifyCellObserver();
                    break;
                case TEXT:

                    break;
                default:
                    // create a text
            }


        }
        isUpdating = false;

    }



    /**
     *  Returns the cell at the specified index. Returns null if no cell was found.
     * @param rowIndex row index of the cell
     * @param columnIndex column index of the cell
     * @return either the cell at the index or null if no cell exists there yet
     */
    public Cell getCellAT(int rowIndex, int columnIndex){
        int index = columnIndex + (rowIndex * cols);

        try{
            return cells.get(index);
        }catch (Exception e){
            // I don´t care why it throws an axception, we just return null
            return null;
        }
    }

    /**
     *  Returns the cells raw value, not the display value, for a formula this would be the actual formula, not the formula result.
     *  Used for saving correctly.
     * @param rowIndex row index of the cell
     * @param columnIndex column index of the cell
     * @return returns the raw value
     */
    public String getRawValue(int rowIndex, int columnIndex){
        Cell cellAtIndex = getCellAT(rowIndex, columnIndex);

        if(cellAtIndex != null){
            return  cellAtIndex.getRawValue();
        }
        else {
            return "";
        }
    }

    /**
     *  Places a cell at the specified index
     * @param rowIndex row to place
     * @param columnIndex column to place
     * @param cell the cell to place
     */
    private void setCellAt(int rowIndex, int columnIndex, Cell cell){
        int index = columnIndex + (rowIndex * cols);

        cells.put(index, cell);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return getCellAT(rowIndex, columnIndex);
    }



}
