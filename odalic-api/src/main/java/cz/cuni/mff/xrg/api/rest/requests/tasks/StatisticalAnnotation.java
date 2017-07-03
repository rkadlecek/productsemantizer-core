package cz.cuni.mff.xrg.api.rest.requests.tasks;

import java.util.Map;
import java.util.Set;

public class StatisticalAnnotation {

    private Map<KnowledgeBase, ComponentTypeValue> component;
    private Map<KnowledgeBase, Set<EntityCandidate>> predicate;

    public StatisticalAnnotation() { super(); }

    public Map<KnowledgeBase, ComponentTypeValue> getComponent() {
        return component;
    }

    public void setComponent(Map<KnowledgeBase, ComponentTypeValue> component) {
        this.component = component;
    }

    public Map<KnowledgeBase, Set<EntityCandidate>> getPredicate() {
        return predicate;
    }

    public void setPredicate(Map<KnowledgeBase, Set<EntityCandidate>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "StatisticalAnnotation [component=" + this.component + ", predicate=" + this.predicate
                + "]";
    }
}
