package cz.cuni.mff.xrg.api.rest.requests.tasks;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

public class CellAnnotation {

    private Map<KnowledgeBase, NavigableSet<EntityCandidate>> candidates;
    private Map<KnowledgeBase, Set<EntityCandidate>> chosen;

    public CellAnnotation() { super(); }

    public Map<KnowledgeBase, NavigableSet<EntityCandidate>> getCandidates() {
        return candidates;
    }

    public void setCandidates(Map<KnowledgeBase, NavigableSet<EntityCandidate>> candidates) {
        this.candidates = candidates;
    }

    public Map<KnowledgeBase, Set<EntityCandidate>> getChosen() {
        return chosen;
    }

    public void setChosen(Map<KnowledgeBase, Set<EntityCandidate>> chosen) {
        this.chosen = chosen;
    }

    @Override
    public String toString() {
        return "CellAnnotation [candidates=" + this.candidates + ", chosen=" + this.chosen + "]";
    }
}
