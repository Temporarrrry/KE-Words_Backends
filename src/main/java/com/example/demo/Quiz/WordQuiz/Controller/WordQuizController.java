package com.example.demo.Quiz.WordQuiz.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.WordQuiz.DTO.Request.DeleteWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Request.GenerateWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Request.Grade.GradeWordQuizTestRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Practice.WordQuizPracticeProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Result.WordQuizProblemsResultResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.Response.Test.WordQuizTestProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.Service.WordQuizService;
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
@RequestMapping("/api/wordQuiz")
@RequiredArgsConstructor
public class WordQuizController {
    private final WordQuizService wordQuizService;

    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.GET, value = "/practice")
    public ResponseEntity<WordQuizPracticeProblemsResponseDTO> practice() {
        Long userId = memberService.findIdByAuthentication();

        WordQuizPracticeProblemsResponseDTO practice = wordQuizService
                .getPractice(new GenerateWordQuizRequestDTO(userId, 20, false));
        return new ResponseEntity<>(practice, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test")
    public ResponseEntity<WordQuizTestProblemsResponseDTO> test() {
        Long userId = memberService.findIdByAuthentication();

        WordQuizTestProblemsResponseDTO test = wordQuizService
                .getTest(new GenerateWordQuizRequestDTO(userId, 20, true));
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteQuizResult(@RequestBody @Valid DeleteWordQuizRequestDTO deleteWordQuizRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        Long wordQuizId = deleteWordQuizRequestDTO.getWordQuizId();
        if (!Objects.equals(wordQuizService.findById(wordQuizId).getUserId(), userId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        wordQuizService.deleteQuiz(wordQuizId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    public ResponseEntity<WordQuizProblemsResultResponseDTO> findById(@RequestParam(value = "id") Long id) {
        Long userId = memberService.findIdByAuthentication();
        WordQuizProblemsResultResponseDTO wordQuizProblemsResultResponseDTO = wordQuizService.findById(id);

        if (!Objects.equals(wordQuizProblemsResultResponseDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(wordQuizProblemsResultResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<WordQuizProblemsResultResponseDTO>> findAllByUserId(@PageableDefault(size = 10) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(wordQuizService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/grade")
    public ResponseEntity<WordQuizProblemsResultResponseDTO> gradingQuizTest(@RequestBody @Valid GradeWordQuizTestRequestDTO gradeWordQuizTestRequestDTO) {
        WordQuizProblemsResultResponseDTO wordQuizProblemsResultResponseDTO = wordQuizService
                .gradeQuiz(gradeWordQuizTestRequestDTO);
        return new ResponseEntity<>(wordQuizProblemsResultResponseDTO, HttpStatus.OK);
    }
}
