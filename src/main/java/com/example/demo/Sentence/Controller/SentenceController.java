package com.example.demo.Sentence.Controller;

import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Sentence.Service.SentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sentence")
@RequiredArgsConstructor
public class SentenceController {
    private final SentenceService sentenceService;

    @GetMapping("/{sentenceId}")
    public ResponseEntity<SentenceResponseDTO> findById(@PathVariable Long sentenceId) throws SentenceNotExistException {
        return new ResponseEntity<>(sentenceService.findById(sentenceId), HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<List<SentenceResponseDTO>> getSentencesByRandom(@RequestParam(value = "count") int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(sentenceService.findByRandom(count), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SentenceResponseDTO>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(sentenceService.findAll(pageable).getContent(), HttpStatus.OK);
    }
}
