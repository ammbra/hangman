package eu.ammbra.hangman.facade;

import eu.ammbra.hangman.dto.Player;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.Status;
import eu.ammbra.hangman.model.User;
import eu.ammbra.hangman.service.GameService;
import eu.ammbra.hangman.service.PlayerService;
import eu.ammbra.hangman.service.WordsGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;


@Service
public class PlayerFacade {

    private final GameService gameService;
    private final PlayerService playerService;
    private final WordsGeneratorService wordsService;

    public PlayerFacade(GameService gameService, PlayerService playerService, WordsGeneratorService wordsService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.wordsService = wordsService;
    }

    public Player findCurrentGame(String username){
        User player = findCurrentPlayer(username);
        boolean isGameActive = nonNull(player.getCurrentGame()) && player.getCurrentGame().getStatus().equals(Status.ACTIVE);
        Game game = (isGameActive) ? player.getCurrentGame() : reinitializeGame(player);

        return new Player(game.getVisibleWord(), game.getSecretWord(),
                game.getAttemptsLeft(), game.getAvailableCharacters(),
                game.getStatus());
    }

    private User findCurrentPlayer(String username) {
        Optional<User> optPlayer = playerService.getUserByUsername(username);
        return optPlayer.orElseGet(() -> playerService.createNewPlayer(username));
    }

    public Player updateGame(String username, Character character){
        User player = playerService.getUserByUsername(username).get();
        Game currentGame = player.getCurrentGame();
        gameService.updateGame(currentGame, character);

        return new Player(currentGame.getVisibleWord(), currentGame.getSecretWord(),
                currentGame.getAttemptsLeft(), currentGame.getAvailableCharacters(),
                currentGame.getStatus());
    }

    private Game reinitializeGame(User player) {
        String secretWord = wordsService.getSecretWord();
        String visibleWord = wordsService.getVisibleWord(secretWord);

        Game game = gameService.startNewGame(player, secretWord, visibleWord);
        player.setCurrentGame(game);

        return game;
    }

}
