package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Practice.PracticeSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Test.TestSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.SentenceQuizProblemsResultResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceQuizService {

    //SentenceQuizResultResponseDTO checkQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO);
    void deleteQuiz(Long userId, DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO);

    //CommonSentenceQuizProblemsResponseDTO generateFillingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    //OrderingQuizProblemsResponseDTO generateOrderingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    //KoreanWordQuizResponseDTO generateKoreanSentenceQuiz(int count);

    TestSentenceQuizProblemsResponseDTO getFillingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    PracticeSentenceQuizProblemsResponseDTO getFillingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    TestSentenceQuizProblemsResponseDTO getOrderingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    PracticeSentenceQuizProblemsResponseDTO getOrderingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);


    SentenceQuizProblemsResultResponseDTO findById(Long id);

    List<SentenceQuizProblemsResultResponseDTO> findAllByUserId(Long userId, Pageable pageable);

    SentenceQuizProblemsResultResponseDTO gradeQuiz(Long userId, GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO);
}
