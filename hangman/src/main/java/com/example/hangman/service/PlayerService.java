package com.example.hangman.service;

import com.example.hangman.model.Player;
import com.example.hangman.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createNewPlayer(String username) {
        Player player = new Player(username);
        playerRepository.addNewPlayer(player);
        return player;
    }

    public List<Player> getAllPlayers() {
        return new LinkedList<>(playerRepository.getAllPlayers());
    }

    public Optional<Player> getUserByUsername(String username) {
        Optional<Player> player = playerRepository.getUserByUsername(username);
        return player;
    }

}
