package factory;

import cells.Cell;
import cells.ECellType;
import formula.EFormulaType;

/**
 * Created by nikola on 19.05.17.
 */
public class FunctionFactory extends AbstractFactory {
    @Override
    public Cell createCell(ECellType eCellType) {
        //not used in this factory
        return null;
    }

    @Override
    public void createFunction(EFormulaType eFormulaType) {

    }
}
