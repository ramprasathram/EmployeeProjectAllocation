package com.empproject.allocation.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document
public class Project {

    @Id
    private String projectId;
    private AccountName accountName;
    private String projectName;
    private float allocation;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String remarks;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public AccountName getAccountName() {
        return accountName;
    }

    public void setAccountName(AccountName accountName) {
        this.accountName = accountName;
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

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Project(String projectId, AccountName accountName, String projectName, float allocation, LocalDate projectStartDate, LocalDate projectEndDate, String remarks) {
        this.projectId = projectId;
        this.accountName = accountName;
        this.projectName = projectName;
        this.allocation = allocation;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.remarks = remarks;
    }

    public enum AccountName {
        ANCESTRY, BNYM, CALIBO_LLC, EXPERIAN, FORD, GUARANTEED_RATE, INVOICE_CLOUD, VATTIKUTI_VENTURES_LLC,
        ZIP_CO_US_INC, PAYPAL, JOHNSON_CONTROLS_INC, WESTERN_UNION
    }
}
