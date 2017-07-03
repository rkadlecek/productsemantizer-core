package sk.kadlecek.productsemantizer.service;

import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.enumeration.JobState;
import sk.kadlecek.productsemantizer.exception.FileStorageException;
import sk.kadlecek.productsemantizer.exception.OdalicApiException;

import java.util.Set;

public interface OdalicApiService {

    /**
     * Uploads the job input file to ODALIC.
     * Returns Odalic file identifier, which can be used for
     * TaskSubmit API call.
     * @param job
     * @return
     * @throws OdalicApiException
     */
    String uploadJobFile(Job job) throws OdalicApiException, FileStorageException;

    /**
     * Creates a new Odalic task from given job.
     * @param job
     * @return ID of the odalic task
     * @throws OdalicApiException
     */
    String createTask(Job job) throws OdalicApiException, FileStorageException;

    void runTask(Job job) throws OdalicApiException;

    JobState getTaskStatus(Job job) throws OdalicApiException;

    Set<KnowledgeBase> getSupportedKnowledgeBases() throws OdalicApiException;

}
