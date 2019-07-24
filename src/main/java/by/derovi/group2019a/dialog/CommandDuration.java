package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandDuration extends Command {
    public CommandDuration() {
        super("duration", "duration - Show duration of all serials and their speech duration.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nShowing duration of all serials and their speech duration..");
        Managers.getAnalyzer().showDuration();
        System.out.println();
    }
}
