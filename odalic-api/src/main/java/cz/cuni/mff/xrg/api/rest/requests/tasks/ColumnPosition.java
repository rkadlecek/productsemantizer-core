package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class ColumnPosition {

    private int index;

    public ColumnPosition() { super(); }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "[" + this.index + "]";
    }
}
