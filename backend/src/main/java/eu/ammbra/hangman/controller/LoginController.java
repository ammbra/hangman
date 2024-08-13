package eu.ammbra.hangman.controller;

import eu.ammbra.hangman.dto.Role;
import eu.ammbra.hangman.facade.LoginFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    private LoginFacade loginFacade;

    @Autowired
    public LoginController(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @RequestMapping(value = "/login/{username}", method = RequestMethod.GET)
    public Role login(@PathVariable("username") String username) {
        return loginFacade.login(username);
    }

}
