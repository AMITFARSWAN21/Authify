package com.authify.controller;
import com.authify.entity.Note;
import com.authify.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<String> saveNote(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        Note note = new Note();
        note.setTitle(title);
        note.setDescription(description);
        note.setDate(LocalDate.parse(date));

        if (file != null && !file.isEmpty()) {
            note.setPdfFile(file.getBytes());
        }

        noteRepository.save(note);
        return ResponseEntity.ok("Note saved successfully");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNotes(@PathVariable Long id)
    {
        if(!noteRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        return noteRepository.findById(id)
                .map(note -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + note.getTitle().replaceAll("\\s+", "-") + ".pdf\"")
                        .body(note.getPdfFile()))
                .orElse(ResponseEntity.notFound().build());
    }

}