package com.authify.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDate issueDate;

    @Lob
    private byte[] pdfContent;
}
