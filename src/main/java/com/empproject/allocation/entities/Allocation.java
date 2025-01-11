package com.empproject.allocation.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Allocation {

    @Id
    private String allocationId;
    private String employeeId;
    private String projectId;
    private String projectName;
    private float allocation;

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public float getAllocation() {
        return allocation;
    }

    public void setAllocation(float allocation) {
        this.allocation = allocation;
    }

    public Allocation(String employeeId, String projectId, String projectName, float allocation) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.projectName = projectName;
        this.allocation = allocation;
    }
}
