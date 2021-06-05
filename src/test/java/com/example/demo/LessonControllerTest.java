package com.example.demo;

import com.example.demo.domain.Lesson;
import com.example.demo.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository lessonRepository;

    @Test
    @Transactional @Rollback
    public void testCreate() throws Exception{
        RequestBuilder rq = post("/lessons/create").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"title\" : \"Jpa2 \"}");

        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id",instanceOf(Number.class)));
    }

    @Test @Transactional @Rollback
    public void testGetLessonById() throws Exception{
        Lesson l = new Lesson();
        l.setTitle("Jpa");
        lessonRepository.save(l);
        RequestBuilder rq = get(String.format("/lessons/%d",l.getId())).
                contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title" , is("Jpa")));
    }

    @Test @Transactional @Rollback
    public void testPatchUpdate() throws  Exception{
        Lesson l = new Lesson();
        l.setTitle("SQL");
        l.setDeliveredOn(LocalDate.of(2021,05,21));
        lessonRepository.save(l);
        System.out.println(l.getId());
        RequestBuilder rq = patch(String.format("/lessons/%d",l.getId())).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                content("{\"title\" : \"Jpa_new\",\"deliveredOn\":\"2017-04-12\"}");

        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title",is("Jpa_new")));
    }

    @Test @Transactional @Rollback
    public void testGetLessonByTitle() throws Exception{
        Lesson l = new Lesson();
        l.setTitle("OOPS");
        lessonRepository.save(l);
        RequestBuilder rq = get(String.format("/lessons/find/%s",l.getTitle())).
                contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title" , is("OOPS")));
    }

    @Test @Transactional @Rollback
    public void testByDates() throws  Exception{
        Lesson l1 = new Lesson();
        l1.setTitle("Exceptions");
        l1.setDeliveredOn(LocalDate.of(2021,04,21));

        Lesson l2 = new Lesson();
        l1.setTitle("Organizing");
        l1.setDeliveredOn(LocalDate.of(2021,05,21));
        Iterable<Lesson> lessons = Arrays.asList(l1,l2);

        lessonRepository.saveAll(lessons);

        RequestBuilder rq = get("/lessons/between").
                queryParam("date1","2020-01-01").
                queryParam("date2","2021-05-21").
                accept(MediaType.APPLICATION_JSON_VALUE);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].title",is("Organizing")));
    }
}
