package duke;

public class Deadline extends Task {
    private String by;

    public Deadline(String taskName, String by) {
        super(taskName);
        this.by = by;
    }

    @Override
    public void getTaskStatus() {
        System.out.printf("[D][%s] %s (by: %s)\n", this.getDone(), this.getTaskName(), this.by);
    }
}