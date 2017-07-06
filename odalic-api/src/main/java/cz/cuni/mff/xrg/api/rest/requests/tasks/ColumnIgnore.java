package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class ColumnIgnore {

    private ColumnPosition position;

    public ColumnIgnore() { super(); }

    public ColumnPosition getPosition() {
        return position;
    }

    public void setPosition(ColumnPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ColumnIgnore [position=" + this.position + "]";
    }
}
