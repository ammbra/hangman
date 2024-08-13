package eu.ammbra.hangman.dto;

import eu.ammbra.hangman.model.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;

public record Activity(String username, String secretWord,
                       LocalDateTime startDate, LocalDateTime endDate,
                       Integer attemptsLeft, Status status) {

    public Activity {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        startDate =  LocalDateTime.parse(startDate.format(formatter), formatter);
        endDate = nonNull(endDate) ? LocalDateTime.parse(endDate.format(formatter), formatter) : LocalDateTime.MIN;
    }
}
