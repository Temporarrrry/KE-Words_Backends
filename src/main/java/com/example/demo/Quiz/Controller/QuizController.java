package com.example.demo.Quiz.Controller;

import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.Service.QuizService;
import com.example.demo.Quiz.dto.QuizProblemResponseDTO;
import com.example.demo.Quiz.dto.QuizRequestDTO;
import com.example.demo.Quiz.dto.QuizResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    private final MemberService memberService;

    //@Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, value = "/saveQuiz")
    public ResponseEntity<Void> saveQuizResult(@RequestBody QuizRequestDTO quizRequestDTO) {
        quizService.saveQuiz(quizRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, value = "/deleteQuiz")
    public ResponseEntity<Void> deleteQuiz(@RequestBody Long id) {
        quizService.deleteQuizById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/generateQuiz")
    public ResponseEntity<QuizProblemResponseDTO> generateQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(quizService.generateQuiz(count), HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    public ResponseEntity<QuizResponseDTO> findById(Authentication authentication, @RequestBody Long id) {
        Long userId = memberService.findIdByUserEmail(((String) authentication.getPrincipal()))
                .orElseThrow(MemberNotExistException::new);
        QuizResponseDTO quizResponseDTO = quizService.findById(id);

        if (!Objects.equals(quizResponseDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(quizResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<QuizResponseDTO>> findAllByUserId(Authentication authentication) {
        Long userId = memberService.findIdByUserEmail(((String) authentication.getPrincipal()))
                .orElseThrow(MemberNotExistException::new);
        return new ResponseEntity<>(quizService.findByUserId(userId), HttpStatus.OK);
    }
}
