package com.example.eksamensprojekt.Model;

public class Project {
    private String projectName;
    private int project_id;
    private String status;
    private int taskCount;
    private int userCount;
    private int subProjectCount;


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
    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    public int getTaskCount() {
        return taskCount;
    }

    public int getUserCount() {
        return userCount;
    }
    public int getSubProjectCount() {
        return subProjectCount;
    }

    public void setSubProjectCount(int subProjectCount) {
        this.subProjectCount = subProjectCount;
    }


}
