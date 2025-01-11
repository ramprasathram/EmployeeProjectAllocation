package com.empproject.allocation.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.empproject.allocation.entities.Employee;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
    Flux<Employee> findByPrimarySkillAndSecondarySkill(String primarySkill, String secondarySkill);
    Flux<Employee> findByPrimarySkill(String primarySkill);
}
