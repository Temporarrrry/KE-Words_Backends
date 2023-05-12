package com.example.demo.Member.Controller;

import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Member.dto.MemberRequestDTO;
import com.example.demo.Member.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Void> register(@RequestBody MemberRequestDTO memberRequestDTO) {
        if (memberService.register(memberRequestDTO)) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @Secured("ROLE_MEMBER")
    @RequestMapping(method = RequestMethod.POST, value = "/resign")
    public ResponseEntity<Void> resign(HttpServletRequest request) {
        Optional<String> OptionalAccessToken = jwtTokenProvider.getAccessTokenByRequest(request);
        if (OptionalAccessToken.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // accessToken이 존재하지 않을 때

        String userEmail = jwtTokenProvider.getUserEmailByAccessToken(OptionalAccessToken.get());
        if (memberService.resign(new MemberRequestDTO(userEmail))) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //READ

    @RequestMapping(method = RequestMethod.POST, value = "/userEmailDuplicatedCheck")
    public ResponseEntity<Void> userEmailDuplicatedCheck(String userEmail) {
        if (memberService.userEmailDupCheck(userEmail)) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody MemberRequestDTO memberRequestDTO) {
        return new ResponseEntity<>(memberService.login(memberRequestDTO), HttpStatus.OK);
    }*/



    @Secured("ROLE_MEMBER")
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ResponseEntity<MemberResponseDTO> getMemberInform(HttpServletRequest request, @RequestParam String email) {
        String userEmail = jwtTokenProvider.getUserEmailByAccessTokenRequest(request);
        if (!Objects.equals(userEmail, email)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        MemberResponseDTO memberResponseDTO = memberService.findMember(new MemberRequestDTO(userEmail));
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }
}
