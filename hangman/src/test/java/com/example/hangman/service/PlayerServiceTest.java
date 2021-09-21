package com.example.hangman.service;

import com.example.hangman.HangmanApplication;
import com.example.hangman.database.Database;
import com.example.hangman.model.Game;
import com.example.hangman.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerServiceTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private WordsGeneratorService wordsGeneratorService;

    @Autowired
    private PlayerService userService;

    @BeforeEach
    public void setup(){
        datatabase.resetDataForTests();
        addOnePlayerTwoGames("ana");
        addOnePlayerTwoGames("alsacia");
        addOnePlayerTwoGames("sorina");
    }

    @Test
    public void createNewPlayer(){
        String username = "newPlayerUsername";
        Player player = userService.createNewPlayer(username);

        assertThat(player).isNotNull();
        assertThat(player.getUsername()).isNotNull().isEqualTo(username);
    }

    @Test
    public void retrieveAllUsers(){
        List<Player> allPlayers = userService.getAllPlayers();

        assertThat(allPlayers).isNotNull();
        assertThat(allPlayers).isNotEmpty();
        assertThat(allPlayers).hasSize(3);
    }

    @Test
    public void getUserByUsername(){
        Optional<Player> player = userService.getUserByUsername("ana");
        assertThat(player).isPresent();
    }

    private void addOnePlayerTwoGames(String username) {
        Map<String, Player> dataToUpdate = datatabase.getData();

        Player player1 = new Player(username);

        String randomWord = wordsGeneratorService.getSecretWord();
        String secretWord = wordsGeneratorService.getVisibleWord(randomWord);
        Game game1 = new Game(player1, randomWord, secretWord);
        Game game2 = new Game(player1, randomWord, secretWord);
        player1.setCurrentGame(game1);
        player1.getGames().add(game2);
        dataToUpdate.put(player1.getUsername(), player1);
    }

}
