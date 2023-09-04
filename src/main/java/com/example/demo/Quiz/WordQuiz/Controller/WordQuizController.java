package com.example.demo.Quiz.WordQuiz.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Quiz.WordQuiz.DTO.DeleteWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.CheckWordQuizRequestDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizProblemsResponseDTO;
import com.example.demo.Quiz.WordQuiz.DTO.WordQuizResultResponseDTO;
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

    @RequestMapping(method = RequestMethod.POST, value = "/check")
    public ResponseEntity<WordQuizResultResponseDTO> checkQuizResult(@RequestBody @Valid CheckWordQuizRequestDTO checkWordQuizRequestDTO) {
        Long userId = memberService.findIdByAuthentication();

        WordQuizResultResponseDTO result = wordQuizService.checkQuiz(checkWordQuizRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    @RequestMapping(method = RequestMethod.GET, value = "/generateWordQuiz")
    public ResponseEntity<WordQuizProblemsResponseDTO> generateWordQuizProblem(@RequestParam(value = "count") int count) {
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
