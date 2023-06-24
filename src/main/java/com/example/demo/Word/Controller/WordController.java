package com.example.demo.Word.Controller;

import com.example.demo.Word.Service.WordService;
import com.example.demo.Word.dto.WordRequestDTO;
import com.example.demo.Word.dto.WordResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/word")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    //@Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, value = "/saveWord")
    public ResponseEntity<Void> saveWord(@RequestBody WordRequestDTO wordRequestDTO) {
        wordService.saveWord(wordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, value = "/deleteWord")
    public ResponseEntity<Void> deleteWord(@RequestBody WordRequestDTO wordRequestDTO) {
        wordService.deleteWord(wordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByEnglish")
    public ResponseEntity<WordResponseDTO> findByEnglish(@RequestBody String english) {
        return new ResponseEntity<>(wordService.findByEnglish(english), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getWords")
    public ResponseEntity<List<WordResponseDTO>> get10Words(@RequestBody int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(wordService.findWords(count), HttpStatus.OK);
    }
}
