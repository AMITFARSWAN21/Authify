package com.authify.controller;

import com.authify.entity.Student;
import com.authify.io.StudentInfo;
import com.authify.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/student")// Optional if calling from frontend
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody StudentInfo dto) {
        String response = service.register(dto);
        Map<String, String> res = new HashMap<>();
        res.put("message", response);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getALlStudent()
    {
        List<Student> students=service.getALlStudent();
        if(students.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }
}
