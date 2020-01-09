package com.example.hangman.service;

import com.example.hangman.model.Game;
import com.example.hangman.model.Player;
import com.example.hangman.model.Status;
import com.example.hangman.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public List<Game> getAllCurrentGames() {
        return gameRepository.getAllCurrentGames();
    }

    public Game startNewGame(Player player, String secretWord, String visibleWord) {
        archiveFinishedGame(player);

        Game currentGame;

        if(isCurrentGameActive(player)) {
            currentGame = player.getCurrentGame();
        } else {
            currentGame = new Game(player, secretWord, visibleWord);
            player.setCurrentGame(currentGame);
            currentGame.setPlayer(player);
        }

        return currentGame;
    }

    public void updateGame(Game game, Character character) {
        updateVisibleWordGivenACharacter(game, character);
        game.getAvailableCharacters().remove(character);
        updateGameStatus(game);
    }

    private void archiveFinishedGame(Player player) {
        if(isThisGameFinished(player)){
            player.getGames().add(player.getCurrentGame());
        }
    }

    private boolean isCurrentGameActive(Player player){
        return nonNull(player.getCurrentGame()) && player.getCurrentGame().getStatus().equals(Status.ACTIVE);
    }

    private boolean isThisGameFinished(Player player){
        return nonNull(player.getCurrentGame()) && !player.getCurrentGame().getStatus().equals(Status.ACTIVE);
    }

    private void updateVisibleWordGivenACharacter(Game game, Character character) {
        String secretWord = game.getSecretWord();
        StringBuilder visibleWord = new StringBuilder(game.getVisibleWord());

        boolean notMatched = Boolean.TRUE;
        int index = secretWord.indexOf(character);
        while (index >= 0) {
            visibleWord.setCharAt(index, character);
            index = secretWord.indexOf(character, index + 1);
            notMatched = Boolean.FALSE;
        }

        if(notMatched){
            game.setAttemptsLeft(game.getAttemptsLeft() - 1);
        }
        game.setVisibleWord(visibleWord.toString());
    }

    private void updateGameStatus(Game game) {
        boolean isGuessedWord = game.getVisibleWord().equals(game.getSecretWord());
        if(isGuessedWord){
            game.setStatus(Status.WON);
            game.setEndDate(LocalDateTime.now());
        }

        boolean isGuessAvailable = game.getAttemptsLeft() == 0 && game.getStatus().equals(Status.ACTIVE);
        if(isGuessAvailable){
            game.setStatus(Status.LOST);
            game.setEndDate(LocalDateTime.now());
        }
    }
}
