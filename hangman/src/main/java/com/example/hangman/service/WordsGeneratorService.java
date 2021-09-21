package com.example.hangman.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class WordsGeneratorService {
    public final static List<String> availableWords = Arrays.asList("NATIVE", "SUBATOMIC", "HOUSE", "SUPERSONIC", "DEVELOPER", "DATABASE", "JAVA");

    public String getSecretWord() {
        Integer randomIndex = ThreadLocalRandom.current().nextInt(0, availableWords.size());
        return availableWords.get(randomIndex);
    }

    public String getVisibleWord(String originalWord) {
        String word = String.join("", Collections.nCopies(originalWord.length(), "?"));
        log.info(word);
        return word;
    }

}
