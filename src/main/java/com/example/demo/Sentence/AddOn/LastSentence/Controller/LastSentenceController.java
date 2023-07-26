package com.example.demo.Sentence.AddOn.LastSentence.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.SaveLastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Service.LastSentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/lastSentence")
@RequiredArgsConstructor
public class LastSentenceController {
    private final LastSentenceService lastSentenceService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveOrUpdate")
    public ResponseEntity<Void> saveOrUpdate(@RequestBody SaveLastSentenceRequestDTO saveLastSentenceRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        lastSentenceService.saveOrUpdate(saveLastSentenceRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> delete() {
        Long userId = memberService.findIdByAuthentication();
        lastSentenceService.delete(new LastSentenceRequestDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByUserId")
    public ResponseEntity<LastSentenceResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastSentenceService.findByUserId(userId), HttpStatus.OK);
    }
}