package swingfrontend;

import cells.Cell;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * Created by Andreas on 23.05.2017.
 */
public class NAPACellEditor extends DefaultCellEditor implements TableCellEditor {

    JFormattedTextField textField ;

    public NAPACellEditor(JFormattedTextField textField) {
        super(textField);
        this.textField = textField;
        textField.setFocusLostBehavior(JFormattedTextField.COMMIT);
    }

    /*
        public NAPACellEditor(){
            textField =
        }
    */

    /**
     *  Makes sure that when editing the raw value is displayed, so that formulas can be edited
     * @param table the table beeing worked on
     * @param value the cell in the slot
     * @param isSelected if the cell is selected or not
     * @param row the row
     * @param column the column
     * @return returns a component to be displayed
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        textField.setText("");
        if(value != null){
           // System.out.println("cell not empty " + value.getClass());
            textField.setText(((Cell)value).getRawValue());
        }

      //  textField.setText(row + "," +column); //debug, shows cell coords
        return textField;
    }

    /**
     *  Returns the text the user typed into the field to the NAPATablemodels setValueAt method for processing
     * @return returns the text of the cell
     */
    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }
}