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

import java.sql.Date;
import java.util.Calendar;

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
        RequestBuilder rq = get(String.format("/lessons/%d",1)).
                contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title" , is("Jpa")));
    }

    @Test @Transactional @Rollback
    public void testPatchUpdate() throws  Exception{
        Lesson l = new Lesson();
        l.setTitle("SQL");
        l.setDeliveredOn(new Date(20210101));
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
}
