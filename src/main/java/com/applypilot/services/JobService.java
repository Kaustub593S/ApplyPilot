package com.applypilot.services;

import com.applypilot.dto.job.CreateJobRequest;
import com.applypilot.dto.job.JobResponse;
import com.applypilot.entity.Job;
import com.applypilot.exceptions.ResourceNotFoundException;
import com.applypilot.mapper.JobMapper;
import com.applypilot.repository.JobRepository;
import com.applypilot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private  final JobMapper jobMapper;

    @Value("${app.temp-user-id}")   // TEMP: replaced by the logged-in user in Week 2
    private UUID currentUserId;

    public JobResponse createJob(CreateJobRequest req) {
        Job job = jobMapper.toJob(req);
        job.setUser(userRepository.getReferenceById(currentUserId));
        Job savedJob = jobRepository.save(job);
        return jobMapper.toJobResponse(savedJob);
    }
    @Transactional(readOnly = true)
    public JobResponse getJob(UUID id) {
        Job job = jobRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        return jobMapper.toJobResponse(job);
    }

    public Page<JobResponse> getUserJobs(Pageable pageable)
    {
        Page<Job> jobs = jobRepository.findByUserId(currentUserId,pageable);
        return jobs.map(jobMapper::toJobResponse);
    }

    public Page<JobResponse> filterJobs(String company, String role,Pageable pageable) {
        Page<Job> jobs = jobRepository.filterJobs(currentUserId,company,role,pageable);
        return jobs.map(jobMapper::toJobResponse);
    }
}
