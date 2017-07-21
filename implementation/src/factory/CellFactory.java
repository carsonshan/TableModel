package factory;

import cells.*;
import formula.EFormulaType;

/**
 * Created by nikola on 19.05.17.
 */
public class CellFactory extends AbstractFactory {

    @Override
    public Cell createCell(ECellType eCellType) {
        switch (eCellType){
            case FORMULA:
                return new FormulaCell();
            case NUMBER:
                return  new NumberCell();
            case TEXT:
                return new TextCell();
        }
        return new TextCell(); // these cell types are pretty safe, so if something goes wrong things will still kind of work
    }

    @Override
    public void createFunction(EFormulaType eFormulaType) {
        // not used in this factory
    }
}
