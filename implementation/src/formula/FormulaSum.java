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
 * Calculation for the formulatyp SUM
 */


public class FormulaSum extends FormulaClass{

    private double sum = 0;



    /**
     * makes calculation sum for singlecell and cells in range
     * @param left
     * @param right
     */

    public void buildSum(String left, String right){

        if (right.isEmpty()){
            Cell tempCell = SwingFrame.getSwingFrame().getCellValue(left);

            switch (tempCell.getCellType()){
                case TEXT:
                    break;
                case NUMBER:
                    sum = ((NumberCell)tempCell).getCellContent();
                    break;
                case FORMULA:
                    sum = ((FormulaCell)tempCell).getContent();
                    break;
            }

        }
        else{
            ArrayList<Cell> arrayList = SwingFrame.getSwingFrame().getCellValuesInRange(left, right, new int[1]);
            if(arrayList == null){
                return;
            }
            else {
                for(Cell cell : arrayList){
                    sum += getCellValue(cell);


                }
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
        else{
            switch (cell.getCellType()){
                case TEXT:
                    // we ignore Textcells
                    return 0;
                case NUMBER:
                    return ((NumberCell) cell).getCellContent();

                case FORMULA:
                    return ((FormulaCell) cell).getContent();
                default:
                    System.out.println("ERROR: Calculation-SUM doesn't work");

            }
        }
        return 0;
    }



    /**
     * start running the calculation of celltype SUM
     * @param left
     * @param right
     * @return
     */

   @Override
    public String run(String left, String right) {
       
        buildSum(left,right);

     return sum + "";
    }

}