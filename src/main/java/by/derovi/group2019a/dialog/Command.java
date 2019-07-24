package by.derovi.group2019a.dialog;

public abstract class Command {
    private String name;
    private String description;
    private int argsCount;

    public Command(String name, String description, int argsCount) {
        this.name = name;
        this.description = description;
        this.argsCount = argsCount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getArgsCount() {
        return argsCount;
    }

    abstract public void execute(String[] args) throws CommandArgumentsException;
}
