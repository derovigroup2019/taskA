package by.derovi.group2019a.dialog;

public class OutputLine implements Comparable<OutputLine> {
    private int priority;
    private String line;

    public OutputLine(String line, int priority) {
        this.priority = priority;
        this.line = line;
    }

    public void print() {
        System.out.println(line);
    }

    @Override
    public int compareTo(OutputLine o) {
        return priority - o.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
