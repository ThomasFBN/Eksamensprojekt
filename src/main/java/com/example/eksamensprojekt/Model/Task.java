package com.example.eksamensprojekt.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {
    private int taskId;
    private String taskName;
    private int subprojectId;
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate startDate;
    private LocalDate endDate;
    private int estTime;
    private String status;
    private int userId;
    private String username;


    public Task() {
    }

    public Task(String taskName, int subprojectId, LocalDate startDate, LocalDate endDate, int estTime, String status) {
        this.taskName = taskName;
        this.subprojectId = subprojectId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estTime = estTime;
        this.status = status;
    }

    public Task(String taskName, LocalDate startDate, LocalDate endDate, int estTime, String status) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estTime = estTime;
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getEstTime() {
        return estTime;
    }

    public void setEstTime(int estTime) {
        this.estTime = estTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

