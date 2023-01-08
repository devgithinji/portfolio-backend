package com.densoft.portfolio.service.project;

import com.densoft.portfolio.dto.ProjectCreateDTO;
import com.densoft.portfolio.dto.ProjectResponse;
import com.densoft.portfolio.dto.ProjectUpdateDTO;
import com.densoft.portfolio.model.Project;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

    List<ProjectResponse> getProjects(String tag);

    ProjectResponse getProject(Integer projectId);

    Project createProject(ProjectCreateDTO projectDTO) throws IOException;

    Project updateProject(ProjectUpdateDTO projectUpdateDTO, Integer projectId) throws IOException;

    Project togglePublishStatus(Integer projectId);

    void deleteProject(Integer projectId);

}
