package com.example.Quiz.Controller;

import com.example.Quiz.Models.QuestionWrapper;
import com.example.Quiz.Models.UserResponse;
import com.example.Quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizservice;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String difficultyLevel, @RequestParam int nques, @RequestParam String title) {
        return quizservice.createQuiz(difficultyLevel, nques, title);

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@PathVariable Integer id) {
        return quizservice.getQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> SubmitQuiz(@PathVariable Integer id, @RequestBody List<UserResponse> responses) {
        return quizservice.calculateScore(id, responses);

    }


}
