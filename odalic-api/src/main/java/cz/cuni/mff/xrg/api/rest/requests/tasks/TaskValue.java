package cz.cuni.mff.xrg.api.rest.requests.tasks;

import java.util.Date;

public class TaskValue {

    private String id;
    private String description;
    private Date created;
    private ConfigurationValue configuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ConfigurationValue getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationValue configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "TaskValue [id=" + this.id + ", description=" + this.description + ", created="
                + this.created + ", configuration=" + this.configuration + "]";
    }
}
