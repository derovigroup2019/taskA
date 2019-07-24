package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandCountWord extends Command {
    public CommandCountWord() {
        super("countWord", "countWord <word> - Show number of all entries of word.", 1);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing number of all entries of \"" + args[0] + "\"");
        Managers.getAnalyzer().countWord(args[0]);
        System.out.println();
    }
}
