package com.example.hangman.facade;


import com.example.hangman.HangmanApplication;
import com.example.hangman.dto.PlayerDTO;
import com.example.hangman.model.Status;
import com.example.hangman.service.PlayerService;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.example.hangman.database.Database;
import com.example.hangman.model.Game;
import com.example.hangman.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private PlayerService playerService;

    private Player gamePlayer;

    @BeforeEach
    public void Setup(){
        datatabase.resetDataForTests();
        Map<String, Player> dataToUpdate = datatabase.getData();

        gamePlayer = new Player("ana");

        String randomWord = "ANY";
        String secretWord = "???";
        Game game1 = new Game(gamePlayer, randomWord, secretWord);
        gamePlayer.setCurrentGame(game1);

        dataToUpdate.put(gamePlayer.getUsername(), gamePlayer);

        Player player2 = new Player("sorina");
        dataToUpdate.put(player2.getUsername(), player2);
    }

    @Test
    public void whenGetCurrentGameButThePlayerIsNew(){
        String username = "ana";

        Player player = playerService.getUserByUsername(username).get();
        Game currentGameBefore = player.getCurrentGame();

        PlayerDTO game = playerFacade.getCurrentGame(username);

        player = playerService.getUserByUsername(username).get();
        Game currentGameAfter = player.getCurrentGame();

        assertThat(currentGameBefore).isNotNull();
        assertThat(currentGameAfter).isNotNull();
        assertThat(currentGameAfter.getVisibleWord()).isEqualTo(game.getVisibleWord());
    }


    @Test
    public void whenGetCurrentGameButThePlayerHadAnActiveGame(){
        String username = gamePlayer.getUsername();
        Player player = playerService.getUserByUsername(username).get();
        Game currentGameBefore = player.getCurrentGame();

        PlayerDTO game = playerFacade.getCurrentGame(username);

        player = playerService.getUserByUsername(username).get();
        Game currentGameAfter = player.getCurrentGame();

        assertThat(currentGameBefore).isNotNull();
        assertThat(currentGameAfter).isNotNull();
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsInTheWord(){
        String username = gamePlayer.getUsername();
        PlayerDTO gameBefore = playerFacade.getCurrentGame(username);
        Integer availableCharactersBefore = gameBefore.getAvailableCharacters().size();
        PlayerDTO gameAfterUpdate = playerFacade.updateGame(username, 'A');

        assertThat(gameAfterUpdate).isNotNull();
        assertThat(gameAfterUpdate.getVisibleWord()).isNotNull().isEqualTo("A??");
        assertThat(gameAfterUpdate.getAttemptsLeft()).isNotNull().isEqualTo(gameBefore.getAttemptsLeft());
        assertThat(gameAfterUpdate.getAvailableCharacters().size()).isEqualTo(availableCharactersBefore - 1);
        assertThat(gameAfterUpdate.getStatus()).isEqualTo(Status.ACTIVE.toString());
    }

    @AfterEach
    public void teardown(){
        this.gamePlayer = null;
    }

}
