package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class Classification {

    private ColumnPosition position;
    private HeaderAnnotation annotation;

    public Classification() { super(); }

    public ColumnPosition getPosition() {
        return position;
    }

    public void setPosition(ColumnPosition position) {
        this.position = position;
    }

    public HeaderAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(HeaderAnnotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "Classification [position=" + this.position + ", annotation=" + this.annotation + "]";
    }
}
