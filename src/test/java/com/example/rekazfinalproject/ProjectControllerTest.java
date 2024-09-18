package com.example.rekazfinalproject;
import com.example.rekazfinalproject.Model.Owner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.rekazfinalproject.Controller.ProjectController;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ProjectService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProjectController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    private Project project1, project2;
    private List<Project> projects;

    @BeforeEach
    void setUp() {
        project1 = new Project();
        project1.setId(1);
        project1.setTitle("Project1");
        project1.setDescription("Description1");
        project1.setBudget(1000.0);
        project1.setProjectType("Type1");
        project1.setStartDate(LocalDate.now());
        project1.setDeadline(LocalDate.now().plusDays(10));
        project1.setStatus(Project.ProjectStatus.PENDING);
        project1.setCity("City1");

        project2 = new Project();
        project2.setId(2);
        project2.setTitle("Project2");
        project2.setDescription("Description2");
        project2.setBudget(2000.0);
        project2.setProjectType("Type2");
        project2.setStartDate(LocalDate.now());
        project2.setDeadline(LocalDate.now().plusDays(20));
        project2.setStatus(Project.ProjectStatus.IN_PROGRESS);
        project2.setCity("City2");

        projects = Arrays.asList(project1, project2);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        Mockito.when(projectService.getAllProjects()).thenReturn(projects);
        mockMvc.perform(get("/api/v1/project/get-all-projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Project1"))
                .andExpect(jsonPath("$[1].title").value("Project2"));
    }



    @Test
    public void testGetProjectsByCity() throws Exception {
        Mockito.when(projectService.findByCity("City1")).thenReturn(Arrays.asList(project1));
        mockMvc.perform(get("/api/v1/project/get-projects-by-city/{city}", "City1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Project1"));
    }

    @Test
    public void testGetProjectsByType() throws Exception {
        Mockito.when(projectService.findByProjectType("Type1")).thenReturn(Arrays.asList(project1));
        mockMvc.perform(get("/api/v1/project/get-projects-by-type/{projectType}", "Type1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Project1"));
    }
}
