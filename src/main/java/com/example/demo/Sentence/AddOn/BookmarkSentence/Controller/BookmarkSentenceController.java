package com.example.demo.Sentence.AddOn.BookmarkSentence.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Service.BookmarkSentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark/sentence")
@RequiredArgsConstructor
public class BookmarkSentenceController {
    
    private final BookmarkSentenceService bookmarkSentenceService;
    private final MemberService memberService;

    @PostMapping("/{sentenceId}")
    public ResponseEntity<Void> saveBookmarkSentence(@PathVariable Long sentenceId) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkSentenceService.saveBookmarkSentence(new BookmarkSentenceRequestDTO(userId, sentenceId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{sentenceId}")
    public ResponseEntity<Void> deleteBookmarkSentence(@PathVariable Long sentenceId) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkSentenceService.deleteBookmarkSentence(new BookmarkSentenceRequestDTO(userId, sentenceId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkSentenceResponseDTO>> findAllByUserId(@PageableDefault(size = 5) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(bookmarkSentenceService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }
}
