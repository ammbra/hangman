package eu.ammbra.hangman.facade;

import eu.ammbra.hangman.Setup;
import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.dto.Role;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Setup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private LoginFacade loginFacade;

    private User player1;

    @BeforeEach
    public void setup(){
        Map<String, User> dataToUpdate = datatabase.getData();

        player1 = new User("ana");

        String randomWord = "ANY";
        String secretWord = "???";
        Game game1 = new Game(player1, randomWord, secretWord, 5);
        player1.setCurrentGame(game1);

        dataToUpdate.put(player1.getUsername(), player1);
    }

    @Test
    public void when_admin_login_then_type_admin_detected(){
        Role typeOfUser = loginFacade.login(Role.ADMINISTRATOR.toString());
        assertThat(typeOfUser).isEqualTo(Role.ADMINISTRATOR);
    }

    @Test
    public void whenAPlayerLoginThenPlayerIsDetected(){
        Role typeOfUser = loginFacade.login(player1.getUsername());
        assertThat(typeOfUser).isEqualTo(Role.PLAYER);
    }

    @Test
    public void whenANewPlayerLoginThenPlayerIsDetected(){
        Role typeOfUser = loginFacade.login("ana");
        assertThat(typeOfUser).isEqualTo(Role.PLAYER);
    }

    @AfterEach
    public void teardown(){
        this.player1 = null;
    }
}
