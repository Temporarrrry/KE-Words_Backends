package com.example.demo.Word.Controller;

import com.example.demo.Word.DTO.WordResponseDTO;
import com.example.demo.Word.Service.WordService;
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
    /*@RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveWord(@RequestBody WordRequestDTO wordRequestDTO) {
        wordService.saveWord(wordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteWord(@RequestBody WordRequestDTO wordRequestDTO) {
        wordService.deleteWord(wordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "/findById")
    public ResponseEntity<WordResponseDTO> findById(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(wordService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByEnglish")
    public ResponseEntity<WordResponseDTO> findByEnglish(@RequestParam(value = "english") String english) {
        return new ResponseEntity<>(wordService.findByEnglish(english), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getWordsByRandom")
    public ResponseEntity<List<WordResponseDTO>> getWordsByRandom(@RequestParam(value = "count") int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(wordService.findWordsByRandom(count), HttpStatus.OK);
    }
}
