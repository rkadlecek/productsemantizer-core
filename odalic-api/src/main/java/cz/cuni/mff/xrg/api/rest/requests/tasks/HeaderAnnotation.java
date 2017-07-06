package cz.cuni.mff.xrg.api.rest.requests.tasks;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

public class HeaderAnnotation {

    private Map<KnowledgeBase, NavigableSet<EntityCandidate>> candidates;
    private Map<KnowledgeBase, Set<EntityCandidate>> chosen;

    public HeaderAnnotation() { super(); }

    public Map<KnowledgeBase, NavigableSet<EntityCandidate>> getCandidates() {
        return candidates;
    }

    public Map<KnowledgeBase, Set<EntityCandidate>> getChosen() {
        return chosen;
    }

    public void setCandidates(Map<KnowledgeBase, NavigableSet<EntityCandidate>> candidates) {
        this.candidates = candidates;
    }

    public void setChosen(Map<KnowledgeBase, Set<EntityCandidate>> chosen) {
        this.chosen = chosen;
    }

    @Override
    public String toString() {
        return "HeaderAnnotation [candidates=" + this.candidates + ", chosen=" + this.chosen + "]";
    }
}
