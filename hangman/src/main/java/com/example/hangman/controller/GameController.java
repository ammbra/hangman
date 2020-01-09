package com.example.hangman.controller;

import com.example.hangman.dto.AdministratorDTO;
import com.example.hangman.dto.PlayerDTO;
import com.example.hangman.facade.AdministratorFacade;
import com.example.hangman.facade.PlayerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class GameController {

    private PlayerFacade playerFacade;

    @Autowired
    public GameController(PlayerFacade playerFacade) {
        this.playerFacade = playerFacade;
    }

    @RequestMapping(value = "/game/{username}", method = RequestMethod.GET)
    @ResponseBody
    public PlayerDTO getGame(@PathVariable("username") String username) {
        log.info("Retrieving the current game for {}" , username);
        return playerFacade.getCurrentGame(username);
    }

    @RequestMapping(value = "/game/update/{username}/{letter}", method = RequestMethod.GET)
    @ResponseBody
    public PlayerDTO updateGame(@PathVariable("username") String username, @PathVariable("letter") Character letter) {
        log.info("Updating the current game for {}  with the character {}", username, letter);
        return playerFacade.updateGame(username, letter);
    }

}
