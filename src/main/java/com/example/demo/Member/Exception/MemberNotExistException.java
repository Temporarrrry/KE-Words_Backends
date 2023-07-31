package com.example.demo.Member.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class MemberNotExistException extends RuntimeExceptionWithHttpStatus {
    public MemberNotExistException() {
        super("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);
    }
}
