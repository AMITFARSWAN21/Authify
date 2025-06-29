package com.authify.service;

import com.authify.entity.Marks;
import com.authify.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    public String enterMarks(Marks marks)
    {
         Optional<Marks> existingStudent=marksRepository.findByName(marks.getName());

        if(existingStudent.isPresent())
        {
            return "Student already exists!";
        }


        Marks savedMarks = marksRepository.save(Marks.builder()
                .name(marks.getName())
                .studClass(marks.getStudClass())
                .course(marks.getCourse())
                .section(marks.getSection())
                .midTerm(marks.getMidTerm())
                .assignment(marks.getAssignment())
                .tutorials(marks.getTutorials())
                .presentation(marks.getPresentation())
                .subject(marks.getSubject())
                .endTerm(marks.getEndTerm())
                .build()
        );

        return "Marks entered successfully for"+ savedMarks.getName();
    }

    public List<Marks> getAllMarks()
    {
       return marksRepository.findAll();
    }

}
