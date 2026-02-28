package com.example.Quiz.Services;

import com.example.Quiz.Models.Question;
import com.example.Quiz.Models.QuestionWrapper;
import com.example.Quiz.Models.Quiz;
import com.example.Quiz.Models.UserResponse;
import com.example.Quiz.dao.QuestionDao;
import com.example.Quiz.dao.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String difficultyLevel, int nques, String title) {
        List<Question> questions = questionDao.findRandomQuestion(difficultyLevel, nques);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(),
                    q.getOption4());
            questionsForUser.add(qw);

        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<UserResponse> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int count = 0;
        int i = 0;
        for (UserResponse it : responses) {
            if (it.getResponded().equals(questions.get(i).getRightAnswer()))
                count++;

            i++;
        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
