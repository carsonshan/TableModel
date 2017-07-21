package cells;

import java.util.ArrayList;

/**
 * The NumberCell needs to be able to listen to changes in other cells hence
 * it implements the CellSubject interface.
 */
public class NumberCell extends Cell implements CellSubject {
    private ArrayList<CellObserver> mObservers = new ArrayList<>();
    private double cellContent;

    public NumberCell() {
        super();
        cellType = ECellType.NUMBER;
    }

    /**
     * Extended class method
     * @return
     */
    @Override
    public String getRawValue() {
        return cellContent + "";
    }

    /**
     * setContent takes a String object and casts it to double in order to fit the number criteria.
     * @param content
     */
    public void setContent(Object content) {
        try{
            this.cellContent = Double.parseDouble(content.toString());
        }
        catch (NumberFormatException e){
            this.cellContent = 0; //go on with an empty value
        }

    }

    public double getCellContent() {
        return this.cellContent;
    }

    @Override
    public String toString() {
        return "" + cellContent;
    }

    /**
    * Subject interface methods
    * */
    @Override
    public void addCellObserver(CellObserver o) {
        //add Observer
        mObservers.add(o);
    }

    @Override
    public void removeCellObserver(CellObserver o) {
        //remove Observer
        mObservers.remove(o);
    }

    @Override
    public void notifyCellObserver() {
        //notify the Observers
        for (CellObserver o : mObservers) {
            o.update();
        }
    }

    @Override
    public Cell getUpdate(CellObserver o) {
        //get Object with updates, pseudo code below
       return this;
    }
}
