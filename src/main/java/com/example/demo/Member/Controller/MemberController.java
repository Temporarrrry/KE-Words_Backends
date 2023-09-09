package com.example.demo.Member.Controller;

import com.example.demo.Jwt.Exception.AccessTokenNotExistException;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Member.DTO.MemberChangePasswordRequestDTO;
import com.example.demo.Member.DTO.MemberInfoResponseDTO;
import com.example.demo.Member.DTO.MemberRequestDTO;
import com.example.demo.Member.DTO.MemberResignRequestDTO;
import com.example.demo.Member.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        memberService.register(memberRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<Void> resign(HttpServletRequest request, @RequestBody @Valid MemberResignRequestDTO memberResignRequestDTO) {
        String accessToken = jwtTokenProvider.getAccessTokenByRequest(request)
                .orElseThrow(AccessTokenNotExistException::new);

        memberService.resign(accessToken, memberResignRequestDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //READ

    @GetMapping("/{email}/check")
    public ResponseEntity<Void> userEmailDuplicatedCheck(@PathVariable String email) {
        if (memberService.userEmailDupCheck(email)) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    //login은 filter에서 진행


    @PostMapping("/logout") // spring security의 logout은 기본적으로 POST
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.getAccessTokenByRequest(request)
                .orElseThrow(AccessTokenNotExistException::new);

        memberService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDTO> getMemberInform() {
        String userEmail = memberService.findByAuthentication().getUserEmail();

        MemberInfoResponseDTO memberInfoResponseDTO = memberService.findMember(new MemberRequestDTO(userEmail));
        return new ResponseEntity<>(memberInfoResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid MemberChangePasswordRequestDTO memberChangePasswordRequestDTO) {
        String userEmail = memberService.findByAuthentication().getUserEmail();

        memberService.changePasswordByUserEmail(userEmail, memberChangePasswordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
