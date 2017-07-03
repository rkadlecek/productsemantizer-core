package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class Ambiguity {

    private CellPosition position;

    public Ambiguity() { super(); }

    public CellPosition getPosition() {
        return position;
    }

    public void setPosition(CellPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Ambiguity [position=" + this.position + "]";
    }
}
