package sk.kadlecek.productsemantizer.dao;

import org.springframework.data.repository.CrudRepository;
import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.enumeration.JobState;

import java.util.List;

public interface JobDAO extends CrudRepository<Job, Long> {

    List<Job> findByJobStatus_JobState(JobState jobState);

}
