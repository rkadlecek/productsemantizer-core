package cz.cuni.mff.xrg.api.rest.requests.tasks;

import java.util.Map;
import java.util.Set;

public class Feedback {

    private Map<KnowledgeBase, ColumnPosition> subjectColumnPositions;
    private Set<ColumnIgnore> columnIgnores;
    private Set<Classification> classifications;
    private Set<ColumnAmbiguity> columnAmbiguities;
    private Set<Ambiguity> ambiguities;
    private Set<Disambiguation> disambiguations;
    private Set<ColumnRelation> columnRelations;
    private Set<DataCubeComponent> dataCubeComponents;

    public Map<KnowledgeBase, ColumnPosition> getSubjectColumnPositions() {
        return subjectColumnPositions;
    }

    public void setSubjectColumnPositions(Map<KnowledgeBase, ColumnPosition> subjectColumnPositions) {
        this.subjectColumnPositions = subjectColumnPositions;
    }

    public Set<ColumnIgnore> getColumnIgnores() {
        return columnIgnores;
    }

    public void setColumnIgnores(Set<ColumnIgnore> columnIgnores) {
        this.columnIgnores = columnIgnores;
    }

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(Set<Classification> classifications) {
        this.classifications = classifications;
    }

    public Set<ColumnAmbiguity> getColumnAmbiguities() {
        return columnAmbiguities;
    }

    public void setColumnAmbiguities(Set<ColumnAmbiguity> columnAmbiguities) {
        this.columnAmbiguities = columnAmbiguities;
    }

    public Set<Ambiguity> getAmbiguities() {
        return ambiguities;
    }

    public void setAmbiguities(Set<Ambiguity> ambiguities) {
        this.ambiguities = ambiguities;
    }

    public Set<Disambiguation> getDisambiguations() {
        return disambiguations;
    }

    public void setDisambiguations(Set<Disambiguation> disambiguations) {
        this.disambiguations = disambiguations;
    }

    public Set<ColumnRelation> getColumnRelations() {
        return columnRelations;
    }

    public void setColumnRelations(Set<ColumnRelation> columnRelations) {
        this.columnRelations = columnRelations;
    }

    public Set<DataCubeComponent> getDataCubeComponents() {
        return dataCubeComponents;
    }

    public void setDataCubeComponents(Set<DataCubeComponent> dataCubeComponents) {
        this.dataCubeComponents = dataCubeComponents;
    }

    @Override
    public String toString() {
        return "Feedback [subjectColumnPositions=" + this.subjectColumnPositions + ", columnIgnores="
                + this.columnIgnores + ", columnAmbiguities=" + this.columnAmbiguities
                + ", classifications=" + this.classifications + ", columnRelations=" + this.columnRelations
                + ", disambiguations=" + this.disambiguations + ", ambiguities=" + this.ambiguities
                + ", dataCubeComponents=" + this.dataCubeComponents + "]";
    }

}
