package com.example.demo.Member.Controller;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Void> register(@RequestBody MemberRequestDTO memberRequestDTO) {
        if (memberService.register(memberRequestDTO)) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @Secured("ROLE_MEMBER")
    @RequestMapping(method = RequestMethod.POST, value = "/resign")
    public ResponseEntity<Void> resign(Authentication authentication) throws Exception {
        String userEmail = ((String) authentication.getPrincipal());
        if (memberService.resign(new MemberRequestDTO(userEmail))) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //READ

    @RequestMapping(method = RequestMethod.GET, value = "/userEmailDuplicatedCheck")
    public ResponseEntity<Void> userEmailDuplicatedCheck(@RequestParam(value = "email") String email) {
        if (memberService.userEmailDupCheck(email))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    //filter에서 진행
    /*@RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody MemberRequestDTO memberRequestDTO) {
        return new ResponseEntity<>(memberService.login(memberRequestDTO), HttpStatus.OK);
    }*/



    @Secured("ROLE_MEMBER")
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ResponseEntity<MemberResponseDTO> getMemberInform(Authentication authentication, @RequestParam String email) throws Exception {
        String userEmail = ((String) authentication.getPrincipal());
        if (!Objects.equals(userEmail, email)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        MemberResponseDTO memberResponseDTO = memberService.findMember(new MemberRequestDTO(userEmail));
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }
}
