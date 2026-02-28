package com.example.Quiz.dao;

import com.example.Quiz.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    @Query(value = " SELECT * FROM question q WHERE q.difficulty_level= :difficultyLevel ORDER BY RAND() LIMIT :nques", nativeQuery = true)
    List<Question> findRandomQuestion(String difficultyLevel, int nques);
}
