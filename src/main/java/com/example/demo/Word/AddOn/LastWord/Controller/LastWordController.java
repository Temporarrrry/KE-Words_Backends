package com.example.demo.Word.AddOn.LastWord.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordResponseDTO;
import com.example.demo.Word.AddOn.LastWord.Service.LastWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/last/word")
@RequiredArgsConstructor
public class LastWordController {
    private final LastWordService lastWordService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<LastWordResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastWordService.findByUserId(userId), HttpStatus.OK);
    }
}
