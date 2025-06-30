package com.authify.io;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanAIRequest {

    private String studentName;
    private Map<String, String> syllabus;
}
