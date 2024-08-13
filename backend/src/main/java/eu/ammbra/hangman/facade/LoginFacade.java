package eu.ammbra.hangman.facade;

import eu.ammbra.hangman.dto.Role;
import eu.ammbra.hangman.model.User;
import eu.ammbra.hangman.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginFacade {

    private final PlayerService playerService;

    public LoginFacade(PlayerService playerService) {
        this.playerService = playerService;
    }

    public Role login(String username){
        if (username.equals(Role.ADMINISTRATOR.toString())) {
            return Role.valueOf(username);
        }

        Optional<User> optionalPlayer = playerService.getUserByUsername(username);
            if (optionalPlayer.isEmpty()) {
                playerService.createNewPlayer(username);
            }

        return Role.PLAYER;
    }

}
