package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * trace < debug < info < warn < error
 * default : info
 */

// spring-boot-starter-web 의존성을 추가했다면 @Slf4j 애노테이션으로 바로 log 사용이 가능하다.
@RestController
@Slf4j
public class FooController {
    @GetMapping("/")
    public void log(){
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message"); // default
        log.warn("warn message");
        log.error("error message");
    }
}