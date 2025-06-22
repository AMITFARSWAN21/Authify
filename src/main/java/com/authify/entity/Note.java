package com.authify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;


    @Column(length = 1000)
    private String description;

    private LocalDate date;

    @Lob
    @Column(name = "pdf_file",columnDefinition = "LONGBLOB")
    private byte[] pdfFile;


}