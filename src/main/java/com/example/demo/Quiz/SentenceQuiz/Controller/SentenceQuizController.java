package com.example.demo.Quiz.SentenceQuiz.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Practice.SentenceQuizFillingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Test.SentenceQuizFillingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Practice.SentenceQuizMeaningPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Test.SentenceQuizMeaningTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Practice.SentenceQuizOrderingPracticeProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Test.SentenceQuizOrderingTestProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.SentenceQuizProblemsResultResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.Service.SentenceQuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/quiz/sentence")
@RequiredArgsConstructor
public class SentenceQuizController {
    private final SentenceQuizService sentenceQuizService;

    private final MemberService memberService;

    @PostMapping("/filling/test")
    public ResponseEntity<SentenceQuizFillingTestProblemsResponseDTO> generateFillingTest() {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizFillingTestProblemsResponseDTO fillingTest = sentenceQuizService
                .getFillingTest(new GenerateSentenceQuizRequestDTO(userId, 20, true));
        return new ResponseEntity<>(fillingTest, HttpStatus.OK);
    }

    @GetMapping("/filling/practice")
    public ResponseEntity<SentenceQuizFillingPracticeProblemsResponseDTO> generateFillingPractice() {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizFillingPracticeProblemsResponseDTO fillingPractice = sentenceQuizService
                .getFillingPractice(new GenerateSentenceQuizRequestDTO(userId, 20, false));
        return new ResponseEntity<>(fillingPractice, HttpStatus.OK);
    }

    @PostMapping("/ordering/test")
    public ResponseEntity<SentenceQuizOrderingTestProblemsResponseDTO> generateOrderingTest() {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizOrderingTestProblemsResponseDTO orderingTest = sentenceQuizService
                .getOrderingTest(new GenerateSentenceQuizRequestDTO(userId, 20, true));
        return new ResponseEntity<>(orderingTest, HttpStatus.OK);
    }

    @GetMapping("/ordering/practice")
    public ResponseEntity<SentenceQuizOrderingPracticeProblemsResponseDTO> generateOrderingPractice() {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizOrderingPracticeProblemsResponseDTO orderingPractice = sentenceQuizService
                .getOrderingPractice(new GenerateSentenceQuizRequestDTO(userId, 20, false));
        return new ResponseEntity<>(orderingPractice, HttpStatus.OK);
    }

    @PostMapping("/meaning/test")
    public ResponseEntity<SentenceQuizMeaningTestProblemsResponseDTO> generateMeaningQuizTest() {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizMeaningTestProblemsResponseDTO meaningTest = sentenceQuizService
                .getMeaningTest(new GenerateSentenceQuizRequestDTO(userId, 20, true));
        return new ResponseEntity<>(meaningTest, HttpStatus.OK);
    }

    @GetMapping("/meaning/practice")
    public ResponseEntity<SentenceQuizMeaningPracticeProblemsResponseDTO> generateMeaningPractice() {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizMeaningPracticeProblemsResponseDTO meaningPractice = sentenceQuizService
                .getMeaningPractice(new GenerateSentenceQuizRequestDTO(userId, 20, false));
        return new ResponseEntity<>(meaningPractice, HttpStatus.OK);
    }

    //=====================

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuizResult(@PathVariable Long quizId) {
        Long userId = memberService.findIdByAuthentication();

        sentenceQuizService.deleteQuiz(userId, quizId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/{quizId}")
    public ResponseEntity<SentenceQuizProblemsResultResponseDTO> findById(@PathVariable Long quizId) {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizProblemsResultResponseDTO sentenceQuizProblemsResultResponseDTO = sentenceQuizService.findById(quizId);

        if (!Objects.equals(sentenceQuizProblemsResultResponseDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(sentenceQuizProblemsResultResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SentenceQuizProblemsResultResponseDTO>> findAllByUserId(@PageableDefault(size = 10) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(sentenceQuizService.findAllByUserId(userId, pageable), HttpStatus.OK);
    }

    @PostMapping("/grade/{quizId}")
    public ResponseEntity<SentenceQuizProblemsResultResponseDTO> gradeQuiz(@PathVariable Long quizId, @RequestBody @Valid GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(sentenceQuizService.gradeQuiz(quizId, userId, gradeSentenceQuizTestRequestDTO), HttpStatus.OK);
    }
}
