package com.example.hangman.dto;

import com.example.hangman.model.Game;
import com.example.hangman.model.Status;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class PlayerDTO {

    public String visibleWord;
    public Integer attemptsLeft;
    public List<Character> availableCharacters;
    public String status;
    public String secretWord;

    public PlayerDTO(Game game) {
        this.visibleWord = game.getVisibleWord();
        this.attemptsLeft = game.getAttemptsLeft();
        this.availableCharacters = game.getAvailableCharacters();
        this.status = game.getStatus().toString();
        this.secretWord = game.getStatus().equals(Status.LOST) ? game.getSecretWord() : "";
    }
}

