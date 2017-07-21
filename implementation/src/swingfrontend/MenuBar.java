//Created by Andreas on 17.05.2017.

package swingfrontend;

import swingfrontend.action.*;
import utilities.ResourceObserver;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import static utilities.R.getR;

/**
 *
 *
 * A menu bar with all subcomponents such as menus and buttons. Calls the appropriate events in the table class that owns it.
 */
public class MenuBar implements ResourceObserver{

    JMenuBar menuBar;
    JMenu menuEdit;
    JMenu menuFile;
    JMenu menuAbout;
    JMenuItem menuItemLoad;
    JMenuItem menuItemSave;
    JMenuItem menuItemNew;
    JMenuItem menuItemCharts;
    JMenuItem menuItemAboutInfo;
    JMenuItem menuItemChangeLanguage;

    SwingFrame ownerTable;
    private JTextArea textArea1;

    /**
     *  Creates a menu bar wrapper for JmenuBar with our own button cofigurations
     * @param ownerTable the SwingFrame that this is attached to. Now redundant since SwingFrame is a singleton now
     */
    protected MenuBar(SwingFrame ownerTable)
    {

        this.ownerTable = ownerTable;

        //setup menu buttons
        menuItemLoad = new JMenuItem();
        menuItemSave = new JMenuItem();
        menuItemNew = new JMenuItem();
        menuItemCharts = new JMenuItem();
        menuItemAboutInfo = new JMenuItem();
        menuItemChangeLanguage = new JMenuItem();

        //setup menus
        menuFile = new JMenu();
        menuAbout = new JMenu();
        menuEdit = new JMenu();

        LanguageChangedAction languageChangedAction = new LanguageChangedAction();

        menuAbout.add(menuItemAboutInfo);
        menuItemAboutInfo.setAction(new AboutInfoAction());
        menuAbout.add(menuItemChangeLanguage);
        menuItemChangeLanguage.setAction(languageChangedAction);
        menuFile.add(menuItemNew);
        menuItemNew.setAction(new NewFileAction(ownerTable));
        menuFile.add(menuItemLoad);
        menuItemLoad.setAction(new LoadFileAction(ownerTable));
        menuFile.add(menuItemSave);
        menuItemSave.setAction(new SaveFileAction(ownerTable));
        menuEdit.add(menuItemCharts);
        menuItemCharts.setAction((new ShowCreateChartWindowAction()));

        //setup menu
        menuBar = new JMenuBar();

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuAbout);


        setMenuItemTexts();

        getR().registerObserver(this);
    }

    /**
     * Sets the text for all the menu Items
     */
    private void setMenuItemTexts()
    {
        //by updating the text in one place we have an easy overview of why uses localized strings
        //and strings are always set to the right locale

        menuItemLoad.setText(getR().label("MenuItemLoadFile"));
        menuItemSave.setText(getR().label("MenuItemSave"));
        menuItemNew.setText(getR().label("MenuItemNewFile"));
        menuItemCharts.setText(getR().label("MenuItemCharts"));
        menuItemAboutInfo.setText(getR().label("MenuAbout"));
        menuItemChangeLanguage.setText(getR().label("ChangeLanguage"));

        //setup menus
        menuFile.setText(getR().label("MenuFile"));
        menuAbout.setText(getR().label("Help"));
        menuEdit.setText(getR().label("MenuEdit"));
    }


    protected JMenuBar getJMenuBar()
    {
        return  menuBar;
    }

    @Override
    public void updateFromResources() {
        setMenuItemTexts();
    }
}
