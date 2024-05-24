package com.example.eksamensprojekt.RepositoryTest;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findProjectById() throws SQLException {
        Project project = projectRepository.findProjectById(2);
        assertEquals(2, project.getProject_id());

    }

    @Test
    void createProject() throws SQLException {
        Project project = new Project("Test Project", "i gang");
        int userId = 1;
        projectRepository.createProject(project, userId);
        assertNotNull(project.getProject_id());
    }

    @Test
    void findProjectsByUserId() throws SQLException {
        int userId = 3;
        List<Project> projects = projectRepository.findProjectsByUserId(userId);
        assertNotNull(projects);
        assertTrue(projects.size() > 0);
    }

    @Test
    void findSubProjectsByProjectId() throws SQLException {
        int projectId = 1;
        List<SubProject> subProjects = projectRepository.findSubProjectsByProjectId(projectId);
        assertNotNull(subProjects);
    }

    @Test
    void editProject() throws SQLException {
        Project project = new Project("Updated Project Name", "Updated Status");
        int projectId = 1;
        projectRepository.editProject(project, projectId);
    }

    @Test
    void deleteProject() throws SQLException {
        projectRepository.deleteProject(1);
        Project deletedProject = projectRepository.findProjectById(1);
        assertNull(deletedProject);
    }


}
