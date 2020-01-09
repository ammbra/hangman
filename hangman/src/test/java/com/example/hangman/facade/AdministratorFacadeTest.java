package com.example.hangman.facade;

import com.example.hangman.HangmanApplication;
import com.example.hangman.dto.AdministratorDTO;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hangman.database.Database;
import com.example.hangman.model.Game;
import com.example.hangman.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = HangmanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdministratorFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private AdministratorFacade administratorFacade;

    @BeforeEach
    public void setup(){
        datatabase.resetDataForTests();
        addOnePlayerTwoGames("ana");
        addOnePlayerTwoGames("alsacia");
        addOnePlayerTwoGames("sorina");
    }

    @Test
    public void retrieveAllGames(){
        List<AdministratorDTO> allGames = administratorFacade.getAllGames();
        assertThat(allGames.size()).isEqualTo(6);
    }

    private void addOnePlayerTwoGames(String username) {
        Map<String, Player> dataToUpdate = datatabase.getData();

        Player player1 = new Player(username);

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game1 = new Game(player1, secretWord, visibleWord);
        Game game2 = new Game(player1, secretWord, visibleWord);
        player1.setCurrentGame(game1);
        player1.getGames().add(game2);
        dataToUpdate.put(player1.getUsername(), player1);
    }

}
