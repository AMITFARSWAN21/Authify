package com.authify.controller;

import com.authify.entity.NotesSummarizer;
import com.authify.service.NotesSummarizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1.0/summarizer")
@RequiredArgsConstructor
public class NotesSummarizerController {

    private final NotesSummarizerService service;

    @PostMapping("/summarize")
    public ResponseEntity<?> uploadReport(@RequestParam("file") MultipartFile file) {
        try {
            NotesSummarizer notes = service.processAndSaveReport(file);
            return ResponseEntity.ok().body(notes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process PDF: " + e.getMessage());
        }
    }
}
