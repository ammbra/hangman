package com.example.hangman.facade;

import com.example.hangman.HangmanApplication;
import com.example.hangman.database.Database;
import com.example.hangman.model.Game;
import com.example.hangman.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginFacadeTest {

    private static final String ADMIN = "ADMINISTRATOR";
    private static final String PLAYER = "PLAYER";

    @Autowired
    private Database datatabase;

    @Autowired
    private LoginFacade loginFacade;

    private  Player player1;

    @BeforeEach
    public void setup(){
        datatabase.resetDataForTests();
        Map<String, Player> dataToUpdate = datatabase.getData();

        player1 = new Player("ana");

        String randomWord = "ANY";
        String secretWord = "???";
        Game game1 = new Game(player1, randomWord, secretWord);
        player1.setCurrentGame(game1);

        dataToUpdate.put(player1.getUsername(), player1);
    }

    @Test
    public void when_admin_login_then_type_admin_detected(){
        String typeOfUser = loginFacade.login(ADMIN);
        assertThat(typeOfUser).isEqualTo(ADMIN);
    }

    @Test
    public void whenAPlayerLoginThenPlayerIsDetected(){
        String typeOfUser = loginFacade.login(player1.getUsername());
        assertThat(typeOfUser).isEqualTo(PLAYER);
    }

    @Test
    public void whenANewPlayerLoginThenPlayerIsDetected(){
        String typeOfUser = loginFacade.login("ana");
        assertThat(typeOfUser).isEqualTo(PLAYER);
    }

    @AfterEach
    public void teardown(){
        this.player1 = null;
    }
}
