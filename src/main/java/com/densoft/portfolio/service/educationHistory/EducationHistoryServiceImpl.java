package com.densoft.portfolio.service.educationHistory;

import com.densoft.portfolio.dto.EducationDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Education;
import com.densoft.portfolio.repository.EducationHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationHistoryServiceImpl implements EducationHistoryService {

    private final EducationHistoryRepository educationHistoryRepository;

    public EducationHistoryServiceImpl(EducationHistoryRepository educationHistoryRepository) {
        this.educationHistoryRepository = educationHistoryRepository;
    }

    @Override
    public List<Education> getEducationHistories() {
        return educationHistoryRepository.findAll();
    }

    @Override
    public Education getEducationHistory(Integer eductionHistoryId) {
        return getExistingEducationHistory(eductionHistoryId);
    }

    @Override
    public Education createEducationHistory(EducationDTO educationDTO) {
        Education education = new Education(
                educationDTO.getInstitution(),
                educationDTO.getLevel(),
                educationDTO.getQualification(),
                educationDTO.getAward(),
                educationDTO.getDurationRange()
        );

        return educationHistoryRepository.save(education);
    }

    @Override
    public Education updateEducationHistory(EducationDTO educationDTO, Integer educationId) {
        Education education = getExistingEducationHistory(educationId);
        education.setInstitution(educationDTO.getInstitution());
        education.setLevel(educationDTO.getLevel());
        education.setQualification(educationDTO.getQualification());
        education.setAward(educationDTO.getAward());
        education.setDurationRange(educationDTO.getDurationRange());
        return educationHistoryRepository.save(education);
    }

    @Override
    public void deleteEducationHistory(Integer educationId) {
        educationHistoryRepository.deleteById(educationId);
    }

    private Education getExistingEducationHistory(Integer eductionHistoryId) {
        return educationHistoryRepository.findById(eductionHistoryId).orElseThrow(() -> new ResourceNotFoundException("eduction history", "Id", String.valueOf(eductionHistoryId)));
    }
}
