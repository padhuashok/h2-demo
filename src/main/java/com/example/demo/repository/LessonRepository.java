package com.example.demo.repository;

import com.example.demo.domain.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends CrudRepository<Lesson,Long> {
    Lesson findByTitle(String titleName);

    @Query(value = "select * from Lesson where DELIVERED_ON between :date1 and :date2", nativeQuery = true)
    List<Lesson> findBetweenDate(LocalDate date1, LocalDate date2);
}
