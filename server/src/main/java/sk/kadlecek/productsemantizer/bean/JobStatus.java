package sk.kadlecek.productsemantizer.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sk.kadlecek.productsemantizer.enumeration.JobState;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Table(name = "job_statuses")
public class JobStatus {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnore
    private Job job;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "job_state", nullable = false)
    private JobState jobState;

    @Column(name = "odalic_task_id")
    private String odalicTaskId;

    public JobStatus() { super(); }

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

    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public String getOdalicTaskId() {
        return odalicTaskId;
    }

    public void setOdalicTaskId(String odalicTaskId) {
        this.odalicTaskId = odalicTaskId;
    }
}
