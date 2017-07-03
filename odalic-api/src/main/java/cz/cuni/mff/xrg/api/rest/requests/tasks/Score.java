package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class Score {

    private double value;

    public Score() { super(); }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Score [value=" + this.value + "]";
    }
}
