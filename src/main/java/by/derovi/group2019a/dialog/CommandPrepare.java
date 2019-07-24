package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;

public class CommandPrepare extends Command {
    public CommandPrepare() {
        super("prepare", "prepare - prepare analyzer, load data from " + Managers.getSettingsManager().getSubtitlesFolder() + " folder. If you will use analyzer without using this command, it will be executed automatically.", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nPreparing Analyzer..");
        Managers.getAnalyzer().setPrepared(false);
        Managers.getAnalyzer().prepare();
        System.out.println("\n");
    }
}
