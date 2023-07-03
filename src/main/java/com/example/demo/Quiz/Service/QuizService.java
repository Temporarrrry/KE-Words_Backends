package com.example.demo.Quiz.Service;

import com.example.demo.Quiz.dto.*;

public interface QuizService {

    void saveQuiz(QuizRequestDTO quizRequestDTO);
    void deleteQuizById(Long id);

    QuizEnglishProblemResponseDTO generateEnglishQuiz(int count);

    QuizKoreanProblemResponseDTO generateKoreanQuiz(int count);

    QuizResponseDTO findById(Long id);
    AllQuizByUserIdResponseDTO findAllByUserId(Long userId);
}
