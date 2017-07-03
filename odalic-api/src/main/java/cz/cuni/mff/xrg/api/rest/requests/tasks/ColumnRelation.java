package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class ColumnRelation {

    private ColumnRelationPosition position;
    private ColumnRelationAnnotation annotation;

    public ColumnRelation() { super(); }

    public ColumnRelationPosition getPosition() {
        return position;
    }

    public ColumnRelationAnnotation getAnnotation() {
        return annotation;
    }

    public void setPosition(ColumnRelationPosition position) {
        this.position = position;
    }

    public void setAnnotation(ColumnRelationAnnotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "ColumnRelation [position=" + this.position + ", annotation=" + this.annotation + "]";
    }
}
