package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.EducationDTO;
import com.densoft.portfolio.model.Education;
import com.densoft.portfolio.service.educationHistory.EducationHistoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/education-history")
public class EducationHistoryController {

    private final EducationHistoryService educationHistoryService;

    public EducationHistoryController(EducationHistoryService educationHistoryService) {
        this.educationHistoryService = educationHistoryService;
    }

    @GetMapping
    public List<Education> getEducationHistories() {
        return educationHistoryService.getEducationHistories();
    }

    @GetMapping("{educationHistId}")
    public Education getEducationHistory(@PathVariable("educationHistId") Integer educationHistId) {
        return educationHistoryService.getEducationHistory(educationHistId);
    }

    @PostMapping
    public Education createEducation(@Valid EducationDTO educationDTO) {
        return educationHistoryService.createEducationHistory(educationDTO);
    }

    @PutMapping("{educationHistId}")
    public Education updateEducation(@Valid EducationDTO educationDTO, @PathVariable("educationHistId") Integer educationHistId) {
        return educationHistoryService.updateEducationHistory(educationDTO, educationHistId);
    }

    @DeleteMapping("{educationHistId}")
    public String deleteEducation(@PathVariable("educationHistId") Integer educationHistId) {
        educationHistoryService.deleteEducationHistory(educationHistId);
        return "education history deleted";
    }
}
