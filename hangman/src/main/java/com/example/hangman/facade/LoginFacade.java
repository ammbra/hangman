package com.example.hangman.facade;

import com.example.hangman.model.Player;
import com.example.hangman.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginFacade {

    private static final String ADMIN = "ADMINISTRATOR";
    private static final String PLAYER = "PLAYER";
    private PlayerService playerService;

    public LoginFacade(PlayerService playerService) {
        this.playerService = playerService;
    }

    public String login(String username){
        if(username.equals(ADMIN)){
            return ADMIN;
        }

        Optional<Player> optionalPlayer = playerService.getUserByUsername(username);
        if(!optionalPlayer.isPresent()){
            playerService.createNewPlayer(username);
        }

        return PLAYER;
    }

}
