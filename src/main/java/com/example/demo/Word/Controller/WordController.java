package com.example.demo.Word.Controller;

import com.example.demo.Word.DTO.PageNumberResponseDTO;
import com.example.demo.Word.DTO.WordResponseDTO;
import com.example.demo.Word.Service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/word")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveWord(@RequestBody @Valid WordRequestDTO wordRequestDTO) {
        wordService.saveWord(wordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    //@Secured("ROLE_ADMIN")
    /*@RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteWord(@RequestBody @Valid WordRequestDTO wordRequestDTO) {
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

    @RequestMapping(method = RequestMethod.GET, value = "/findPageNumberById")
    public ResponseEntity<PageNumberResponseDTO> findPageNumberById(@RequestParam(value = "id") Long id, int pageSize) {
        PageNumberResponseDTO pageNumberResponseDTO = wordService.findPageNumberById(id, pageSize);
        return new ResponseEntity<>(pageNumberResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll")
    public ResponseEntity<List<WordResponseDTO>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(wordService.findAll(pageable).getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findLastPageNumber")
    public ResponseEntity<PageNumberResponseDTO> findLastPageNumber(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(wordService.findLastPageNumber(pageable), HttpStatus.OK);
    }
}
