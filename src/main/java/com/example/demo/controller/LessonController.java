package com.example.demo.controller;

import com.example.demo.domain.Lesson;
import com.example.demo.repository.LessonRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

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

    @RequestMapping(method = PATCH,value = "/{id}")
    public Lesson patchUpdate(@RequestBody Lesson l,@PathVariable Long id){
        Lesson l1 = this.repository.findById(id).get();
        l1.setTitle(l.getTitle());
        l1.setDeliveredOn(l.getDeliveredOn());
        return this.repository.save(l1);
    }

    @RequestMapping(method = GET,value = "/find/{title}")
    public Lesson getAllByTitle(@PathVariable String title){
        return this.repository.findByTitle(title);
    }

    @RequestMapping(method = GET,value = "/between")
    public Iterable<Lesson> getAllBetweenDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date1,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date2){
            return this.repository.findBetweenDate(LocalDate.parse(date1),LocalDate.parse(date2));
    }
}
