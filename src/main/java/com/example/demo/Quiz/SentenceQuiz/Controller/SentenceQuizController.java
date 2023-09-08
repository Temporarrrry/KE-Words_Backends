package com.example.demo.Quiz.SentenceQuiz.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.GenerateSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade.GradeSentenceQuizTestRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Practice.PracticeSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Test.TestSentenceQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.SentenceQuizResultResponseDTO;
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
@RequestMapping("/api/sentenceQuiz")
@RequiredArgsConstructor
public class SentenceQuizController {
    private final SentenceQuizService sentenceQuizService;

    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/fillingQuiz/test")
    public ResponseEntity<TestSentenceQuizProblemsResponseDTO> generateFillingTest() {
        Long userId = memberService.findIdByAuthentication();
        TestSentenceQuizProblemsResponseDTO fillingTest = sentenceQuizService
                .getFillingTest(new GenerateSentenceQuizRequestDTO(userId, 20, true));
        return new ResponseEntity<>(fillingTest, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fillingQuiz/practice")
    public ResponseEntity<PracticeSentenceQuizProblemsResponseDTO> generateFillingPractice() {
        Long userId = memberService.findIdByAuthentication();
        PracticeSentenceQuizProblemsResponseDTO fillingPractice = sentenceQuizService
                .getFillingPractice(new GenerateSentenceQuizRequestDTO(userId, 20, false));
        return new ResponseEntity<>(fillingPractice, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orderingQuiz/test")
    public ResponseEntity<TestSentenceQuizProblemsResponseDTO> generateOrderingTest() {
        Long userId = memberService.findIdByAuthentication();
        TestSentenceQuizProblemsResponseDTO orderingTest = sentenceQuizService
                .getOrderingTest(new GenerateSentenceQuizRequestDTO(userId, 20, true));
        return new ResponseEntity<>(orderingTest, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orderingQuiz/practice")
    public ResponseEntity<PracticeSentenceQuizProblemsResponseDTO> generateOrderingPractice() {
        Long userId = memberService.findIdByAuthentication();
        PracticeSentenceQuizProblemsResponseDTO orderingPractice = sentenceQuizService
                .getOrderingPractice(new GenerateSentenceQuizRequestDTO(userId, 20, false));
        return new ResponseEntity<>(orderingPractice, HttpStatus.OK);
    }

    //=====================

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteQuizResult(@RequestBody @Valid DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO) {
        Long userId = memberService.findIdByAuthentication();

        sentenceQuizService.deleteQuiz(userId, deleteSentenceQuizRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    public ResponseEntity<SentenceQuizResultResponseDTO> findById(@RequestParam(value = "id") Long id) {
        Long userId = memberService.findIdByAuthentication();
        SentenceQuizResultResponseDTO sentenceQuizResultResponseDTO = sentenceQuizService.findById(id);

        if (!Objects.equals(sentenceQuizResultResponseDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(sentenceQuizResultResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<SentenceQuizResultResponseDTO>> findAllByUserId(@PageableDefault(size = 10) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(sentenceQuizService.findAllByUserId(userId, pageable), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/grade")
    public ResponseEntity<SentenceQuizResultResponseDTO> gradeQuiz(@RequestBody @Valid GradeSentenceQuizTestRequestDTO gradeSentenceQuizTestRequestDTO) {
        return new ResponseEntity<>(sentenceQuizService.gradeQuiz(gradeSentenceQuizTestRequestDTO), HttpStatus.OK);
    }
}
