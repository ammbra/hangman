package com.example.hangman.repository;

import com.example.hangman.database.Database;
import com.example.hangman.model.Player;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class PlayerRepository {

    private Database database;

    public PlayerRepository(Database database) {
        this.database = database;
    }

    public Collection<Player> getAllPlayers(){
        return database.getData().values();
    }

    public synchronized void addNewPlayer(Player player){
        database.getData().put(player.getUsername(), player);
    }

    public Optional<Player> getUserByUsername(String username){
        Optional<Player> player = Optional.empty();
        if(database.getData().containsKey(username)){
            player = Optional.of(database.getData().get(username));
        }
        return player;
    }

}
