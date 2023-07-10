package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SentenceQuizService {

    void saveQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO);
    void deleteQuizById(Long id);

    FillingQuizProblemsResponseDTO generateFillingSentenceQuiz(int count);

    OrderingQuizProblemsResponseDTO generateOrderingSentenceQuiz(int count);

    //KoreanWordQuizResponseDTO generateKoreanSentenceQuiz(int count);

    SentenceQuizResponseDTO findById(Long id);

    Page<SentenceQuizResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
