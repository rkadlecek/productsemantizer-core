package sk.kadlecek.productsemantizer.bean.api;

import org.hibernate.validator.constraints.NotEmpty;
import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.bean.JobStatus;
import sk.kadlecek.productsemantizer.enumeration.JobState;

import java.util.Date;

public class JobPayload {

    @NotEmpty
    private String title;
    private String description;
    @NotEmpty
    private String inputFilePath;
    private OdalicConfigurationPayload odalicConfiguration;

    public JobPayload() { super(); }

    public Job toJob() {
        Job job = new Job();
        job.setTitle(getTitle());
        job.setDescription(getDescription());
        job.setInputFilePath(getInputFilePath());
        job.setCreatedOn(new Date());
        job.setOdalicConfiguration(getOdalicConfiguration().toOdalicConfiguration(job));

        JobStatus js = new JobStatus();
        js.setJob(job);
        js.setJobState(JobState.NOT_SUMITTED);

        job.setJobStatus(js);
        return job;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OdalicConfigurationPayload getOdalicConfiguration() {
        return odalicConfiguration;
    }

    public void setOdalicConfiguration(OdalicConfigurationPayload odalicConfiguration) {
        this.odalicConfiguration = odalicConfiguration;
    }
}
