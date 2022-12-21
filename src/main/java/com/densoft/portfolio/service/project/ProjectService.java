package com.densoft.portfolio.service.project;

import com.densoft.portfolio.dto.ProjectDTO;
import com.densoft.portfolio.model.Project;

import java.util.List;

public interface ProjectService {


    Project createProject(ProjectDTO projectDTO, String filePath);

    List<Project> getProjectsByTag(int tagId);
}
