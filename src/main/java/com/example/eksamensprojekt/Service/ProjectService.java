package com.example.eksamensprojekt.Service;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Repository.ProjectRepository;
import com.example.eksamensprojekt.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(Project project, int user_id) throws SQLException {
        projectRepository.createProject(project,user_id);
    }

}
