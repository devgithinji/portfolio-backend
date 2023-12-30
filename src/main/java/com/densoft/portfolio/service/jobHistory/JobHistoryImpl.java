package com.densoft.portfolio.service.jobHistory;

import com.densoft.portfolio.dto.JobDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Job;
import com.densoft.portfolio.repository.JobRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobHistoryImpl implements JobHistoryService {

    private final JobRepository jobRepository;

    private final ObjectMapper objectMapper;

    public JobHistoryImpl(JobRepository jobRepository, ObjectMapper objectMapper) {
        this.jobRepository = jobRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Job> getJobs() {
        return jobRepository.findAll().stream().sorted().toList();
    }

    @Override
    public Job getJob(Integer jobId) {
        return getExistingJob(jobId);
    }


    @Override
    public Job createJob(JobDTO jobDTO) throws JsonProcessingException {
        Job job = new Job(
                jobDTO.getInstitution(),
                jobDTO.getTitle(),
                jobDTO.getDescription(),
                objectMapper.writeValueAsString(jobDTO.getAchievements()),
                jobDTO.getDurationRange()
        );
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(JobDTO jobDTO, Integer jobId) throws JsonProcessingException {
        Job job = getExistingJob(jobId);
        job.setInstitution(jobDTO.getInstitution());
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setAchievements(objectMapper.writeValueAsString(jobDTO.getAchievements()));
        job.setDurationRange(jobDTO.getDurationRange());
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Integer jobId) {
        Job job = getExistingJob(jobId);
        jobRepository.deleteById(job.getId());
    }

    private Job getExistingJob(Integer jobId) {
        return jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("job", "Id", String.valueOf(jobId)));
    }
}
