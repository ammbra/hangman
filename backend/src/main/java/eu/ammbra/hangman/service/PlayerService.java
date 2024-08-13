package eu.ammbra.hangman.service;

import eu.ammbra.hangman.model.User;
import eu.ammbra.hangman.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public User createNewPlayer(String username) {
        User player = new User(username);
        playerRepository.addNewPlayer(player);
        return player;
    }

    public List<User> getAllPlayers() {
        return new LinkedList<>(playerRepository.getAllPlayers());
    }

    public Optional<User> getUserByUsername(String username) {
		return playerRepository.getUserByUsername(username);
    }

}
