package eu.ammbra.hangman.repository;

import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class PlayerRepository {

    private final Database database;

    public PlayerRepository(Database database) {
        this.database = database;
    }

    public Collection<User> getAllPlayers(){
        return database.getData().values();
    }

    public synchronized void addNewPlayer(User player){
        database.getData().put(player.getUsername(), player);
    }

    public Optional<User> getUserByUsername(String username){
        Optional<User> player = Optional.empty();
        if(database.getData().containsKey(username)){
            player = Optional.of(database.getData().get(username));
        }
        return player;
    }

}
