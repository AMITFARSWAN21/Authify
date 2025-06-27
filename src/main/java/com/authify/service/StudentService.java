package com.authify.service;

import com.authify.entity.Student;
import com.authify.io.StudentInfo;
import com.authify.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String register(StudentInfo dto)
    {
      if(studentRepository.existsByEmail(dto.getEmail()))
      {
          return "Email already exists!";
      }


        Student student = Student.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .email(dto.getEmail())
                .rollNumber(dto.getRollNumber())
                .studentClass(dto.getStudentClass())
                .semester(dto.getSemester())
                .build();

      studentRepository.save(student);

      return "Student Registered Successfully!";
    }

    public List<Student> getALlStudent()
    {
       return studentRepository.findAll();
    }

}
