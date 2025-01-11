package com.empproject.allocation.service;

import com.empproject.allocation.entities.Allocation;
import com.empproject.allocation.repository.AllocationRepository;
import com.empproject.allocation.repository.EmployeeRepository;
import com.empproject.allocation.repository.ProjectRepository;
import com.empproject.allocation.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;

@Service
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    KafkaTemplate kafkaTemplate;

    public Flux<Employee> getEmployeesWithSkills(String primarySkill, String secondarySkill) {
        log.info("EmployeeService : getEmployeesWithSkills Starts ");
        return employeeRepository.findByPrimarySkillAndSecondarySkill(primarySkill, secondarySkill);
    }

    @Cacheable(value = "employeesNotAllocatedWithPrimarySkill", key = "#primarySkill")
    public Flux<Employee> getEmployeesNotAllocatedToPrimarySkill(String primarySkill) {
        log.info("EmployeeService : getEmployeesNotAllocatedToPrimarySkill Starts ");
        return employeeRepository.findByPrimarySkill(primarySkill)
                .flatMap(employee -> {
                    return allocationRepository.findByEmployeeId(employee.getEmployeeId())
                            .flatMap(allocation -> {
                                return projectRepository.findById(allocation.getProjectId());
                            })
                            .filter(project -> employee.getPrimarySkill().equals(primarySkill))
                            .hasElements()
                            .map(isAllocated -> {
                                if (isAllocated) {
                                    return null;
                                }
                                return employee;
                            });
                })
                .filter(employee -> employee != null)
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Employee> getSecondMostExperiencedEmployeeInProject(String projectId) {
        log.info("EmployeeService : getSecondMostExperiencedEmployeeInProject Starts ");
        return allocationRepository.findByProjectId(projectId)
                .flatMap(allocation -> {
                             return employeeRepository.findById(allocation.getEmployeeId());
                })
                .collectList()
                .map(employees -> {
                    employees.sort(Comparator.comparingInt(Employee::getOverallExperience).reversed());

                    if (employees.size() > 1) {
                        return employees.get(1);
                    } else {
                        return null;
                    }
                });
    }

    public Mono<Allocation> allocateEmployeeToProject(String employeeId, String projectId, String projectName, float allocation) {
        log.info("EmployeeService : allocateEmployeeToProject Starts ");
        String message = "Employee " + employeeId + " has been allocated to project " + projectId +"with allocation"+allocation;
        Allocation allocationObj = new Allocation(employeeId, projectId,projectName, allocation);
        Mono<Allocation>  allocationempproj = allocationRepository.save(allocationObj);
        kafkaTemplate.send("employee-allocation-topic", message);
        log.info("Kafka message sent: " + message);
        log.info("allocateEmployeeToProject Ends ");
        return allocationempproj;
    }

    public boolean hasMoreThanThreeProjects(String employeeId) {
        List<Allocation> allocations = (List<Allocation>) allocationRepository.findByEmployeeId(employeeId);
        return allocations.size() >= 3;
    }

    public Mono<Allocation> modifyProjectAllocation(String employeeId, String projectName, float newAllocation, String remarks) {
        Allocation existingAllocation = allocationRepository.findByEmployeeIdAndProjectName(employeeId, projectName);
        if (existingAllocation != null) {
            existingAllocation.setAllocation(newAllocation);
            return allocationRepository.save(existingAllocation);
        }
        return null;
    }

    @CacheEvict(value = "employeesNotAllocatedWithPrimarySkill", key = "#primarySkill")
    public void clearCache(String primarySkill) {
        // This method clears the cache for the given primary skill
    }
}
