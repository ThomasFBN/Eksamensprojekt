package com.example.eksamensprojekt.Model;

public class Task {
    private int taskId;
    private int subProjectId;
    private String taskName;
    private String description;
    private String status;
    private int userId;

    public Task() {
    }

    public Task(int subProjectId, String taskName, String description, String status, int userId) {
        this.subProjectId = subProjectId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
