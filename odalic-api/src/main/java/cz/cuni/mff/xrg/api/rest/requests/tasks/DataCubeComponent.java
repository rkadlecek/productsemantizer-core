package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class DataCubeComponent {

    private ColumnPosition position;
    private StatisticalAnnotation annotation;

    public DataCubeComponent() { super(); }

    public ColumnPosition getPosition() {
        return position;
    }

    public void setPosition(ColumnPosition position) {
        this.position = position;
    }

    public StatisticalAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(StatisticalAnnotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "DataCubeCompoment [position=" + this.position + ", annotation=" + this.annotation + "]";
    }
}
