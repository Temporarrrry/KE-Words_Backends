package com.example.demo.Member.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(loginFailureException.class)
    public ResponseEntity<String> loginFailure(loginFailureException e) {
        return new ResponseEntity<>("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ResponseEntity<String> MemberNotExist(MemberNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
    }
}
