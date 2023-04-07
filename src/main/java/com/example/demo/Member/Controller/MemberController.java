package com.example.demo.Member.Controller;

import com.example.demo.Member.Entity.SessionMember;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Void> register(@RequestBody MemberRequestDTO memberRequestDTO) {
        if (memberService.register(memberRequestDTO)) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resign")
    public ResponseEntity<Void> resign(@AuthenticationPrincipal SessionMember sessionMember) {
        if (sessionMember == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //로그인 되지 않았을 때

        if (memberService.resign(modelMapper.map(sessionMember.getMember(), MemberRequestDTO.class)))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //READ

    @RequestMapping(method = RequestMethod.GET, value = "/userEmailDuplicatedCheck")
    public ResponseEntity<Void> userEmailDuplicatedCheck(String userEmail) {

        if (memberService.userEmailDupCheck(userEmail)) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/getMemberInform")
    public ResponseEntity<MemberResponseDTO> getMemberInform(@AuthenticationPrincipal SessionMember sessionMember) {
        if (sessionMember == null) return new ResponseEntity<>(null, HttpStatus.OK);

        MemberResponseDTO memberResponseDTO = modelMapper.map(sessionMember.getMember(), MemberResponseDTO.class);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }
}
