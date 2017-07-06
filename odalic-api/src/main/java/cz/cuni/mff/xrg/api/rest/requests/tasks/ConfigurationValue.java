package cz.cuni.mff.xrg.api.rest.requests.tasks;

import java.util.NavigableSet;

public class ConfigurationValue {

    private String input;
    private Feedback feedback;
    private NavigableSet<KnowledgeBase> usedBases;
    private KnowledgeBase primaryBase;
    private Integer rowsLimit;
    private Boolean statistical;

    public ConfigurationValue() { super(); }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public NavigableSet<KnowledgeBase> getUsedBases() {
        return usedBases;
    }

    public void setUsedBases(NavigableSet<KnowledgeBase> usedBases) {
        this.usedBases = usedBases;
    }

    public KnowledgeBase getPrimaryBase() {
        return primaryBase;
    }

    public void setPrimaryBase(KnowledgeBase primaryBase) {
        this.primaryBase = primaryBase;
    }

    public Integer getRowsLimit() {
        return rowsLimit;
    }

    public void setRowsLimit(Integer rowsLimit) {
        this.rowsLimit = rowsLimit;
    }

    public Boolean getStatistical() {
        return statistical;
    }

    public void setStatistical(Boolean statistical) {
        this.statistical = statistical;
    }

    @Override
    public String toString() {
        return "ConfigurationValue [input=" + this.input + ", feedback=" + this.feedback
                + ", usedBases=" + this.usedBases + ", primaryBase=" + this.primaryBase + ", rowsLimit="
                + this.rowsLimit + ", statistical=" + this.statistical + "]";
    }
}
