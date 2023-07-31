package com.example.demo.Member.Exception;

import com.example.demo.Member.Controller.MemberController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {
    @ExceptionHandler(loginFailureException.class)
    public ResponseEntity<String> loginFailure(loginFailureException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(MemberExistException.class)
    public ResponseEntity<String> memberExist(MemberExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ResponseEntity<String> memberNotExist(MemberNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> passwordNotMatch(PasswordNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
