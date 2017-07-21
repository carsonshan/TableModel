package cells;

import formula.FormulaEvaluator;

import java.util.ArrayList;

/**
 * Extending the Cell class, FormulaCell implements both the CellObserver and the CellSubject interface
 * as it needs to be able to listen to other cells and be listened to itself.
 */
public class FormulaCell extends Cell implements CellObserver, CellSubject {
    private String formula;
    private double evaluatedFormula;
    private ArrayList<CellObserver> mObservers = new ArrayList<>();
    private ArrayList<CellSubject> mSubjects = new ArrayList<>();

    public FormulaCell() {
        super();
        cellType = ECellType.FORMULA;
    }

    @Override
    public String getRawValue() {
        return formula;
    }

    /**
     * class specific evaluateContentMethod
     */
    public double getContent() {
        //reference evaluation classes and parse String in order to get the evaluated double value
        System.out.println("Evaluating content");
        return FormulaEvaluator.evaluateFormula(formula);
    }

    public void setContent(Object content) {
        this.formula = content.toString();
    }

    @Override
    public String toString() {
        return "" + getContent();
    }

    /**
    * Observer interface methods
    * */
    @Override
    public void update() {
        //update the subscribed Observers
        notifyCellObserver();
    }

    @Override
    public void setCellSubject(CellSubject s) {
        //do something
        mSubjects.add(s);
    }

    /**
    * Subject interface methods
    * */
    @Override
    public void addCellObserver(CellObserver o) {
        mObservers.add(o);
    }

    @Override
    public void removeCellObserver(CellObserver o) {
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
        //get Object with updates
       return this;
    }
}
