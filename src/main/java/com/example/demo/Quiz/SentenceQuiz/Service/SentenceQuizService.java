package com.example.demo.Quiz.SentenceQuiz.Service;

import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Practice.SentenceQuizFillingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Test.SentenceQuizFillingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Practice.SentenceQuizMeaningPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Test.SentenceQuizMeaningTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Practice.SentenceQuizOrderingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Test.SentenceQuizOrderingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.SentenceQuizProblemsResultForAllResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.SentenceQuizProblemsResultResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceQuizService {

    SentenceQuizMeaningTestProblemsResponseDTO getMeaningTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);
    SentenceQuizMeaningPracticeProblemsResponseDTO getMeaningPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizFillingTestProblemsResponseDTO getFillingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizFillingPracticeProblemsResponseDTO getFillingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizOrderingTestProblemsResponseDTO getOrderingTest(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);

    SentenceQuizOrderingPracticeProblemsResponseDTO getOrderingPractice(GenerateSentenceQuizRequestDTO generateSentenceQuizRequestDTO);


    SentenceQuizProblemsResultResponseDTO findById(Long id);

    List<SentenceQuizProblemsResultForAllResponseDTO> findAllByUserId(Long userId, Pageable pageable);

    SentenceQuizProblemsResultResponseDTO gradeQuiz(Long quizId, Long userId, GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO);
}
