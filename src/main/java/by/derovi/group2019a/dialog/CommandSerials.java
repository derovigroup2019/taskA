package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandSerials extends Command {
    public CommandSerials() {
        super("serials", "serials - Show information about serials - count of seasons and episodes.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing serials statistics..");
        Managers.getAnalyzer().serialsStatistics();
        System.out.println();
    }
}
