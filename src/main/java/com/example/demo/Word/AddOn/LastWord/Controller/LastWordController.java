package com.example.demo.Word.AddOn.LastWord.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordResponseDTO;
import com.example.demo.Word.AddOn.LastWord.DTO.SaveLastWordRequestDTO;
import com.example.demo.Word.AddOn.LastWord.Service.LastWordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lastWord")
@RequiredArgsConstructor
public class LastWordController {
    private final LastWordService lastWordService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<Void> saveOrUpdate(@RequestBody @Valid SaveLastWordRequestDTO saveLastWordRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        lastWordService.saveOrUpdate(saveLastWordRequestDTO.toInnerDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<Void> delete() {
        Long userId = memberService.findIdByAuthentication();
        lastWordService.delete(new LastWordRequestDTO(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByUserId")
    public ResponseEntity<LastWordResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastWordService.findByUserId(userId), HttpStatus.OK);
    }
}
