package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class Prefix {

    private String with;
    private String what;

    public Prefix() { super(); }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    @Override
    public String toString() {
        return "Prefix [with=" + this.with + ", what=" + this.what + "]";
    }
}
