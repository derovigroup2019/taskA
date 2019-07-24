package by.derovi.group2019a.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
    private List<String> words = new ArrayList<>();

    public Sentence() {

    }

    public Sentence(List<String> words) {
        this.words = words;
    }

    public Sentence(String str) {
        String[] strings = str.split("(\\s*[;,—\\-:]\\s*|\\s+)");
        for(String word : strings)
            if(word.length() > 0)
                words.add(word);
    }

    public List<String> getWords() {
        return words;
    }
}
