package cz.cuni.mff.xrg.api.rest.requests.tasks;

public class KnowledgeBase implements Comparable<KnowledgeBase> {

    private String name;

    public KnowledgeBase() { super(); }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "KnowledgeBase [name=" + this.name + "]";
    }

    @Override
    public int compareTo(KnowledgeBase o) {
        return this.name.compareTo(o.name);
    }
}
