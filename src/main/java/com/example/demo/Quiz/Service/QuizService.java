package com.example.demo.Quiz.Service;

import com.example.demo.Quiz.dto.QuizProblemResponseDTO;
import com.example.demo.Quiz.dto.QuizRequestDTO;
import com.example.demo.Quiz.dto.QuizResponseDTO;

import java.util.List;

public interface QuizService {

    void saveQuiz(QuizRequestDTO quizRequestDTO);
    void deleteQuizById(Long id);

    QuizProblemResponseDTO generateQuiz(int count);

    QuizResponseDTO findById(Long id);
    List<QuizResponseDTO> findByUserId(Long userId);
}
