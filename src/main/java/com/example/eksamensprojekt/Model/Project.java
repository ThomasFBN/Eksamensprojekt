package com.example.eksamensprojekt.Model;

public class Project {
    private String projectName;
    private int project_id;
    private String status;


    private int user_id;

    public Project(String projectName, int project_id, String status) {
        this.projectName = projectName;
        this.project_id = project_id;
        this.status = status;

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


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
