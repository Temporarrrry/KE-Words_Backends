package com.example.demo.Word.Controller;

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

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<WordResponseDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(wordService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/random")
    public ResponseEntity<List<WordResponseDTO>> getWordsByRandom(@RequestParam(value = "count") int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(wordService.findWordsByRandom(count), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<WordResponseDTO>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(wordService.findAll(pageable).getContent(), HttpStatus.OK);
    }
}
