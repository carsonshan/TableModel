package cells;

/**
 * The Cell class is an abstract class that is extend by the TextCell, NumberCell and FormulaCell.
 */
public abstract class Cell {
    protected ECellType cellType;

    public Cell() {
    }

    abstract public String getRawValue();

    public ECellType getCellType() {
        return cellType;
    }

    abstract public void setContent(Object content) ;
}
