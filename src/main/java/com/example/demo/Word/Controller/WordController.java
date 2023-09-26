package com.example.demo.Word.Controller;

import com.example.demo.Word.DTO.WordWithBookmarkResponseDTO;
import com.example.demo.Word.Service.WithBookmark.WordWIthBookmarkService;
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
    private final WordWIthBookmarkService wordWIthBookmarkService;

    @GetMapping("/{wordId}")
    public ResponseEntity<WordWithBookmarkResponseDTO> findById(@PathVariable Long wordId) {
        return new ResponseEntity<>(wordWIthBookmarkService.findById(wordId), HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<List<WordWithBookmarkResponseDTO>> getWordsByRandom(@RequestParam(value = "count") int count) {
        if (200 < count) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(wordWIthBookmarkService.findWordsByRandom(count), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WordWithBookmarkResponseDTO>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(wordWIthBookmarkService.findAll(pageable).getContent(), HttpStatus.OK);
    }
}
