package com.example.demo.Word.AddOn.LastWord.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordResponseDTO;
import com.example.demo.Word.AddOn.LastWord.Service.LastWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/last/word")
@RequiredArgsConstructor
public class LastWordController {
    private final LastWordService lastWordService;
    private final MemberService memberService;

    @PostMapping("/{wordId}/update")
    public ResponseEntity<Void> saveOrUpdate(@PathVariable Long wordId) {
        Long userId = memberService.findIdByAuthentication();
        lastWordService.saveOrUpdate(new LastWordRequestDTO(userId, wordId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        Long userId = memberService.findIdByAuthentication();
        lastWordService.delete(new LastWordRequestDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<LastWordResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastWordService.findByUserId(userId), HttpStatus.OK);
    }
}
