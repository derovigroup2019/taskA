package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandWordsCount extends Command {
    public CommandWordsCount() {
        super("wordsCount", "wordsCount - Show words count, words average length and duration.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing words information..");
        Managers.getAnalyzer().wordsCount();
        System.out.println();
    }
}
