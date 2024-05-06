package com.example.eksamensprojekt.Model;

public class Project {
    private String projectName;
    private int project_id;
    private String status;
    private int task_id;
    private int user_id;

    public Project(String projectName, int project_id, String status, int task_id, int user_id) {
        this.projectName = projectName;
        this.project_id = project_id;
        this.status = status;
        this.task_id = task_id;
        this.user_id = user_id;
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project() {
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProject_id() {
        return project_id;
    }

    public String getStatus() {
        return status;
    }


    public int getUser_id() {
        return user_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
