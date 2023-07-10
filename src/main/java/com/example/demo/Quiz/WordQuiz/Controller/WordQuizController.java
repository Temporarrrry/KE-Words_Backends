package com.example.demo.Quiz.WordQuiz.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizProblemResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizResultResponseDTO;
import com.example.demo.Quiz.WordQuiz.Service.WordQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/wordQuiz")
@RequiredArgsConstructor
public class WordQuizController {
    private final WordQuizService wordQuizService;

    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveQuizResult(@RequestBody WordQuizRequestDTO wordQuizRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        if (!Objects.equals(wordQuizRequestDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        wordQuizService.saveQuiz(wordQuizRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteQuizResult(@RequestParam(value = "id") Long id) {
        Long userId = memberService.findIdByAuthentication();
        if (!Objects.equals(wordQuizService.findById(id).getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        wordQuizService.deleteQuizById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/generateEnglishQuiz")
    public ResponseEntity<WordQuizProblemResponseDTO> generateEnglishQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(wordQuizService.generateEnglishWordQuiz(count), HttpStatus.OK);
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "/generateKoreanQuiz")
    public ResponseEntity<KoreanWordQuizResponseDTO> generateKoreanQuizProblem(@RequestParam(value = "count") int count) {
        return new ResponseEntity<>(wordQuizService.generateKoreanWordQuiz(count), HttpStatus.OK);
    }*/



    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    public ResponseEntity<WordQuizResultResponseDTO> findById(@RequestParam(value = "id") Long id) {
        Long userId = memberService.findIdByAuthentication();
        WordQuizResultResponseDTO wordQuizResultResponseDTO = wordQuizService.findById(id);

        if (!Objects.equals(wordQuizResultResponseDTO.getUserId(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(wordQuizResultResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<WordQuizResultResponseDTO>> findAllByUserId(@PageableDefault(size = 10) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(wordQuizService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }
}
