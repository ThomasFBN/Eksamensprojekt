package com.example.eksamensprojekt.Service;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Repository.ProjectRepository;
import com.example.eksamensprojekt.Repository.SubProjectRepository;
import com.example.eksamensprojekt.Repository.TaskRepository;
import com.example.eksamensprojekt.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubProjectService {
    private SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    public void createSubProject(SubProject subProject) throws SQLException {
        subProjectRepository.createSubProject(subProject);
    }

    public List<Task> findTasksBySubProjectId(int subProject_id) throws SQLException {
        return subProjectRepository.findTasksBySubProjectId(subProject_id);


    }

    public List<User> findUsersBySubProjectId(int subProjectId) throws SQLException {
        return subProjectRepository.findUsersBySubProjectId(subProjectId);
    }
}