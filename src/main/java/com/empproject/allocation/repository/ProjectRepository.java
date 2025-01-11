package com.empproject.allocation.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.empproject.allocation.entities.Project;

import reactor.core.publisher.Flux;

@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
    Flux<Project> findByAccountName(Project.AccountName accountName);
}

