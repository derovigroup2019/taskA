package by.derovi.group2019a.analyzer;

import by.derovi.group2019a.managers.Managers;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class WordTypeRecognizer {
    private Set<String> nouns = new HashSet<>(), verbs = new HashSet<>(), adverbs = new HashSet<>(), pronouns = new HashSet<>(), adjectives = new HashSet<>();

    public WordTypeRecognizer() {
        load(nouns, new File(Managers.getSettingsManager().getWordsFolder() + "/nouns.txt"));
        load(verbs, new File(Managers.getSettingsManager().getWordsFolder() + "/verbs.txt"));
        load(adverbs, new File(Managers.getSettingsManager().getWordsFolder() + "/adverbs.txt"));
        load(pronouns, new File(Managers.getSettingsManager().getWordsFolder() + "/pronouns.txt"));
        load(adjectives, new File(Managers.getSettingsManager().getWordsFolder() + "/adjectives.txt"));
    }

    private void load(Set<String> set, File file) {
        if(!file.exists()) return;
        if(!file.isFile()) return;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(fileInputStream);
            while(scanner.hasNextLine()) {
                set.add(scanner.nextLine().toLowerCase());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    List<String> defineWordType(String word) {
        word = word.toLowerCase();
        List<String> answer = new ArrayList<>();
        if(nouns.contains(word))
            answer.add("nouns");
        if(verbs.contains(word))
            answer.add("verbs");
        if(adverbs.contains(word))
            answer.add("adverbs");
        if(pronouns.contains(word))
            answer.add("pronouns");
        if(adjectives.contains(word))
            answer.add("adjectives");;
        return answer;
    }
}
