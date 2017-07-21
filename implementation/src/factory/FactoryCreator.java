package factory;

/**
 * Created by Andreas on 20.05.2017.
 */
public class FactoryCreator {

    private static FactoryCreator factoryCreator; // the singleton

    //these are kept as singletons
    private FunctionFactory functionFactory;

    private CellFactory cellFactory;

    private FactoryCreator(){
        functionFactory = new FunctionFactory();
        cellFactory = new CellFactory();
    }

    public static FactoryCreator getFactoryCreator(){
        if(factoryCreator != null){
            return factoryCreator;
        }
        else {
            factoryCreator = new FactoryCreator();
            return factoryCreator;
        }
    }

    public CellFactory getCellFactory(){
        return cellFactory;
    }

    public FunctionFactory getFunctionFactory(){
        return functionFactory;
    }
}
