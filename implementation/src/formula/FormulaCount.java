package formula;

/**
 * Created by Philipp on 19.05.2017.
 */

import cells.Cell;
import cells.ECellType;
import swingfrontend.SwingFrame;

import java.util.ArrayList;

/**
 * Calculation for the FormulaTyp COUNT
 */


public class FormulaCount extends FormulaClass {

    private double amount;


    /**
     * makes calculation of count for singlecell and for a range of cells
     * @param left
     * @param right
     */

    public void buildCount(String left, String right) {
        Cell tempCell = SwingFrame.getSwingFrame().getCellValue(left);
        if(tempCell == null){
            return; //don´t do anything if there is no cell
        }

        if (right.isEmpty() && !left.isEmpty() && (tempCell.getCellType() == ECellType.FORMULA||
                tempCell.getCellType() == ECellType.NUMBER)) {
           amount = 1;
        }else {
            ArrayList<Cell> arrayList = SwingFrame.getSwingFrame().getCellValuesInRange(left, right, new int[1]);
            if(arrayList == null) {
                return;
            }

          amount = arrayList.size();

        }

    }



    /**
     * starts the calculation of number of cells
     * @param left
     * @param right
     * @return
     */
    @Override
    public String run(String left, String right) {
        buildCount(left, right);

        return amount + "";
    }


}
