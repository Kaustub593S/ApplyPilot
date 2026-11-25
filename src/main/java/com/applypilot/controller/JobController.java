package com.applypilot.controller;

import com.applypilot.dto.job.CreateJobRequest;
import com.applypilot.dto.job.JobResponse;
import com.applypilot.services.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RequestMapping("/jobs")
@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getJobs(@PageableDefault(size = 10, sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable)
    {
        return ResponseEntity.ok(jobService.getUserJobs(pageable));
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody CreateJobRequest req)
    {
        return ResponseEntity.ok(jobService.createJob(req));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<JobResponse>> filterJobs(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String role,
            @PageableDefault(size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable)
    {

        return ResponseEntity.ok(jobService.filterJobs(company, role,pageable));
    }
}
