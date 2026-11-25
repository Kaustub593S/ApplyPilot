package com.applypilot.mapper;

import com.applypilot.dto.job.CreateJobRequest;
import com.applypilot.dto.job.JobResponse;
import com.applypilot.entity.Job;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobResponse toJobResponse(Job req);

    Job toJob(CreateJobRequest req);

}
