package com.example.demo.Quiz.WordQuiz.Service;

import com.example.demo.Quiz.WordQuiz.DTO.DeleteWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizProblemResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizResultResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WordQuizService {

    void saveQuiz(WordQuizRequestDTO wordQuizRequestDTO);
    void deleteQuiz(DeleteWordQuizRequestDTO deleteWordQuizRequestDTO);

    WordQuizProblemResponseDTO generateEnglishWordQuiz(int count);

    //KoreanWordQuizResponseDTO generateKoreanWordQuiz(int count);

    WordQuizResultResponseDTO findById(Long id);
    Page<WordQuizResultResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
