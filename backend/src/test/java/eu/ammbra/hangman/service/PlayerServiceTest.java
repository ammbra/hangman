package eu.ammbra.hangman.service;

import eu.ammbra.hangman.Setup;
import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = Setup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerServiceTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private WordsGeneratorService wordsGeneratorService;

    @Autowired
    private PlayerService userService;

    @BeforeEach
    public void setup(){
//        datatabase.resetDataForTests();
        addOnePlayerTwoGames("ana");
        addOnePlayerTwoGames("alsacia");
        addOnePlayerTwoGames("sorina");
    }

    @Test
    public void createNewPlayer(){
        String username = "newPlayerUsername";
        User player = userService.createNewPlayer(username);

        assertThat(player).isNotNull();
        assertThat(player.getUsername()).isNotNull().isEqualTo(username);
    }

    @Test
    public void retrieveAllUsers(){
        List<User> allPlayers = userService.getAllPlayers();

        assertThat(allPlayers).isNotNull();
        assertThat(allPlayers).isNotEmpty();
        assertThat(allPlayers).hasSize(3);
    }

    @Test
    public void getUserByUsername(){
        Optional<User> player = userService.getUserByUsername("ana");
        assertThat(player).isPresent();
    }

    private void addOnePlayerTwoGames(String username) {
        Map<String, User> dataToUpdate = datatabase.getData();

        User player1 = new User(username);

        String randomWord = wordsGeneratorService.getSecretWord();
        String secretWord = wordsGeneratorService.getVisibleWord(randomWord);
        Game game1 = new Game(player1, randomWord, secretWord, 5);
        Game game2 = new Game(player1, randomWord, secretWord, 5);
        player1.setCurrentGame(game1);
        player1.getGames().add(game2);
        dataToUpdate.put(player1.getUsername(), player1);
    }

}
