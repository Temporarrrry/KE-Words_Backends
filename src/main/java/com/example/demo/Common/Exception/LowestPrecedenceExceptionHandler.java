package com.example.demo.Common.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class LowestPrecedenceExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<String> unHandledError(Exception e) {
        log.error("Unhandled Error", e);
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
    //TODO 기본 에러 처리 우선순위 버그 수정
}
