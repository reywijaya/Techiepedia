package com.enigmacamp.tokonyadia.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Qualifier(value = "hello")
@RequestMapping("/api")
public class Hello {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World!") String name) {
        return "Hello " + name;
    }
}