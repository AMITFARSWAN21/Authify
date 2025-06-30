package com.authify.controller;

import com.authify.io.StudyPlanAIRequest;
import com.authify.service.StudyPlanAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/study-plan-ai")
@RequiredArgsConstructor
public class StudyPlanAIController {

    private final StudyPlanAIService studyPlanAIService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePlan(@RequestBody StudyPlanAIRequest request) {
        String studentName = request.getStudentName();
        String plan = studyPlanAIService.generatePersonalizedStudyPlan(studentName, request.getSyllabus());
        return ResponseEntity.ok(plan);
    }
}
