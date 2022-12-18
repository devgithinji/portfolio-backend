package com.densoft.portfolio.service.project;

import com.densoft.portfolio.model.Project;
import com.densoft.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject() {
        return null;
    }
}
