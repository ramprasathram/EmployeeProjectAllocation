package com.empproject.allocation.repository;

import com.empproject.allocation.entities.Allocation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AllocationRepository extends ReactiveMongoRepository<Allocation, String> {
    Flux<Allocation> findByProjectId(String projectId);
    Flux<Allocation> findByEmployeeId(String employeeId);
    Allocation  findByEmployeeIdAndProjectName(String employeeId,String ProjectName);
}

