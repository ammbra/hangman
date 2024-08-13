package eu.ammbra.hangman.dto;


import eu.ammbra.hangman.model.Status;
import java.util.List;

public record Player(String visibleWord, String secretWord, Integer attemptsLeft,
					 List<Character> availableCharacters, Status status) {
}

