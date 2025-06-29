package com.authify.controller;

import com.authify.entity.Marks;
import com.authify.repository.MarksRepository;
import com.authify.service.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @PostMapping("/register")
    public ResponseEntity<String> addNumber(@RequestBody Marks marks)
    {
        String result=marksService.enterMarks(marks);

        if(result.contains("Student already exists!"))
        {
            ResponseEntity.status(409).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<Marks> getAllMarks()
    {
        return marksService.getAllMarks();
    }


}
