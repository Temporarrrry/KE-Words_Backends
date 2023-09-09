package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Practice.SentenceQuizFillingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Test.SentenceQuizFillingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Practice.SentenceQuizMeaningPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Test.SentenceQuizMeaningTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Practice.SentenceQuizOrderingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Test.SentenceQuizOrderingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.SentenceQuizProblemsResultResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceQuizService {

    //SentenceQuizResultResponseDTO checkQuiz(SentenceQuizRequestDTO sentenceQuizRequestDTO);
    void deleteQuiz(Long userId, DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO);

    //CommonSentenceQuizProblemsResponseDTO generateFillingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    //OrderingQuizProblemsResponseDTO generateOrderingSentenceQuiz(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    //KoreanWordQuizResponseDTO generateKoreanSentenceQuiz(int count);

    SentenceQuizMeaningTestProblemsResponseDTO getMeaningTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);
    SentenceQuizMeaningPracticeProblemsResponseDTO getMeaningPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizFillingTestProblemsResponseDTO getFillingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizFillingPracticeProblemsResponseDTO getFillingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizOrderingTestProblemsResponseDTO getOrderingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizOrderingPracticeProblemsResponseDTO getOrderingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);


    SentenceQuizProblemsResultResponseDTO findById(Long id);

    List<SentenceQuizProblemsResultResponseDTO> findAllByUserId(Long userId, Pageable pageable);

    SentenceQuizProblemsResultResponseDTO gradeQuiz(Long userId, GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO);
}
