package com.densoft.portfolio.service.jobHistory;

import com.densoft.portfolio.dto.JobDTO;
import com.densoft.portfolio.model.Job;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface JobHistoryService {
    List<Job> getJobs();

    Job getJob(Integer jobId);

    Job createJob(JobDTO jobDTO) throws JsonProcessingException;

    Job updateJob(JobDTO jobDTO, Integer jobId) throws JsonProcessingException;

    void deleteJob(Integer jobId);
}
