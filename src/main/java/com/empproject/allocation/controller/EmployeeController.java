package com.empproject.allocation.controller;

import com.empproject.allocation.entities.Allocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.empproject.allocation.entities.Employee;
import com.empproject.allocation.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/skills")
    public Flux<Employee> getEmployeesWithSkills(@RequestParam String primarySkill, @RequestParam String secondarySkill) {
        log.info("EmployeeController : getEmployeesWithSkills Starts ");
        return employeeService.getEmployeesWithSkills(primarySkill, secondarySkill);
    }

    @GetMapping("/notAllocatedWithPrimarySkill")
    public Flux<Employee> getEmployeesNotAllocatedToPrimarySkill(@RequestParam String primarySkill) {
        log.info("EmployeeController : getEmployeesNotAllocatedToPrimarySkill Starts ");
        return employeeService.getEmployeesNotAllocatedToPrimarySkill(primarySkill);
    }

    @PostMapping("/allocate")
    public Mono<Allocation> allocateEmployeeToProject(@RequestParam String employeeId,
                                                      @RequestParam String projectId,
                                                      @RequestParam String projectName,
                                                      @RequestParam float allocation) {
        log.info("EmployeeController : allocateEmployeeToProject Starts ");
        return employeeService.allocateEmployeeToProject(employeeId, projectId, projectName, allocation);
    }

    @PutMapping("/modify/{employeeId}/{projectName}")
    public ResponseEntity<Object> modifyProjectAllocation(
            @PathVariable String employeeId,
            @PathVariable String projectName,
            @RequestParam float newAllocation,
            @RequestParam String remarks) {
        log.info("EmployeeController : modifyProjectAllocation Starts ");
        if (employeeService.hasMoreThanThreeProjects(employeeId)) {
            return ResponseEntity.badRequest().body("An employee cannot be allocated to more than 3 projects.");
        }
        Mono<Allocation> updatedAllocation = employeeService.modifyProjectAllocation(employeeId, projectName, newAllocation, remarks);
        if (updatedAllocation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAllocation);
    }

    @GetMapping("/secondMostExperienced/{projectId}")
    public Mono<Employee> getSecondMostExperiencedEmployeeInProject(@PathVariable String projectId) {
        log.info("EmployeeController : getSecondMostExperiencedEmployeeInProject Starts ");
        return employeeService.getSecondMostExperiencedEmployeeInProject(projectId);
    }
}
