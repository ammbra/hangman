package eu.ammbra.hangman.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordsGeneratorService {

    @Value("#{'${availableWords}'.split(',')}")
    public List<String> availableWords;

    public String getSecretWord() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, availableWords.size());
        return availableWords.get(randomIndex);
    }

    public String getVisibleWord(String originalWord) {
		return String.join("", Collections.nCopies(originalWord.length(), "?"));
    }

}
