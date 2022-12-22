package com.densoft.portfolio.service.educationHistory;

import com.densoft.portfolio.dto.EducationDTO;
import com.densoft.portfolio.model.Education;

import java.util.List;

public interface EducationHistoryService {
    List<Education> getEducationHistories();

    Education getEducationHistory(Integer eductionHistoryId);

    Education createEducationHistory(EducationDTO educationDTO);

    Education updateEducationHistory(EducationDTO educationDTO, Integer educationId);

    void deleteEducationHistory(Integer educationId);
}
