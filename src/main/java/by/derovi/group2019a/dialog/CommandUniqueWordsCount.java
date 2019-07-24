package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandUniqueWordsCount extends Command {
    public CommandUniqueWordsCount() {
        super("uniqueWordsCount", "uniqueWordsCount - Show unique words count and most used word of all serials.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing unique words count..");
        Managers.getAnalyzer().uniqueWordsCount();
        System.out.println();
    }
}
