package eu.ammbra.hangman.service;

import eu.ammbra.hangman.Setup;
import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.User;
import eu.ammbra.hangman.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = Setup.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameServiceTest {

    @Autowired
    private Database datatabase;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @BeforeEach
    public void setup(){
        addOnePlayerTwoGames("ana");
        addOnePlayerTwoGames("alsacia");
        addOnePlayerTwoGames("sorina");
    }

    @Test
    public void retrieveAllGames(){
        List<Game> allGames = gameService.getAllGames();

        assertThat(allGames).isNotNull().isNotEmpty();
        assertThat(allGames.size()).isEqualTo(6);
    }

    @Test
    public void retrieveAllCurrentGames(){
        List<Game> allGames = gameService.getAllCurrentGames();

        assertThat(allGames).isNotNull().isNotEmpty();
        assertThat(allGames.size()).isEqualTo(3);
    }

    @Test
    public void givenAPlayerThenStartANewGame(){
        User player = new User("sorin");

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo(visibleWord);
    }

    @Test
    public void givenAPlayerThenStartANewGameWhileCurrentStillActive(){
        User player = new User("matt");

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        game = gameService.startNewGame(game.getPlayer(), secretWord, visibleWord);

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo(visibleWord);
    }

    @Test
    public void givenAPlayerThenStartANewGameWhenCurrentIsFinished(){
        User player = new User("sorin");

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        game.setStatus(Status.WON);
        game.getPlayer().setCurrentGame(game);
        game = gameService.startNewGame(game.getPlayer(), secretWord, visibleWord);

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo(visibleWord);
    }

    @Test
    public void givenANewGameCheckIfCorrectlyStored(){
        User player = playerService.getUserByUsername("ana").get();

        String secretWord = "SECRET";
        String visibleWord = "??????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);

        List<Game> games = gameService.getAllCurrentGames();

        assertThat(games).isNotNull().contains(game);
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsInTheWord(){
        User player = new User("sorin");

        String secretWord = "HELLO";
        String visibleWord = "?????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'H');

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo("H????");
        assertThat(game.getAttemptsLeft()).isEqualTo(attemptsLeftBefore);
        assertThat(game.getAvailableCharacters().size()).isEqualTo(numOfAvailableLettersBefore - 1);
        assertThat(game.getStatus()).isEqualTo(Status.ACTIVE);
    }


    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsNotInTheWord(){
        User player = new User("sorin");

        String secretWord = "HELLO";
        String visibleWord = "?????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'X');

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo("?????");
        assertThat(game.getAttemptsLeft()).isEqualTo(attemptsLeftBefore - 1);
        assertThat(game.getAvailableCharacters().size()).isEqualTo(numOfAvailableLettersBefore - 1);
        assertThat(game.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    public void updateGameWhenIsNewGameAndTheLetterIsMoreThanOneTimeInTheWord(){
        User player = new User("sorin");

        String secretWord = "GOOD";
        String visibleWord = "????";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'O');

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo("?OO?");
        assertThat(game.getAttemptsLeft()).isEqualTo(attemptsLeftBefore);
        assertThat(game.getAvailableCharacters().size()).isEqualTo(numOfAvailableLettersBefore - 1);
        assertThat(game.getStatus()).isEqualTo(Status.ACTIVE);
    }


    @Test
    public void updateGameWhenIHaveGuessedTheWord(){
        User player = new User("sorin");

        String secretWord = "H";
        String visibleWord = "?";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer attemptsLeftBefore = game.getAttemptsLeft();
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'H');

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo("H");
        assertThat(game.getAttemptsLeft()).isEqualTo(attemptsLeftBefore);
        assertThat(game.getAvailableCharacters().size()).isEqualTo(numOfAvailableLettersBefore - 1);
        assertThat(game.getStatus()).isEqualTo(Status.WON);

    }

    @Test
    public void updateGameWhenIDoNotHaveMoreAttemps(){
        User player = new User("sorin");

        String secretWord = "H";
        String visibleWord = "?";

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        Integer numOfAvailableLettersBefore = game.getAvailableCharacters().size();

        gameService.updateGame(game, 'A');
        gameService.updateGame(game, 'B');
        gameService.updateGame(game, 'C');
        gameService.updateGame(game, 'D');
        gameService.updateGame(game, 'E');

        assertThat(game.getSecretWord()).isNotNull().isEqualTo(secretWord);
        assertThat(game.getVisibleWord()).isNotNull().isEqualTo("?");
        assertThat(game.getAttemptsLeft()).isEqualTo(0);
        assertThat(game.getAvailableCharacters().size()).isEqualTo(numOfAvailableLettersBefore - 5);
        assertThat(game.getStatus()).isEqualTo(Status.LOST);
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
