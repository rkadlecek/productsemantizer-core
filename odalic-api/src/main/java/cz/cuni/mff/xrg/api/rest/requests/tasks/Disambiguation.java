package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class Disambiguation {

    private CellPosition position;
    private CellAnnotation annotation;

    public Disambiguation() { super(); }

    public CellPosition getPosition() {
        return position;
    }

    public void setPosition(CellPosition position) {
        this.position = position;
    }

    public CellAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(CellAnnotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "Disambiguation [position=" + this.position + ", annotation=" + this.annotation + "]";
    }
}
