package formula;

/**
 * Created by Philipp on 19.05.2017.
 */

import cells.Cell;
import cells.FormulaCell;
import cells.NumberCell;
import swingfrontend.SwingFrame;

import java.util.ArrayList;

/**
 * Calculation for the FormulaType MEAN
 */

public class FormulaMean extends FormulaClass {

    private double mean = 0;
    private double sum = 0;
    private double amount ;




    /**
     * Makes the Calculation mean for cells in range
     * @param left
     * @param right
     */

    public void buildMean(String left, String right) {
        Cell tempCell = SwingFrame.getSwingFrame().getCellValue(left);
        if (tempCell == null) {
            return; //don´t do anything if there is no cell
        }

        if (right.isEmpty()) {

            switch (tempCell.getCellType()) {
                case TEXT:
                    break;
                case NUMBER:
                    sum = ((NumberCell) tempCell).getCellContent();
                    amount = 1;
                    break;
                case FORMULA:
                    sum = ((FormulaCell) tempCell).getContent();
                    amount = 1;
                    break;
            }


        } else {
            ArrayList<Cell> arrayList =  SwingFrame.getSwingFrame().getCellValuesInRange(left, right, new int[1]);
            
            if(arrayList == null){
                return;
            }

            amount = arrayList.size();

            for(Cell cell : arrayList){
                sum += getCellValue(cell);
            }
        }
    }



    /**
     * Organize the static values of different celltyps
     * @param cell
     * @return
     */

    private double getCellValue(Cell cell){

        if(cell == null){
            return 0.0;
        }

        switch (cell.getCellType()){
            case TEXT:
                return 0; // we ignore Textcells
            case NUMBER:
                return ((NumberCell) cell).getCellContent();

            case FORMULA:
                return ((FormulaCell) cell).getContent();
            default:
                System.out.println("ERROR: Calculation-MEAN (SUM) doesn't work");

        }
        return 0;
    }



    /**
     *  start running the calculation an makes a final mean-calculation
     * @param left
     * @param right
     * @return
     */

    @Override
    public String run(String left, String right) {
        buildMean(left, right);
        mean = sum / amount;
        return mean + "";
    }


}
