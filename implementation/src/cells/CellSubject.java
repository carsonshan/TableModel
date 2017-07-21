package cells;

/**
 * Created by aldinbradaric on 19/05/17.
 */
public interface CellSubject {

    public void addCellObserver(CellObserver o);

    public void removeCellObserver(CellObserver o);

    public void notifyCellObserver();

    public Cell getUpdate(CellObserver o);
}
