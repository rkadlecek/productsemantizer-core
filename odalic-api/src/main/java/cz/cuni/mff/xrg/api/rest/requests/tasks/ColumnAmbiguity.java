package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class ColumnAmbiguity {

    private ColumnPosition position;

    public ColumnAmbiguity() { super(); }

    public ColumnPosition getPosition() {
        return position;
    }

    public void setPosition(ColumnPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ColumnAmbiguity [position=" + this.position + "]";
    }
}
