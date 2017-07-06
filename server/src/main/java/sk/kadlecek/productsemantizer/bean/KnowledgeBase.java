package sk.kadlecek.productsemantizer.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cuni.mff.xrg.api.rest.responses.bases.KnowledgeBaseResponse;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "knowledge_bases")
public class KnowledgeBase implements Comparable<KnowledgeBase>{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean active;


    @ManyToMany(fetch = FetchType.EAGER, mappedBy="knowledgeBases")
    @JsonIgnore
    private Set<OdalicConfiguration> odalicConfigurations = new HashSet<>();

    public KnowledgeBase() { super(); }

    @Override
    public int compareTo(KnowledgeBase o) {
        return this.name.compareTo(o.name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<OdalicConfiguration> getOdalicConfigurations() {
        return odalicConfigurations;
    }

    public void setOdalicConfigurations(Set<OdalicConfiguration> odalicConfigurations) {
        this.odalicConfigurations = odalicConfigurations;
    }
}
