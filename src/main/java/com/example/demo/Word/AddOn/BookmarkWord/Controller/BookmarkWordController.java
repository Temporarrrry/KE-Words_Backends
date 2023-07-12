package com.example.demo.Word.AddOn.BookmarkWord.Controller;

import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordResponseDTO;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.DeleteBookmarkWordRequestDTO;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.SaveBookmarkWordRequestDTO;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.AddOn.BookmarkWord.Service.BookmarkWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> saveBookmarkWord(@RequestBody SaveBookmarkWordRequestDTO saveBookmarkWordRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkWordService.saveBookmarkWord(saveBookmarkWordRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> deleteBookmarkWord(@RequestBody DeleteBookmarkWordRequestDTO deleteBookmarkWordRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        bookmarkWordService.deleteBookmarkWord(deleteBookmarkWordRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllByUserId")
    public ResponseEntity<List<BookmarkWordResponseDTO>> findAllByUserId(@PageableDefault(size = 5) Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(bookmarkWordService.findAllByUserId(userId, pageable).getContent(), HttpStatus.OK);
    }
}
