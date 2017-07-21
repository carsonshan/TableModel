//Created by Andreas on 18.05.2017.

package swingfrontend;

import cells.Cell;
import swingfrontend.filestrategy.FileLoader;
import swingfrontend.filestrategy.FileSaver;
import utilities.ResourceObserver;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridLayout;
import java.util.ArrayList;

import static utilities.R.getR;

/**
 *
 */
public class SwingFrame  extends JPanel implements  ResourceObserver{

    private static SwingFrame theFrameSingleton = null;

    public static final int BASE_ROW_AMOUNT = 250;
    public static final int BASE_COLUMN_AMOUNT = 200;

    private static JFrame jFrame;

    private ArrayList<SwingTable> sheets;//holds all the sheets
    private int currentActiveSheetIndex;
    private JTabbedPane tabsPanel;

    private SwingFrame() {
        super(new GridLayout(1,0 ));

        //set up the tab panes
        tabsPanel = new JTabbedPane();

        tabsPanel.setTabPlacement(JTabbedPane.BOTTOM);


        add(tabsPanel);

        //set up the first sheet
        sheets = new ArrayList<>(1);

        addNewTable(BASE_COLUMN_AMOUNT,BASE_ROW_AMOUNT);
        currentActiveSheetIndex = 0;


        //register with Resources as observer
        getR().registerObserver(this);


        //we need to add the tab change listener at the end, or else it will fire when initializing the first sheet,
        // leading to an infinate loop of tab changed events on the last tab (new tab).
        tabsPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                int selectedTabIndex = ((JTabbedPane) e.getSource()).getSelectedIndex();
                tabSelected(selectedTabIndex); //dispatch selected tabinfo to the SwingFrame for handling

            }
        });

    }

    public static SwingFrame getSwingFrame(){
        if(theFrameSingleton != null){
            return  theFrameSingleton;
        }
        else{
            theFrameSingleton = new SwingFrame();
            return theFrameSingleton;
        }
    }


    /**
     *  Creates a new JTable with the specified dimentions, registers the event listener.
     * @param cols number of columns at creation
     * @param rows number of rows at creation
     * @return Returns a JTable as specified
     */
    private SwingTable addNewTable(int cols, int rows){
        return addNewTable(new NAPATableModel(rows, cols));
    }

    private SwingTable addNewTable(NAPATableModel tableModel){
        //remove the last tab so we can replace it with the new one we are creating, and then add the "new sheet" tab at the end again
        if(sheets.size() > 0){
            tabsPanel.remove(sheets.size());
        }

        SwingTable tempTable = new SwingTable(tableModel);

        //add to sheets for later
        sheets.add(tempTable);

        //attach to scroll pane for scrolling
        JScrollPane scrollPane = new JScrollPane(tempTable.getTable());

        //add to tabs panel
        tabsPanel.addTab(getR().label("Sheet") + (sheets.size()),null, scrollPane, getR().label("Sheet") + (sheets.size() ));
        tabsPanel.addTab(getR().label("NewSheet"),null, null, getR().label("NewSheetTooltip"));

        tabsPanel.setSelectedIndex(sheets.size() - 1);

        //cosmetics


        return tempTable;
    }



    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {

        try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        //Create and set up the window.
        jFrame = new JFrame(getR().label("AppName"));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Create and set up the content pane.
        SwingFrame newContentPane = SwingFrame.getSwingFrame();
        newContentPane.setOpaque(true); //content panes must be opaque
        jFrame.setContentPane(newContentPane);

        //add menu bar
        MenuBar menuBar = new MenuBar(newContentPane);
        jFrame.setJMenuBar(menuBar.getJMenuBar());

        //Display the window.
        jFrame.pack();
        jFrame.setVisible(true);

    }

    public static JFrame getFrame() {
        return jFrame;
    }

    public void saveFile(){

        ArrayList<NAPATableModel> tableModels = new ArrayList<>(sheets.size());
        for(SwingTable table : sheets){
            tableModels.add(table.getModel());
        }

        //Collections.reverse(tableModels);

        boolean saveSuccess = FileSaver.getInstance().saveFile(tableModels);

        if(saveSuccess){
            JOptionPane.showConfirmDialog(SwingFrame.getFrame(),
                    getR().label("SaveSuccess"),
                    getR().label("Success"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
        }
        else{
            JOptionPane.showConfirmDialog(SwingFrame.getFrame(),
                    getR().label("SaveError"),
                    getR().label("Error"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    public void loadFile(){

        //loads a file and dispatches the parsing via strategy pattern
        //returns null if there was an error
        ArrayList<NAPATableModel> tableModels = FileLoader.getInstance().loadFile();

        if(tableModels == null){
            JOptionPane.showConfirmDialog(SwingFrame.getFrame(),
                    getR().label("ErrorReadingFile"),
                    getR().label("Error"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int i = 0;


        for(NAPATableModel tableModel : tableModels){
            if(i < sheets.size()){
                sheets.get(i).setTableModel(tableModel);
            }
            else{
                addNewTable(tableModel);
            }
            i++;
        }




    }

    public void newDocument(){
        tabsPanel.removeAll();
        sheets.clear();
        addNewTable(BASE_COLUMN_AMOUNT, BASE_ROW_AMOUNT);
    }

    private void tabSelected(int selectedTabIndex){


        if(selectedTabIndex < sheets.size()){
            currentActiveSheetIndex = selectedTabIndex;
        }
        else if(selectedTabIndex == sheets.size()){
            addNewTable(BASE_COLUMN_AMOUNT,BASE_ROW_AMOUNT);
            currentActiveSheetIndex = selectedTabIndex;
        }
    }

    @Override
    public void updateFromResources() {

        //update the text on the tabs
        int i = 0;
        for(; i < sheets.size(); i++){
            tabsPanel.setToolTipTextAt(i ,getR().label("Sheet") + (i + 1));
            tabsPanel.setTitleAt(i, getR().label("Sheet") + (i + 1));
        }
        // the last tab is always the new sheet
        tabsPanel.setToolTipTextAt(i ,getR().label("NewSheet") + (i + 1));
        tabsPanel.setTitleAt(i, getR().label("NewSheet") + (i + 1));
    }

    public Cell getCellValue(String cellName){
        int[] rowCol = mapNameToIndex(cellName);
        return sheets.get(currentActiveSheetIndex).getModel().getCellAT(rowCol[0], rowCol[1]);
    }

    public ArrayList<Cell> getCellValuesInRange(String start, String end, int[] outOptionalRowLength){

        int[] rowColStart = mapNameToIndex(start);
        int[] rowColEnd = mapNameToIndex(end);

        ArrayList<Cell> cellsInRange = new ArrayList<>();

        if(rowColEnd[0] < rowColStart[0] || rowColEnd[1] < rowColStart[1]){
            //value not in range

            return null;
        }
        else {
            if(rowColEnd[1] > rowColStart[1]){
                //we need to get the range

               outOptionalRowLength[0] = rowColEnd[0] - rowColStart[0] + 1; //this is a workaround to inform the caller of the row length
                for(int i = rowColStart[0]; i<= rowColEnd[1]; i++){

                    for(int j = rowColStart[0]; j <= rowColEnd[0]; j++ ){

                        Cell cell = sheets.get(currentActiveSheetIndex).getModel().getCellAT( j,i);
                        if(cell != null){
                            cellsInRange.add(cell);

                        }
                        else {
                            return null;
                        }
                    }
                }


            }
            else{

                outOptionalRowLength[0] = -1; // yeah, we are doing magic numbers now
                //it´s a row so we only need to go through the cols
              //  System.out.println("rowStart: " + rowColStart[0] + " rowEnd: " + rowColEnd[0]);
                for(int i = rowColStart[0]; i <= rowColEnd[0]; i++ ){
                 //   System.out.println("row index: " + i);
                    Cell cell = sheets.get(currentActiveSheetIndex).getModel().getCellAT( i,rowColStart[1]);
                    if(cell != null){
                        cellsInRange.add(cell);

                    }
                    else {
                        return null;
                    }
                }
            }
        }


        return cellsInRange;
    }

    private int[] mapNameToIndex(String inName){


        int[] rowColsInd = new int[]{0,0};
        int asciiAlphabetStart = 65;
        int asciiNumbersStart = 48;
        String name = inName.toUpperCase();

        char[] splitName = name.substring(1).toCharArray();

        System.out.println(splitName);


        if(((int) splitName[0]) < 65 || ((int) splitName[0]) > 132){
            return null; //not a valid cell identifier
        }

        int row = 0;

        ArrayList<Integer> charsAsIntTempArray = new ArrayList<>();
        int i = 0;
        for(char character : splitName){
            int ascii = ((int) character);

            if(ascii >= 65){
                charsAsIntTempArray.add(ascii);
            }
            else{
                int exp = (splitName.length - (i + 1));
                row += (ascii - asciiNumbersStart) * Math.pow(10, exp ) ;

            }

            i++;

        }

        int col = 0;
        for(int j = 0; j < charsAsIntTempArray.size(); j++){ // iterate backwards over the vector
            int exp = (charsAsIntTempArray.size() - (j + 1));
            col += (charsAsIntTempArray.get(j) - asciiAlphabetStart + 1) * (Math.max(26.f * exp, 1.f)) ;

        }

        rowColsInd[0] = (row - 1);
        rowColsInd[1] = (col - 1);



        return rowColsInd;
    }
}


