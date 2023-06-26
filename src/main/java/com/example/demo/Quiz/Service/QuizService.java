package com.example.demo.Quiz.Service;

import com.example.demo.Quiz.dto.QuizEnglishProblemResponseDTO;
import com.example.demo.Quiz.dto.QuizKoreanProblemResponseDTO;
import com.example.demo.Quiz.dto.QuizRequestDTO;
import com.example.demo.Quiz.dto.QuizResponseDTO;

import java.util.List;

public interface QuizService {

    void saveQuiz(QuizRequestDTO quizRequestDTO);
    void deleteQuizById(Long id);

    QuizEnglishProblemResponseDTO generateEnglishQuiz(int count);

    QuizKoreanProblemResponseDTO generateKoreanQuiz(int count);

    QuizResponseDTO findById(Long id);
    List<QuizResponseDTO> findByUserId(Long userId);
}
