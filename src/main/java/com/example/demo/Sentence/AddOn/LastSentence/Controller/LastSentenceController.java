package com.example.demo.Sentence.AddOn.LastSentence.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Service.LastSentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/last/sentence")
@RequiredArgsConstructor
public class LastSentenceController {
    private final LastSentenceService lastSentenceService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<LastSentenceResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastSentenceService.findByUserId(userId), HttpStatus.OK);
    }
}
