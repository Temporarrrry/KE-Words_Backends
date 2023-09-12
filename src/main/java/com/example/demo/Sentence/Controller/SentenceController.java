package com.example.demo.Sentence.Controller;

import com.example.demo.Sentence.DTO.SentenceWithBookmarkResponseDTO;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Sentence.Service.WithBookmark.SentenceWithBookmarkService;
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
    private final SentenceWithBookmarkService sentenceWithBookmarkService;

    @GetMapping("/{sentenceId}")
    public ResponseEntity<SentenceWithBookmarkResponseDTO> findById(@PathVariable Long sentenceId) throws SentenceNotExistException {
        return new ResponseEntity<>(sentenceWithBookmarkService.findById(sentenceId), HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<List<SentenceWithBookmarkResponseDTO>> getSentencesByRandom(@RequestParam(value = "count") int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(sentenceWithBookmarkService.findByRandom(count), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SentenceWithBookmarkResponseDTO>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(sentenceWithBookmarkService.findAll(pageable).getContent(), HttpStatus.OK);
    }
}
