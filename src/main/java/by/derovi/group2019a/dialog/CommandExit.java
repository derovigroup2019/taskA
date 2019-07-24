package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandExit extends Command {
    public CommandExit() {
        super("exit", "exit - close window", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nExiting..");
        System.exit(0);
    }
}
