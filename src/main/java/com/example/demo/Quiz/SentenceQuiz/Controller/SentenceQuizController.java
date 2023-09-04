package com.example.demo.Quiz.SentenceQuiz.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.SentenceQuiz.DTO.CheckSentenceQuizListRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.DeleteSentenceQuizRequestDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz.FillingQuizProblemsResponseDTO;
import com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz.OrderingQuizProblemsResponseDTO;
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

    @RequestMapping(method = RequestMethod.POST, value = "/check")
    public ResponseEntity<SentenceQuizResultResponseDTO> checkQuizResult(@RequestBody @Valid CheckSentenceQuizListRequestDTO checkSentenceQuizListRequestDTO) {
        Long userId = memberService.findIdByAuthentication();

        SentenceQuizResultResponseDTO result = sentenceQuizService
                .checkQuiz(checkSentenceQuizListRequestDTO.toInnerDTO(userId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteQuizResult(@RequestBody @Valid DeleteSentenceQuizRequestDTO deleteSentenceQuizRequestDTO) {
        Long userId = memberService.findIdByAuthentication();

        sentenceQuizService.deleteQuiz(userId, deleteSentenceQuizRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/generateFillingQuiz")
    public ResponseEntity<FillingQuizProblemsResponseDTO> generateFillingQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(sentenceQuizService.generateFillingSentenceQuiz(count), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/generateOrderingQuiz")
    public ResponseEntity<OrderingQuizProblemsResponseDTO> generateOrderingQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(sentenceQuizService.generateOrderingSentenceQuiz(count), HttpStatus.OK);
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
}
