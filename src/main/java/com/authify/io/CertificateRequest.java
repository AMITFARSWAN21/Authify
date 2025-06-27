package com.authify.io;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {
    private String studentName;
    private String courseName;
    private String instructorName;
    private String institutionName;
    private String completionDate;
    private String duration;
    private String grade;
    private String creditsEarned;
    private String certificateType;
    private String description;
    private String issueDate;
}
