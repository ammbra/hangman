package com.example.hangman.repository;

import com.example.hangman.database.Database;
import com.example.hangman.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;



@Repository
public class GameRepository {

    private Database database;

    @Autowired
    public GameRepository(Database database) {
        this.database = database;
    }

    public List<Game> getAllGames(){
        return database.getData().values().parallelStream().flatMap(games -> games.getAllGames().stream()).collect(Collectors.toList());
    }

    public List<Game> getAllCurrentGames(){
        return database.getData().values().parallelStream().map(games -> games.getCurrentGame()).collect(Collectors.toList());
    }

}
