package com.example.hangman.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordsServiceTest {

    @Autowired
    private WordsGeneratorService wordsGeneratorService;

    @Test
    public void getARandomWord(){
        String randomWord = wordsGeneratorService.getSecretWord();
        assertThat(wordsGeneratorService.availableWords, hasItem(randomWord));
    }

    @Test
    public void givenAWordOfSixLettersThenGetSixHyphens(){
        String originalWord = "Marble";
        String secretWord = wordsGeneratorService.getVisibleWord(originalWord);
        String sixHyphens = "??????";
        assertThat(secretWord, is(sixHyphens));
    }
}
