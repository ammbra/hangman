package com.example.hangman.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class Game {

    private final int NUMBER_OF_ATTEMPTS = 5;

    private Player player;
    private String secretWord;
    private String visibleWord;
    private Integer attemptsLeft;
    private List<Character> availableCharacters;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;

    public Game(Player player, String secretWord, String visibleWord) {
        this.player = player;
        this.secretWord = secretWord;
        this.visibleWord = visibleWord;
        this.attemptsLeft = NUMBER_OF_ATTEMPTS;
        this.availableCharacters =  IntStream.rangeClosed('A', 'Z')
                .mapToObj(c -> (char) c).collect(Collectors.toList());
        this.startDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

}
