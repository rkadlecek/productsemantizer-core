package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class ExecutionValue {

    private boolean draft;

    public ExecutionValue() { super(); }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    @Override
    public String toString() {
        return "ExecutionValue [draft=" + this.draft + "]";
    }
}
