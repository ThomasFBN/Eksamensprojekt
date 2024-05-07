package com.example.eksamensprojekt.Model;

public class SubProject {
        private int subProjectId;
        private String subProjectName;
        private String status;
        private int projectId;

        public SubProject() {
        }

        public SubProject(int subProjectId, String subProjectName, String status, int projectId) {
            this.subProjectId = subProjectId;
            this.subProjectName = subProjectName;
            this.status = status;
            this.projectId = projectId;
        }

        public int getSubProjectId() {
            return subProjectId;
        }

        public void setSubProjectId(int subProjectId) {
            this.subProjectId = subProjectId;
        }

        public String getSubProjectName() {
            return subProjectName;
        }

        public void setSubProjectName(String subProjectName) {
            this.subProjectName = subProjectName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }
    }


