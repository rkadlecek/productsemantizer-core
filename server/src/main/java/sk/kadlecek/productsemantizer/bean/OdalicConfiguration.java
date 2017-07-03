package sk.kadlecek.productsemantizer.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "odalic_configurations")
public class OdalicConfiguration {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnore
    private Job job;

    /**
     * KnowledgeBases which should be used during Odalic processing.
     * First KnowledgeBase in the list will be used as primary knowledge base.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "map_odalic_configurations_knowledge_bases",
            joinColumns = {
                @JoinColumn(name = "odalic_configuration_id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                @JoinColumn(name = "knowledge_base_id", nullable = false, updatable = false)
            }
    )
    private List<KnowledgeBase> knowledgeBases =  new ArrayList<>();

    @Column(name = "rows_limit")
    private Integer rowsLimit;

    @Column(name = "statistical", nullable = false)
    private Boolean statistical;

    public OdalicConfiguration() { super(); }

    public Integer getRowsLimit() {
        return rowsLimit;
    }

    public void setRowsLimit(Integer rowsLimit) {
        this.rowsLimit = rowsLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Boolean getStatistical() {
        return statistical;
    }

    public void setStatistical(Boolean statistical) {
        this.statistical = statistical;
    }

    public List<KnowledgeBase> getKnowledgeBases() {
        return knowledgeBases;
    }

    public void setKnowledgeBases(List<KnowledgeBase> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }
}
