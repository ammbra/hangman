package eu.ammbra.hangman.repository;

import eu.ammbra.hangman.database.Database;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


@Repository
public class GameRepository {

    private final Database database;

    @Autowired
    public GameRepository(Database database) {
        this.database = database;
    }

    public List<Game> getAllGames(){
        return database.getData().values().parallelStream()
                .flatMap(games -> {
					List<Game> allGames = new LinkedList<>(games.getGames());
                    if(nonNull(games.getCurrentGame())) {
                        allGames.add(games.getCurrentGame());
                    }
					return allGames.stream();
				}).collect(Collectors.toList());
    }

    public List<Game> getAllCurrentGames(){
        return database.getData().values().parallelStream()
                .map(User::getCurrentGame)
                .collect(Collectors.toList());
    }

}
