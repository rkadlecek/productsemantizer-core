package sk.kadlecek.productsemantizer.bean.api;

import org.hibernate.validator.constraints.NotEmpty;
import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.bean.OdalicConfiguration;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;

import java.util.List;
import java.util.stream.Collectors;

public class OdalicConfigurationPayload {

    /**
     * KnowledgeBases which should be used during Odalic processing.
     * First KnowledgeBase in the list will be used as primary knowledge base.
     */
    @NotEmpty
    private List<Long> knowledgeBases;

    private Integer rowsLimit;
    private boolean statistical;

    public OdalicConfigurationPayload() {
        super();
        setStatistical(false);
    }

    public OdalicConfiguration toOdalicConfiguration(Job job) {
        OdalicConfiguration oc = new OdalicConfiguration();
        oc.setJob(job);

        oc.setKnowledgeBases(knowledgeBases
                .stream()
                .map(kbId -> {
                    KnowledgeBase kb = new KnowledgeBase();
                    kb.getOdalicConfigurations().add(oc);
                    kb.setId(kbId);
                    return kb;
                })
                .collect(Collectors.toList()));

        oc.setRowsLimit(getRowsLimit());
        oc.setStatistical(isStatistical());
        return oc;
    }

    public List<Long> getKnowledgeBases() {
        return knowledgeBases;
    }

    public void setKnowledgeBases(List<Long> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

    public Integer getRowsLimit() {
        return rowsLimit;
    }

    public void setRowsLimit(Integer rowsLimit) {
        this.rowsLimit = rowsLimit;
    }

    public boolean isStatistical() {
        return statistical;
    }

    public void setStatistical(boolean statistical) {
        this.statistical = statistical;
    }

}
