package utilities;

import java.util.ArrayList;

/**
 * Created by Andreas on 18.05.2017.
 */
public interface ResourceSubject {

    void registerObserver(ResourceObserver observer);
    void unregisterObserver(ResourceObserver observer);

    void notifyObservers();
}
