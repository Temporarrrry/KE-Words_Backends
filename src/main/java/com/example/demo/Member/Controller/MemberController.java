package com.example.demo.Member.Controller;

import com.example.demo.Jwt.Exception.AccessTokenNotExistException;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Member.DTO.MemberChangePasswordRequestDTO;
import com.example.demo.Member.DTO.MemberInfoResponseDTO;
import com.example.demo.Member.DTO.MemberRequestDTO;
import com.example.demo.Member.DTO.MemberResignRequestDTO;
import com.example.demo.Member.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Void> register(@RequestBody MemberRequestDTO memberRequestDTO) {
        memberService.register(memberRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/resign")
    public ResponseEntity<Void> resign(HttpServletRequest request, @RequestBody MemberResignRequestDTO memberResignRequestDTO) {
        String accessToken = jwtTokenProvider.getAccessTokenByRequest(request)
                .orElseThrow(AccessTokenNotExistException::new);

        memberService.resign(accessToken, memberResignRequestDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //READ

    @RequestMapping(method = RequestMethod.GET, value = "/userEmailDuplicatedCheck")
    public ResponseEntity<Void> userEmailDuplicatedCheck(@RequestParam(value = "email") String email) {
        if (memberService.userEmailDupCheck(email)) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    //filter에서 진행
    /*@RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody MemberRequestDTO memberRequestDTO) {
        return new ResponseEntity<>(memberService.login(memberRequestDTO), HttpStatus.OK);
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/logout") // spring security의 logout은 기본적으로 POST
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.getAccessTokenByRequest(request)
                .orElseThrow(AccessTokenNotExistException::new);

        memberService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public ResponseEntity<MemberInfoResponseDTO> getMemberInform(Authentication authentication, @RequestParam String email) throws Exception {
        String userEmail = ((String) authentication.getPrincipal());
        if (!Objects.equals(userEmail, email)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        MemberInfoResponseDTO memberInfoResponseDTO = memberService.findMember(new MemberRequestDTO(userEmail));
        return new ResponseEntity<>(memberInfoResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public ResponseEntity<Void> changePassword(Authentication authentication,
                                               @RequestBody MemberChangePasswordRequestDTO memberChangePasswordRequestDTO) {
        String userEmail = ((String) authentication.getPrincipal());
        String newPassword = memberChangePasswordRequestDTO.getNewPassword();

        memberService.changePasswordByUserEmail(userEmail, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
