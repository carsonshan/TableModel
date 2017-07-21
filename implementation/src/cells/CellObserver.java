package cells;

/**
 * The CellObserver is part of the Observer pattern used for the cells package.
 * It is being implemented by the FormulaCell class.
 */
public interface CellObserver {

    public void update();

    public void setCellSubject(CellSubject s);
}
