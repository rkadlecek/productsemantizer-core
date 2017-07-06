package sk.kadlecek.productsemantizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.dao.KnowledgeBaseDAO;
import sk.kadlecek.productsemantizer.enumeration.JobState;
import sk.kadlecek.productsemantizer.exception.OdalicApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("SchedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    private final JobService jobService;
    private final KnowledgeBaseService knowledgeBaseService;
    private final OdalicApiService odalicApiService;

    @Autowired
    public SchedulerServiceImpl(JobService jobService,
                                KnowledgeBaseService knowledgeBaseService,
                                OdalicApiService odalicApiService) {
        Assert.notNull(jobService, "JobService must not be null!");
        Assert.notNull(knowledgeBaseService, "KnowledgeBaseService must not be null!");
        Assert.notNull(odalicApiService, "OdalicApiService must not be null!");
        this.jobService = jobService;
        this.knowledgeBaseService = knowledgeBaseService;
        this.odalicApiService = odalicApiService;
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void everyTenSeconds() {
        logger.info("[SCHEDULED] Performing every 10s tasks..");
        List<Job> jobsToRefreshState = jobService.findAllToRefreshOdalicState();
        List<Job> jobsWithRefreshedState = new ArrayList<>();

        logger.info("Found {} jobs with upgradeable state", jobsToRefreshState.size());
        for (Job job : jobsToRefreshState) {
            try {
                JobState newState = odalicApiService.getTaskStatus(job);
                if (job.getJobStatus().getJobState() != newState) {
                    job.getJobStatus().setJobState(odalicApiService.getTaskStatus(job));
                    jobsWithRefreshedState.add(job);
                }
            } catch (OdalicApiException e) {
                logger.error("Failed to retrieve state of job: {} from Odalic: {}", job.getId(), e);
            }
        }
        jobService.save(jobsToRefreshState);
        logger.info("State of {} jobs updated.", jobsWithRefreshedState.size());
        logger.info("[SCHEDULED] Every 10s tasks done.");
    }

    @Scheduled(fixedDelay = 30000)
    @Override
    public void everyThirtySeconds() {
        logger.info("[SCHEDULED] Performing every 30s tasks..");
        try {
            refreshOdalicKnowledgeBases();
        } catch (OdalicApiException e) {
            logger.error("Failed to retrieve list of knowledge bases supported by Odalic: {}", e);
        }
        logger.info("[SCHEDULED] Every 30s tasks done..");
    }


    private void refreshOdalicKnowledgeBases() throws OdalicApiException {
        Set<KnowledgeBase> bases = odalicApiService.getSupportedKnowledgeBases();
        logger.debug("Fetched {} supported knowledge bases.", bases.size());
        knowledgeBaseService.updateListOfSupportedKnowledgeBases(bases);
    }

}
