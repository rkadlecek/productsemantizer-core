package sk.kadlecek.productsemantizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sk.kadlecek.productsemantizer.bean.Job;
import sk.kadlecek.productsemantizer.bean.api.JobPayload;
import sk.kadlecek.productsemantizer.exception.DatabaseException;
import sk.kadlecek.productsemantizer.service.JobService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/jobs")
public class JobController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        Assert.notNull(jobService, "JobService must not be null!");
        this.jobService = jobService;
    }

    @GetMapping
    public @ResponseBody List<Job> list() throws DatabaseException {
        return jobService.findAll();
    }

    @GetMapping(value="/{id}")
    public @ResponseBody Job findById(@PathVariable long id) throws DatabaseException {
        return jobService.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    public @ResponseBody Job create(@RequestBody JobPayload jobPayload, HttpServletResponse response) throws Exception {
        return respondWithCreated(response, () -> jobService.save(jobPayload.toJob()));
    }

    @PostMapping(value="/{id}/submit")
    public @ResponseBody Job submit(@PathVariable long id) throws Exception {
        Job job = jobService.findById(id).orElseThrow(ResourceNotFoundException::new);
        jobService.run(job);
        return job;
    }
}
