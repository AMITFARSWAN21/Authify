package com.authify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reports")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotesSummarizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String pdfText;

    @Lob
    private String summary;



}
