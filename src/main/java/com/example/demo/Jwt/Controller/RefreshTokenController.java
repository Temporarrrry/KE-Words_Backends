package com.example.demo.Jwt.Controller;

import com.example.demo.Jwt.Service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://**", maxAge = 3600) //TODO originPatterns 수정
@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;


    @RequestMapping(method = RequestMethod.POST, value = "/reIssue")
    public ResponseEntity<String> reIssueAccessToken(@RequestBody String refreshToken) throws Exception {
        String accessToken = refreshTokenService.reIssueAccessToken(refreshToken);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
