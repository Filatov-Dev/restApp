package ru.filatov.springrest.restApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class RestController {

    @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello world!";
    }
}
