package com.example.hangman.facade;

import com.example.hangman.dto.PlayerDTO;
import com.example.hangman.model.Game;
import com.example.hangman.model.Status;
import com.example.hangman.model.Player;
import com.example.hangman.service.GameService;
import com.example.hangman.service.PlayerService;
import com.example.hangman.service.WordsGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;


@Service
public class PlayerFacade {

    private GameService gameService;
    private PlayerService playerService;
    private WordsGeneratorService wordsService;

    public PlayerFacade(GameService gameService, PlayerService playerService, WordsGeneratorService wordsService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.wordsService = wordsService;
    }

    public PlayerDTO getCurrentGame(String username){
        Player player = getCurrentPlayerPlayer(username);

        Game game;
        boolean isGameActive = nonNull(player.getCurrentGame()) && player.getCurrentGame().getStatus().equals(Status.ACTIVE);
        if (isGameActive) {
            game = player.getCurrentGame();
        } else {
            game = reinitializeGame(player);
        }

        return new PlayerDTO(game);
    }

    private Player getCurrentPlayerPlayer(String username) {
        Optional<Player> optPlayer = playerService.getUserByUsername(username);
        return optPlayer.isPresent() ? optPlayer.get() : playerService.createNewPlayer(username);
    }

    public PlayerDTO updateGame(String username, Character character){
        Player player = playerService.getUserByUsername(username).get();
        gameService.updateGame(player.getCurrentGame(), character);

        return new PlayerDTO(player.getCurrentGame());
    }

    private Game reinitializeGame(Player player) {
        String secretWord = wordsService.getSecretWord();
        String visibleWord = wordsService.getVisibleWord(secretWord);

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        player.setCurrentGame(game);

        return game;
    }

}
