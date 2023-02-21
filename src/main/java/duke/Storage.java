package duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Storage {
    private TaskList taskList;
    private ArrayList<Task> taskListArray;

    public void setTaskList(TaskList taskList, ArrayList<Task> taskListArray) {
        this.taskList = taskList;
        this.taskListArray = taskListArray;
    }

    public void initializeFile(String path) {
        try {
            File f = new File(path);
            taskList.readData(f);
        } catch (IOException e) {
            System.out.println("No file exists");
        }
    }

    public void saveTasklist(String path) throws IOException {
        FileWriter fw = new FileWriter(path, false);
        for (Task i : taskListArray) {
            String taskType = i.getTaskType();
            switch (taskType) {
            case "todo":
                fw.write(taskType + " | " + i.getDone() + " | " + i.getTaskName() + System.lineSeparator());
                break;

            case "deadline":
                Deadline d = (Deadline) i;
                fw.write(taskType + " | " + i.getDone() + " | " + i.getTaskName() + " | " + d.getBy() + System.lineSeparator());
                break;
            case "event":
                Event e = (Event) i;
                fw.write(taskType + "|" + i.getDone()+ "|" + i.getTaskName() + "|" + e.getStartDate() + "|"
                        + e.getEndDate()  + System.lineSeparator());
            }
        }
        fw.close();
    }
}