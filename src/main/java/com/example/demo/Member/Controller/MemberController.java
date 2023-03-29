package com.example.demo.Member.Controller;

import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.SessionMember;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Member.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public boolean register(@RequestBody MemberRequestDTO memberRequestDTO) {
        System.out.println("email: " + memberRequestDTO.getUserEmail());
        System.out.println("password: " + memberRequestDTO.getPassword());
        return memberService.register(memberRequestDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/withdraw")
    public boolean resign(@AuthenticationPrincipal SessionMember sessionMember) {
        return memberService.resign(modelMapper.map(sessionMember.getMember(), MemberRequestDTO.class));
    }
}
