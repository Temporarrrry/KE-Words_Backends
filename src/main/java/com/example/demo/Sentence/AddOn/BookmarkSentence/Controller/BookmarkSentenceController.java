package com.example.demo.Sentence.AddOn.BookmarkSentence.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.DeleteBookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.SaveBookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Service.BookmarkSentenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarkSentence")
@RequiredArgsConstructor
public class BookmarkSentenceController {
    
    private final BookmarkSentenceService bookmarkSentenceService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveBookmarkSentence(@RequestBody @Valid SaveBookmarkSentenceRequestDTO saveBookmarkSentenceRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkSentenceService.saveBookmarkSentence(saveBookmarkSentenceRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteBookmarkSentence(@RequestBody @Valid DeleteBookmarkSentenceRequestDTO deleteBookmarkSentenceRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkSentenceService.deleteBookmarkSentence(deleteBookmarkSentenceRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<BookmarkSentenceResponseDTO>> findAllByUserId(@PageableDefault(size = 5) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(bookmarkSentenceService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }
}
