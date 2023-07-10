package com.example.demo.Member.Exception;

import com.example.demo.Member.Controller.MemberController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {
    @ExceptionHandler(loginFailureException.class)
    public ResponseEntity<String> loginFailure(loginFailureException e) {
        return new ResponseEntity<>("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MemberExistException.class)
    public ResponseEntity<String> memberExist(MemberExistException e) {
        return new ResponseEntity<>("이미 존재하는 회원입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ResponseEntity<String> memberNotExist(MemberNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> passwordNotMatch(PasswordNotMatchException e) {
        return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
