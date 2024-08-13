package eu.ammbra.hangman.facade;

import eu.ammbra.hangman.Setup;
import eu.ammbra.hangman.dto.Activity;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Setup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdministratorFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private AdministratorFacade administratorFacade;

    @BeforeEach
    public void setup(){
        addOnePlayerTwoGames("ana");
        addOnePlayerTwoGames("alsacia");
        addOnePlayerTwoGames("sorina");
    }

    @Test
    public void retrieveAllGames(){
        List<Activity> allGames = administratorFacade.retrieveHistory();
        assertThat(allGames.size()).isEqualTo(6);
    }

    private void addOnePlayerTwoGames(String username) {
        Map<String, User> dataToUpdate = datatabase.getData();

        User player1 = new User(username);

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game1 = new Game(player1, secretWord, visibleWord, 5);
        Game game2 = new Game(player1, secretWord, visibleWord, 5);
        player1.setCurrentGame(game1);
        player1.getGames().add(game2);
        dataToUpdate.put(player1.getUsername(), player1);
    }

}
