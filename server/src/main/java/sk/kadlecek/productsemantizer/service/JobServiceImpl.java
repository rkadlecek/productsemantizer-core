package sk.kadlecek.productsemantizer.service;

import org.apache.commons.lang3.Conversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.dao.JobDAO;
import sk.kadlecek.productsemantizer.enumeration.JobState;
import sk.kadlecek.productsemantizer.exception.FileStorageException;
import sk.kadlecek.productsemantizer.exception.OdalicApiException;
import sk.kadlecek.productsemantizer.exception.UnsupportedKnowledgeBaseException;
import sk.kadlecek.productsemantizer.util.ConversionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("JobService")
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobDAO jobDAO;
    private final OdalicApiService odalicApiService;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO, OdalicApiService odalicApiService) {
        Assert.notNull(jobDAO, "JobDAO must not be null!");
        Assert.notNull(odalicApiService, "OdalicApiService must not be null!");
        this.jobDAO = jobDAO;
        this.odalicApiService = odalicApiService;
    }

    @Override
    public List<Job> findAll() {
        return ConversionUtils.iterableToList(jobDAO.findAll());

    }

    @Override
    public List<Job> findAllToRefreshOdalicState() {
        return jobDAO.findByJobStatus_JobState(JobState.ODALIC_RUNNING);
    }

    @Override
    public Optional<Job> findById(Long id) {
        return Optional.ofNullable(jobDAO.findOne(id));
    }

    @Override
    @Transactional
    public Job save(Job job) {
        return jobDAO.save(job);
    }

    @Override
    @Transactional
    public List<Job> save(Iterable<Job> jobs) {
        return ConversionUtils.iterableToList(jobDAO.save(jobs));
    }

    @Override
    public void run(Job job) throws OdalicApiException, FileStorageException, UnsupportedKnowledgeBaseException {
        // submit to odalic
        validateIfAllKnowledgeBasesAreSupported(job);
        job.getJobStatus().setOdalicTaskId(odalicApiService.createTask(job));
        odalicApiService.runTask(job);
        job.getJobStatus().setJobState(JobState.ODALIC_RUNNING);
        save(job);
    }

    private void validateIfAllKnowledgeBasesAreSupported(Job job) throws UnsupportedKnowledgeBaseException {
        List<KnowledgeBase> unsupportedKBs = job.getOdalicConfiguration().getKnowledgeBases().stream()
                .filter(kb -> !kb.getActive())
                .collect(Collectors.toList());
        if (!unsupportedKBs.isEmpty()) throw new UnsupportedKnowledgeBaseException(unsupportedKBs);
    }
}
