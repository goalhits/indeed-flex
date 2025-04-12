package com.indeedflex;

import java.time.LocalDateTime;
import java.util.Date;


public class WorkerActivity {
    String worker;
    Integer employer;
    Integer role;
    LocalDateTime date;

    public WorkerActivity() {

    }

    public WorkerActivity(String worker,Integer employer,Integer role,LocalDateTime date) {
        this.worker = worker;
        this.employer = employer;
        this.role = role;
        this.date = date;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Integer getEmployer() {
        return employer;
    }

    public void setEmployer(Integer employer) {
        this.employer = employer;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
