package eu.ammbra.hangman.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private User player;
    private String secretWord;
    private String visibleWord;
    private Integer attemptsLeft;
    private List<Character> availableCharacters;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;

    public Game(User player, String secretWord, String visibleWord, Integer attemptsLeft) {
        this.player = player;
        this.secretWord = secretWord;
        this.visibleWord = visibleWord;
        this.attemptsLeft = attemptsLeft;
        this.availableCharacters =  IntStream.rangeClosed('A', 'Z')
                .mapToObj(c -> (char) c).collect(Collectors.toList());
        this.startDate = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getVisibleWord() {
        return visibleWord;
    }

    public void setVisibleWord(String visibleWord) {
        this.visibleWord = visibleWord;
    }

    public Integer getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(Integer attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public List<Character> getAvailableCharacters() {
        return availableCharacters;
    }

    public void setAvailableCharacters(List<Character> availableCharacters) {
        this.availableCharacters = availableCharacters;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
