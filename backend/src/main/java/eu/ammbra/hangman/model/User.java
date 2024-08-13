package eu.ammbra.hangman.model;


import java.util.LinkedList;
import java.util.List;

public class User {

    private List<Game> games;
    private Game currentGame;
    private String username;

    public User(String username) {
        this.username = username;
        games = new LinkedList<>();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
