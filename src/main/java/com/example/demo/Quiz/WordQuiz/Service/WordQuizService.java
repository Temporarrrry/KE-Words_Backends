package com.example.demo.Quiz.WordQuiz.Service;

import com.example.demo.Quiz.WordQuiz.DTO.Request.GenerateWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Request.Grade.GradeWordQuizTestRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Practice.WordQuizPracticeProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Result.WordQuizProblemsResultResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Test.WordQuizTestProblemsResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WordQuizService {

    WordQuizProblemsResultResponseDTO gradeQuiz(GradeWordQuizTestRequestDTO gradeWordQuizTestRequestDTO);

    //WordQuizCommonProblemsResponseDTO generateWordQuiz(GenerateWordQuizRequestDTO generateWordQuizRequestDTO);
    WordQuizPracticeProblemsResponseDTO getPractice(GenerateWordQuizRequestDTO generateWordQuizRequestDTO);

    WordQuizTestProblemsResponseDTO getTest(GenerateWordQuizRequestDTO generateWordQuizRequestDTO);

    void deleteQuiz(Long wordQuizId);

    //KoreanWordQuizResponseDTO generateKoreanWordQuiz(int count);

    WordQuizProblemsResultResponseDTO findById(Long id);
    Page<WordQuizProblemsResultResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
