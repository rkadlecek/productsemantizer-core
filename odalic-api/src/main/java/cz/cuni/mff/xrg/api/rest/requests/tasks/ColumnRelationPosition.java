package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class ColumnRelationPosition {

    private ColumnPosition first;
    private ColumnPosition second;

    public ColumnRelationPosition() { super(); }

    public ColumnPosition getFirst() {
        return first;
    }

    public void setFirst(ColumnPosition first) {
        this.first = first;
    }

    public ColumnPosition getSecond() {
        return second;
    }

    public void setSecond(ColumnPosition second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "ColumnRelationPosition [first=" + this.first + ", second=" + this.second + "]";
    }
}
