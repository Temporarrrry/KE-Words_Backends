package com.example.demo.Quiz.Controller;

import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.Service.QuizService;
import com.example.demo.Quiz.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveQuiz")
    public ResponseEntity<Void> saveQuizResult(Authentication authentication, @RequestBody QuizRequestDTO quizRequestDTO) {
        Long userId = memberService.findIdByUserEmail(((String) authentication.getPrincipal()))
                .orElseThrow(MemberNotExistException::new);
        if (!Objects.equals(quizRequestDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        quizService.saveQuiz(quizRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteQuiz")
    public ResponseEntity<Void> deleteQuizResult(Authentication authentication, @RequestParam(value = "id") Long id) {
        Long userId = memberService.findIdByUserEmail(((String) authentication.getPrincipal()))
                .orElseThrow(MemberNotExistException::new);
        if (!Objects.equals(quizService.findById(id).getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        quizService.deleteQuizById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/generateEnglishQuiz")
    public ResponseEntity<QuizEnglishProblemResponseDTO> generateEnglishQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(quizService.generateEnglishQuiz(count), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/generateKoreanQuiz")
    public ResponseEntity<QuizKoreanProblemResponseDTO> generateKoreanQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(quizService.generateKoreanQuiz(count), HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    public ResponseEntity<QuizResponseDTO> findById(Authentication authentication, @RequestParam(value = "id") Long id) {
        Long userId = memberService.findIdByUserEmail(((String) authentication.getPrincipal()))
                .orElseThrow(MemberNotExistException::new);
        QuizResponseDTO quizResponseDTO = quizService.findById(id);

        if (!Objects.equals(quizResponseDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(quizResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<AllQuizByUserIdResponseDTO> findAllByUserId(Authentication authentication) {
        Long userId = memberService.findIdByUserEmail(((String) authentication.getPrincipal()))
                .orElseThrow(MemberNotExistException::new);
        return new ResponseEntity<>(quizService.findAllByUserId(userId), HttpStatus.OK);
    }
}
