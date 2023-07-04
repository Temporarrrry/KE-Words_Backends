package com.example.demo.BookmarkWord.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.BookmarkWord.Service.BookmarkWordService;
import com.example.demo.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.BookmarkWord.DTO.BookmarkWordResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/bookmarkWord")
@RequiredArgsConstructor
public class BookmarkWordController {
    private final BookmarkWordService bookmarkWordService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Void> saveBookmarkWord(Authentication authentication, @RequestParam(value = "english") String english) {
        Long userId = memberService.findIdByAuthentication(authentication);
        bookmarkWordService.saveBookmarkWord(new BookmarkWordRequestDTO(userId, english));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteBookmarkWord(Authentication authentication, @RequestParam(value = "english") String english) {
        Long userId = memberService.findIdByAuthentication(authentication);
        bookmarkWordService.deleteBookmarkWord(new BookmarkWordRequestDTO(userId, english));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<BookmarkWordResponseDTO>> findAllByUserId(Authentication authentication,
                                                                         @PageableDefault(size = 5) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication(authentication);
        return new ResponseEntity<>(bookmarkWordService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }
}
