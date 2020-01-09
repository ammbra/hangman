package com.example.hangman.controller;

import com.example.hangman.dto.AdministratorDTO;
import com.example.hangman.facade.AdministratorFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class AdministratorController {

    private AdministratorFacade adminFacade;

    @Autowired
    public AdministratorController(AdministratorFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public List<AdministratorDTO> manage() {
        log.info("Retrieving all the games for the game admin");
        return adminFacade.getAllGames();
    }

}
