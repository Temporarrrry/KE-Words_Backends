package com.example.demo.Sentence.AddOn.LastSentence.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Service.LastSentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/last/sentence")
@RequiredArgsConstructor
public class LastSentenceController {
    private final LastSentenceService lastSentenceService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/update/{sentenceId}")
    public ResponseEntity<Void> saveOrUpdate(@PathVariable Long sentenceId) {
        Long userId = memberService.findIdByAuthentication();
        lastSentenceService.saveOrUpdate(new LastSentenceRequestDTO(userId, sentenceId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> delete() {
        Long userId = memberService.findIdByAuthentication();
        lastSentenceService.delete(new LastSentenceRequestDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<LastSentenceResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastSentenceService.findByUserId(userId), HttpStatus.OK);
    }
}
