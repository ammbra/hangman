package com.example.hangman.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.nonNull;

@Getter
@Setter
public class Player {

    private List<Game> games;
    private Game currentGame;
    private String username;

    public Player(String username) {
        this.username = username;
        games = new LinkedList<>();
    }

    public List<Game> getAllGames() {
        List<Game> allGames = new LinkedList<>();
        allGames.addAll(getGames());
        if(nonNull(getCurrentGame())){
            allGames.add(getCurrentGame());
        }

        return allGames;
    }
}
