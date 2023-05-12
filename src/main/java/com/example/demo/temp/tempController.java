package com.example.demo.temp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class tempController {



    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ResponseEntity<String> register() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
