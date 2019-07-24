package by.derovi.group2019a.managers;

import by.derovi.group2019a.dialog.*;

import java.util.*;

public class DialogManager {
    private Scanner scanner;
    private Map<String, Command> commands;

    public DialogManager() {
        commands = new HashMap<>();
        scanner = new Scanner(System.in);
        registerCommands();
    }

    public void start() {
        System.out.println("Print \"help\" to see all available commands!");
        while(true) {
            String[] commandArgs = scanner.nextLine().split(" ");
            if(commands.containsKey(commandArgs[0].toLowerCase())) {
                Command command = commands.get(commandArgs[0].toLowerCase());
                if(command.getArgsCount() + 1 != commandArgs.length) {
                    System.out.println("Invalid number of arguments!\nExample: " + command.getDescription());
                    continue;
                }
                try {
                    String[] args = new String[command.getArgsCount()];
                    for(int idx = 0; idx < args.length; ++ idx)
                        args[idx] = commandArgs[idx + 1];
                    command.execute(args);
                } catch (CommandArgumentsException ex) {
                    System.out.println(ex.getMessage());
                }
            } else
                System.out.println("Command not found, print \"help\" to see all available commands!");
        }
    }

    private void registerCommands() {
        commands.put("help", new CommandHelp());
        commands.put("exit", new CommandExit());
        commands.put("download", new CommandDownload());
        commands.put("test", new CommandTest());
        commands.put("prepare", new CommandPrepare());
        commands.put("serials", new CommandSerials());
        commands.put("wordscount", new CommandWordsCount());
        commands.put("uniquewordscount", new CommandUniqueWordsCount());
        commands.put("uniquewordstop", new CommandUniqueWordsTop());
        commands.put("duration", new CommandDuration());
        commands.put("countword", new CommandCountWord());
        commands.put("sentencescount", new CommandSentencesCount());
        commands.put("wordstypes", new CommandWordsTypes());
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
