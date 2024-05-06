package com.example.eksamensprojekt.Model;

import java.time.LocalDate;

public class Task {
    private String taskName;
    private int task_id;
    private int project_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int estTime;
    private String status;

    public String getTaskName() {
        return taskName;
    }

    public int getTask_id() {
        return task_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getEstTime() {
        return estTime;
    }

    public String getStatus() {
        return status;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEstTime(int estTime) {
        this.estTime = estTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
