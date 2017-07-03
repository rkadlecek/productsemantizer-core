package sk.kadlecek.productsemantizer.service;

import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.exception.DatabaseException;
import sk.kadlecek.productsemantizer.exception.FileStorageException;
import sk.kadlecek.productsemantizer.exception.OdalicApiException;
import sk.kadlecek.productsemantizer.exception.UnsupportedKnowledgeBaseException;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> findAll();

    List<Job> findAllToRefreshOdalicState();

    Optional<Job> findById(Long id);

    Job save(Job job);
    List<Job> save(Iterable<Job> jobs);

    void run(Job job) throws OdalicApiException, FileStorageException, UnsupportedKnowledgeBaseException;

}
