package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandUniqueWordsTop extends Command {
    public CommandUniqueWordsTop() {
        super("uniqueWordsTop", "uniqueWordsTop - Show 30 most popular unique words in all serials.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing unique words top..");
        Managers.getAnalyzer().uniqueWordsTop();
        System.out.println();
    }
}
