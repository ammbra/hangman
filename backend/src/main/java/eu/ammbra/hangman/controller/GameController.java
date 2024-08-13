package eu.ammbra.hangman.controller;

import eu.ammbra.hangman.dto.Player;
import eu.ammbra.hangman.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public Player findGame(@PathVariable("username") String username) {
        return playerFacade.findCurrentGame(username);
    }

    @RequestMapping(value = "/game/update/{username}/{letter}", method = RequestMethod.GET)
    @ResponseBody
    public Player updateGame(@PathVariable("username") String username, @PathVariable("letter") Character letter) {
        return playerFacade.updateGame(username, letter);
    }

}
