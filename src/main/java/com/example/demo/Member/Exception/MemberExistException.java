package com.example.demo.Member.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class MemberExistException extends RuntimeExceptionWithHttpStatus {
    public MemberExistException() {
        super("이미 존재하는 회원입니다.", HttpStatus.CONFLICT);
    }
}
