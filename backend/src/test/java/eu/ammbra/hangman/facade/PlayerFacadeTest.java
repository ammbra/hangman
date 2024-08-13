package eu.ammbra.hangman.facade;


import eu.ammbra.hangman.Setup;
import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.dto.Player;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.User;
import eu.ammbra.hangman.model.Status;
import eu.ammbra.hangman.service.PlayerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Setup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerFacadeTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private PlayerService playerService;

    private User gamePlayer;

    @BeforeEach
    public void Setup(){
        Map<String, User> dataToUpdate = datatabase.getData();

        gamePlayer = new User("ana");

        String randomWord = "ANY";
        String secretWord = "???";
        Game game1 = new Game(gamePlayer, randomWord, secretWord, 5);
        gamePlayer.setCurrentGame(game1);

        dataToUpdate.put(gamePlayer.getUsername(), gamePlayer);

        User player2 = new User("sorina");
        dataToUpdate.put(player2.getUsername(), player2);
    }

    @Test
    public void whenGetCurrentGameButThePlayerIsNew(){
        String username = "ana";

        User player = playerService.getUserByUsername(username).get();
        Game currentGameBefore = player.getCurrentGame();

        Player game = playerFacade.findCurrentGame(username);

        player = playerService.getUserByUsername(username).get();
        Game currentGameAfter = player.getCurrentGame();

        assertThat(currentGameBefore).isNotNull();
        assertThat(currentGameAfter).isNotNull();
        assertThat(currentGameAfter.getVisibleWord()).isEqualTo(game.visibleWord());
    }


    @Test
    public void whenGetCurrentGameButThePlayerHadAnActiveGame(){
        String username = gamePlayer.getUsername();
        User player = playerService.getUserByUsername(username).get();
        Game currentGameBefore = player.getCurrentGame();

		playerFacade.findCurrentGame(username);

		player = playerService.getUserByUsername(username).get();
        Game currentGameAfter = player.getCurrentGame();

        assertThat(currentGameBefore).isNotNull();
        assertThat(currentGameAfter).isNotNull();
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsInTheWord(){
        String username = gamePlayer.getUsername();
        Player gameBefore = playerFacade.findCurrentGame(username);
        int availableCharactersBefore = gameBefore.availableCharacters().size();
        Player gameAfterUpdate = playerFacade.updateGame(username, 'A');

        assertThat(gameAfterUpdate).isNotNull();
        assertThat(gameAfterUpdate.visibleWord()).isNotNull().isEqualTo("A??");
        assertThat(gameAfterUpdate.attemptsLeft()).isNotNull().isEqualTo(gameBefore.attemptsLeft());
        assertThat(gameAfterUpdate.availableCharacters().size()).isEqualTo(availableCharactersBefore - 1);
        assertThat(gameAfterUpdate.status()).isEqualTo(Status.ACTIVE);
    }

    @AfterEach
    public void teardown(){
        this.gamePlayer = null;
    }

}
