package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.JobDTO;
import com.densoft.portfolio.model.Job;
import com.densoft.portfolio.service.jobHistory.JobHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/job-history")
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;

    public JobHistoryController(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }

    @GetMapping
    public List<Job> getJobs() {
        return jobHistoryService.getJobs();
    }

    @GetMapping("{jobId}")
    public Job getJob(@PathVariable("jobId") Integer jobId) {
        return jobHistoryService.getJob(jobId);
    }

    @PostMapping
    public Job createJob(@Valid @RequestBody JobDTO jobDTO) throws JsonProcessingException {
        return jobHistoryService.createJob(jobDTO);
    }

    @PutMapping("{jobId}")
    public Job updateJob(@PathVariable("jobId") Integer jobId, @Valid @RequestBody JobDTO jobDTO) throws JsonProcessingException {
        return jobHistoryService.updateJob(jobDTO, jobId);
    }

    @DeleteMapping("{jobId}")
    public String deleteJob(@PathVariable("jobId") Integer jobId) {
        jobHistoryService.deleteJob(jobId);
        return "job deleted successfully";
    }
}
