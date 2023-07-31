package com.example.demo.Member.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class loginFailureException extends RuntimeExceptionWithHttpStatus {
    public loginFailureException() {
        super("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED);
    }
}
