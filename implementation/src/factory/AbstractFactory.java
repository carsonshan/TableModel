package factory;

import cells.Cell;
import cells.ECellType;
import formula.EFormulaType;

/**
 * Created by nikola on 19.05.17.
 */
public abstract class AbstractFactory {

    abstract public Cell createCell(ECellType eCellType) ;

    abstract public void createFunction(EFormulaType eFormulaType);
}
