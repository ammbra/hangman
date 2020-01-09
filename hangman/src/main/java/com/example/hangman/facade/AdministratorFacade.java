package com.example.hangman.facade;

import com.example.hangman.dto.AdministratorDTO;
import com.example.hangman.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministratorFacade {

    private GameService gameService;

    public AdministratorFacade(GameService gameService) {
        this.gameService = gameService;
    }

    public List<AdministratorDTO> getAllGames(){
        return gameService.getAllGames().stream().map(game -> new AdministratorDTO(game)).collect(Collectors.toList());
    }

}
