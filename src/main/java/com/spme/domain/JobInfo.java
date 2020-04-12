package com.spme.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JobInfo {

    private String owner;
    @JsonAlias("jobname")
    private String jobName;
    @JsonAlias("jobid")
    private String jobId;
    private String type;
    private String status;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
