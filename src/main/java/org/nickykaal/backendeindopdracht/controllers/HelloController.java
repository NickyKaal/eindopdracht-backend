package org.nickykaal.backendeindopdracht.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @CrossOrigin
    @GetMapping("/hello")
    public String sayHello(@AuthenticationPrincipal UserDetails userDetails) {


        if (userDetails != null) {
            return "Hello " + userDetails.getUsername();
        }
        else {
            return "Hello stranger!";
        }
    }

    @CrossOrigin
    @PostMapping("/hello")
    public String sayHello2(@AuthenticationPrincipal UserDetails userDetails) {


        if (userDetails != null) {
            return "Hello " + userDetails.getUsername();
        }
        else {
            return "Hello stranger!";
        }
    }

    @GetMapping("/secret")
    public String tellSecret() {
        return "Secret....";
    }
}
