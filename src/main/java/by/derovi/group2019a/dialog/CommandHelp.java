package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandHelp extends Command {
    public CommandHelp() {
        super("help", "help - show description of all commands", 0);
    }

    @Override
    public void execute(String[] args) {
        System.out.println("\n========= Help =========");
        for(Command command : Managers.getDialogManager().getCommands().values()) {
            System.out.println(command.getDescription());
        }
        System.out.println();
    }
}
