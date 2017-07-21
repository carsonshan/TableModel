//Created by Andreas on 20.05.2017.

package swingfrontend;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

/**
 *
 */
public class SwingTable implements MouseInputListener {

    private JTable jtable;
    private NAPATableModel tableModel;


    public SwingTable(int rows, int cols){

        this( new NAPATableModel(rows, cols));

    }

    public SwingTable(NAPATableModel tableModel){

        this.tableModel = tableModel;

        jtable = new JTable(tableModel);
        jtable.addMouseListener(this);
        NAPACellEditor cellEditor = new NAPACellEditor(new JFormattedTextField());
        jtable.setDefaultEditor(Object.class , cellEditor);
        cellEditor.setClickCountToStart(2);

        //table cosmetics
        jtable.setPreferredScrollableViewportSize(new Dimension(1024, 512));
        jtable.setFillsViewportHeight(true);

        jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jtable.setRowHeight(30);
        jtable.getColumnModel().setColumnMargin(2);
    }

    public JTable getTable(){

        return jtable;
    }

    public NAPATableModel getModel(){
        return  (NAPATableModel) jtable.getModel();
    }

    public void setTableModel(NAPATableModel tableModel){
        this.tableModel = tableModel;
        jtable.setModel(tableModel);
    }

    /**
     * Mouse input handling
     *
     */

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        /*
        int clickCount = mouseEvent.getClickCount();
        System.out.println(clickCount);
        if(clickCount == 2) {//only handle double clicks because these allow cell editing
            int row = jtable.rowAtPoint(mouseEvent.getPoint());
            int col = jtable.columnAtPoint(mouseEvent.getPoint());

            System.out.println("row: " + row + " col: " + col);
        }
*/
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
