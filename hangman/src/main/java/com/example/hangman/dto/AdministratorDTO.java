package com.example.hangman.dto;

import com.example.hangman.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Getter
@Setter
public class AdministratorDTO {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public String username;
    public String secretWord;
    public String visibleWord;
    public String attemptsLeft;
    public String startDate;
    public String endDate;
    public String status;


    public AdministratorDTO(Game game) {
        this.username = game.getPlayer().getUsername();
        this.secretWord = game.getSecretWord();
        this.visibleWord = game.getVisibleWord();
        this.attemptsLeft = game.getAttemptsLeft().toString();
        this.startDate = game.getStartDate().format(formatter);
        this.endDate = nonNull(game.getEndDate()) ? game.getEndDate().format(formatter) : "-";
        this.status = game.getStatus().toString();
    }


}
