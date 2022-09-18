package org.alexzhvalev.peopleinfoapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping ("/api/v1/test")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping ("/api/v1/paramtest")
    public String helloWithParameter(@RequestParam (name = "param") String param){

        return String.format("Hello %s !",param);
    }

    @GetMapping ("/api/v1/{pathvar}")
    public String helloWithPathVariable(@PathVariable(name = "pathvar") String name){

        return String.format("GoodBye %s!", name);
    }


}
