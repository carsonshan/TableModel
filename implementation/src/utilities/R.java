package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Andreas on 17.05.2017.
 */
public class R implements ResourceSubject{

    private static R singletonResource = null;

    private ArrayList<ResourceObserver> observers;
    private Locale currentLocale;
    private ResourceBundle resBundle;
    private Locale[] supportedLocales = {
                Locale.ENGLISH,
                Locale.GERMAN
    };

    private R(){
        observers = new ArrayList<>();
        currentLocale = Locale.GERMAN;
        resBundle = ResourceBundle.getBundle("Labels",currentLocale);
    }

    /**
     *  Singleton acess
     * @return the R instance (resource manager )
     */
    public static R getR()
    {
        if(singletonResource == null){
            singletonResource = new R();
        }

        return singletonResource;
    }

    /**
     * Gets a localized string for a specified key
     * @param key the key of the string
     * @returnthe string for the key
     */
    public String label(String key)
    {
        return resBundle.getString(key);
    }

    public void changeLocale(Locale desiredLocale)
    {
        if(Arrays.asList(supportedLocales).contains(desiredLocale))
        {
            currentLocale = desiredLocale;
            resBundle = ResourceBundle.getBundle("Labels",currentLocale);

            //
            notifyObservers();
        }
    }

    /**
     * returns a list of locales supported by our app
     * @return a list of locales supported by our app
     */
    public Locale[] getSupportedLocales()
    {
        return supportedLocales;
    }

    /**
     *
     * @return the current active locale
     */
    public Locale getCurrentLocale(){ return currentLocale; }

    @Override
    public void registerObserver(ResourceObserver observer) {
        if(!observers.contains(observer))
        {
            observers.add(observer);
        }

    }

    @Override
    public void unregisterObserver(ResourceObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

        for(ResourceObserver observer : observers)
        {
            observer.updateFromResources();
        }

    }
}
