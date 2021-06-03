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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        RequestBuilder rq = get(String.format("/lessons/%d",1)).contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title" , is("Jpa")));
    }
}
