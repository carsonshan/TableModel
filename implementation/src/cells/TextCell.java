package cells;

/**
 * The TextCell only extends the Cell class as it needn't be part of the Observer pattern.
 */
public class TextCell extends Cell {
    private String cellContent;

    public TextCell () {
        super();
        cellType = ECellType.TEXT;
    }

    /**
     * Extended class method
     * @return
     */
    @Override
    public String getRawValue() {
        return cellContent;
    }

    public void setContent(Object content) {
        this.cellContent = content.toString();
    }

    public String getCellContent() {
        return this.cellContent;
    }

    @Override
    public String toString() {
        return cellContent;
    }
}
