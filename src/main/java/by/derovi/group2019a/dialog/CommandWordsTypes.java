package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandWordsTypes extends Command {
    public CommandWordsTypes() {
        super("wordsTypes", "wordsTypes - Show count of nouns, adjectives, verbs, pronouns and adverbs.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing words types information..");
        Managers.getAnalyzer().wordsTypes();
        System.out.println();
    }
}
