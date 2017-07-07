package sk.kadlecek.productsemantizer.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jobs", uniqueConstraints =
    @UniqueConstraint(columnNames = "title")
)
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on", nullable = false)
    private Date createdOn;

    @Column(name = "input_file_path", nullable = false)
    private String inputFilePath;

    @Column(name = "input_file_column_separator", nullable = false)
    private Character inputFileColumnSeparator;

    @Column(name = "input_file_column_enclosing", nullable = false)
    private Character inputFileColumnEnclosing;

    @OneToOne(optional = false,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "job"
    )
    private JobStatus jobStatus;

    @OneToOne(optional = false,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "job"
    )
    private OdalicConfiguration odalicConfiguration;

    public Job() { super(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public OdalicConfiguration getOdalicConfiguration() {
        return odalicConfiguration;
    }

    public void setOdalicConfiguration(OdalicConfiguration odalicConfiguration) {
        this.odalicConfiguration = odalicConfiguration;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Character getInputFileColumnSeparator() {
        return inputFileColumnSeparator;
    }

    public void setInputFileColumnSeparator(Character inputFileColumnSeparator) {
        this.inputFileColumnSeparator = inputFileColumnSeparator;
    }

    public Character getInputFileColumnEnclosing() {
        return inputFileColumnEnclosing;
    }

    public void setInputFileColumnEnclosing(Character inputFileColumnEnclosing) {
        this.inputFileColumnEnclosing = inputFileColumnEnclosing;
    }
}
