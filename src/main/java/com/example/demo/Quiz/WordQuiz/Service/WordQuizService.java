package com.example.demo.Quiz.WordQuiz.Service;

import com.example.demo.Quiz.WordQuiz.DTO.WordQuizProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizResultResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WordQuizService {

    WordQuizResultResponseDTO checkQuiz(WordQuizRequestDTO wordQuizRequestDTO);
    void deleteQuiz(Long wordQuizId);

    WordQuizProblemsResponseDTO generateEnglishWordQuiz(int count);

    //KoreanWordQuizResponseDTO generateKoreanWordQuiz(int count);

    WordQuizResultResponseDTO findById(Long id);
    Page<WordQuizResultResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
