package com.example.demo.Word.AddOn.BookmarkWord.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordResponseDTO;
import com.example.demo.Word.AddOn.BookmarkWord.Service.BookmarkWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark/word")
@RequiredArgsConstructor
public class BookmarkWordController {
    private final BookmarkWordService bookmarkWordService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/save/{wordId}")
    public ResponseEntity<Void> saveBookmarkWord(@PathVariable Long wordId) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkWordService.saveBookmarkWord(new BookmarkWordRequestDTO(userId, wordId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/{wordId}")
    public ResponseEntity<Void> deleteBookmarkWord(@PathVariable Long wordId) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkWordService.deleteBookmarkWord(new BookmarkWordRequestDTO(userId, wordId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookmarkWordResponseDTO>> findAllByUserId(@PageableDefault(size = 5) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(bookmarkWordService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }
}
