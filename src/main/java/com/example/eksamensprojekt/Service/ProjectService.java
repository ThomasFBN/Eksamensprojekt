package com.example.eksamensprojekt.Service;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(Project project, int user_id) throws SQLException {
        projectRepository.createProject(project, user_id);
    }

    public List<Project> findProjectsByUserId(int userId) throws SQLException {
        return projectRepository.findProjectsByUserId(userId);
    }

    public List<SubProject> findSubProjectsByProjectId(int project_id) throws SQLException {
        return projectRepository.findSubProjectsByProjectId(project_id);
    }


    public Project findProjectById(int id) throws SQLException {
        return projectRepository.findProjectById(id);
    }

    public void deleteProject(int project_id) {
        projectRepository.deleteProject(project_id);
    }

    public void editProject(Project project, int projectId) throws SQLException {
        projectRepository.editProject(project, projectId);
    }


}
