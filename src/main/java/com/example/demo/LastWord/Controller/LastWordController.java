package com.example.demo.LastWord.Controller;

import com.example.demo.LastWord.DTO.DeleteLastWordRequestDTO;
import com.example.demo.LastWord.DTO.LastWordResponseDTO;
import com.example.demo.LastWord.DTO.SaveLastWordRequestDTO;
import com.example.demo.LastWord.Service.LastWordService;
import com.example.demo.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/lastWord")
@RequiredArgsConstructor
public class LastWordController {
    private final LastWordService lastWordService;
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public void save(@RequestBody SaveLastWordRequestDTO saveLastWordRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        lastWordService.save(saveLastWordRequestDTO.toInnerDTO(userId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public void delete(@RequestBody DeleteLastWordRequestDTO deleteLastWordRequestDTO) {
        Long userId = memberService.findIdByAuthentication();
        lastWordService.save(deleteLastWordRequestDTO.toInnerDTO(userId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByUserId")
    public ResponseEntity<LastWordResponseDTO> findByUserId() {
        Long userId = memberService.findIdByAuthentication();
        return new ResponseEntity<>(lastWordService.findByUserId(userId), HttpStatus.OK);
    }
}
