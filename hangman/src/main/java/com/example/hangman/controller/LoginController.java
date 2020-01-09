package com.example.hangman.controller;

import com.example.hangman.facade.LoginFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
public class LoginController {

    private LoginFacade loginFacade;

    @Autowired
    public LoginController(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @RequestMapping(value = "/login/{username}", method = RequestMethod.GET)
    public String login(@PathVariable("username") String username) {
        log.info("User with username {} is trying to login into the system.", username);
        return loginFacade.login(username);
    }

}
