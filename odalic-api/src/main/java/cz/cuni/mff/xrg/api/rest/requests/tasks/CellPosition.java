package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class CellPosition {

    private RowPosition rowPosition;
    private ColumnPosition columnPosition;

    public CellPosition() { super(); }

    public RowPosition getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(RowPosition rowPosition) {
        this.rowPosition = rowPosition;
    }

    public ColumnPosition getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(ColumnPosition columnPosition) {
        this.columnPosition = columnPosition;
    }

    @Override
    public String toString() {
        return "" + this.rowPosition + this.columnPosition;
    }

}
