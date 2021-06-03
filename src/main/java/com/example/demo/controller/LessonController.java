package com.example.demo.controller;

import com.example.demo.domain.Lesson;
import com.example.demo.repository.LessonRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonRepository repository;

    public LessonController(LessonRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = POST,value = "/create")
    public Lesson create(@RequestBody  Lesson l){
        return this.repository.save(l);
    }

    @RequestMapping(method = GET,value ="/{id}" )
    public Lesson getById(@PathVariable("id")  Long id){
        return this.repository.findById(id).get();
    }

    @RequestMapping(method= GET, value = "")
    public Iterable<Lesson> getAllLessons(){
        return this.repository.findAll();
    }

    @RequestMapping(method = DELETE,value ="/{id}")
    public void deleteById(@PathVariable("id") Long id){
        this.repository.deleteById(id);
    }
}
