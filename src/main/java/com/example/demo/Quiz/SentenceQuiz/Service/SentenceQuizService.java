package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizResultResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceQuizService {

    SentenceQuizResultResponseDTO checkQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO);
    void deleteQuiz(Long userId, DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO);

    FillingQuizProblemsResponseDTO generateFillingSentenceQuiz(int count);

    OrderingQuizProblemsResponseDTO generateOrderingSentenceQuiz(int count);

    //KoreanWordQuizResponseDTO generateKoreanSentenceQuiz(int count);

    SentenceQuizResultResponseDTO findById(Long id);

    List<SentenceQuizResultResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
