package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandSentencesCount extends Command {
    public CommandSentencesCount() {
        super("sentencesCount", "sentencesCount - Show sentences count and average length.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing sentences information..");
        Managers.getAnalyzer().sentencesCount();
        System.out.println();
    }
}
