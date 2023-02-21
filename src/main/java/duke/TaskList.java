package duke;

import duke.exceptions.InsufficientInputException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    private static final int EVENT_COMMAND_LENGTH = 3;
    private static final int INPUT_LENGTH = 2;
    private static final int DEADLINE_COMMAND_LENGTH = 2;

    private ArrayList<Task> taskListArray = new ArrayList<>();
    public void displayList() {
        if (taskListArray.size() != 0) {
            System.out.println(UI.HorizontalLine + "List of Tasks: \n");
            for (int i = 0; i < Task.maxTaskNumber; i++) {
                System.out.printf("%d.", i + 1);
                taskListArray.get(i).getTaskStatus();
            }
            System.out.println(UI.HorizontalLine);
        } else {
            System.out.println(UI.HorizontalLine + "no task added yet\n" + UI.HorizontalLine);
        }
    }
    public void createEvent(String[] inputWords) throws InsufficientInputException {
        if (inputWords.length < INPUT_LENGTH) {
            throw new InsufficientInputException("Event command has insufficient input, please try again.");
        }
        String[] event = inputWords[1].split(" /from | /to ", 3);
        if (event.length < EVENT_COMMAND_LENGTH || event[0].trim().equals("") || event[1].trim().equals("") || event[2].trim().equals("")) {
            throw new InsufficientInputException("Event command has insufficient input, please try again.");
        }
        taskListArray.add(new Event(event[0], event[1], event[2]));
        taskListArray.get(Task.maxTaskNumber).setTaskType("event");
        Task.maxTaskNumber++;
        System.out.printf(UI.HorizontalLine + "Event added: %s (from: %s to: %s)\n" + UI.HorizontalLine, event[0], event[1], event[2]);
    }

    public void createDeadline(String[] inputWords) throws InsufficientInputException, DateTimeParseException {
        if (inputWords.length < INPUT_LENGTH) {
            throw new InsufficientInputException("Deadline command has insufficient input, please try again.");
        }
        String[] deadline = inputWords[1].split(" /by ", 2);
        if (deadline.length < DEADLINE_COMMAND_LENGTH || deadline[0].trim().equals("") || deadline[1].trim().equals("")) {
            throw new InsufficientInputException("Deadline command has insufficient input, please try again.");
        }
        taskListArray.add(new Deadline(deadline[0], LocalDate.parse(deadline[1])));
        taskListArray.get(Task.maxTaskNumber).setTaskType("deadline");
        Task.maxTaskNumber++;
        System.out.printf(UI.HorizontalLine + "Deadline added: %s (by: %s)\n" + UI.HorizontalLine, deadline[0],
                LocalDate.parse(deadline[1]).format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
    }

    public void createTodo(String[] inputWords) throws InsufficientInputException {
        if (inputWords.length < INPUT_LENGTH || inputWords[1].trim().equals("")) {
            throw new InsufficientInputException("Todo command has insufficient input, please try again.");
        }
        taskListArray.add(new ToDo(inputWords[1].trim()));
        taskListArray.get(Task.maxTaskNumber).setTaskType("todo");
        Task.maxTaskNumber++;
        System.out.println(UI.HorizontalLine + "To do added: " + inputWords[1] + "\n" + UI.HorizontalLine);
    }

    public void unmarkTask(String[] inputWords) throws ArrayIndexOutOfBoundsException, InsufficientInputException {
        if (inputWords.length < INPUT_LENGTH || inputWords[1].trim().equals("")) {
            throw new InsufficientInputException("Task number not specified, please try again");
        }
        int unmarkTaskNumber = Integer.valueOf(inputWords[1]) - 1;
        if (unmarkTaskNumber >= Task.maxTaskNumber || unmarkTaskNumber < 0) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            taskListArray.get(unmarkTaskNumber).unsetDone();
            System.out.println(UI.HorizontalLine + "Task set as undone: " + taskListArray.get(unmarkTaskNumber).getTaskName() +
                    "\n" + UI.HorizontalLine);
        }
    }

    public void markTask(String[] inputWords) throws ArrayIndexOutOfBoundsException, InsufficientInputException {
        if (inputWords.length < INPUT_LENGTH || inputWords[1].trim().equals("")) {
            throw new InsufficientInputException("Task number not specified, please try again");
        }
        int markTaskNumber = Integer.valueOf(inputWords[1]) - 1;
        if (markTaskNumber >= Task.maxTaskNumber || markTaskNumber < 0) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            taskListArray.get(markTaskNumber).setDone();
            System.out.println(UI.HorizontalLine + "Task set as done: " + taskListArray.get(markTaskNumber).getTaskName() + "\n"
                    + UI.HorizontalLine);
        }
    }

    public void deleteTask(String[] inputWords) throws ArrayIndexOutOfBoundsException, InsufficientInputException{
        if (inputWords.length < INPUT_LENGTH || inputWords[1].trim().equals("")) {
            throw new InsufficientInputException("Task number not specified, please try again");
        }
        int taskIndex = Integer.valueOf(inputWords[1])-1;
        if(taskIndex >= Task.maxTaskNumber || taskIndex < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }else{
            taskListArray.remove(taskIndex);
            Task.maxTaskNumber--;
            System.out.printf(UI.HorizontalLine + "Task %d has been deleted\n" + UI.HorizontalLine, taskIndex+1);
        }
    }
    public void readData(File f) throws IOException {
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String input = s.nextLine();
            String[] data = input.split("\\|");
            switch (data[0].trim()) {
            case "todo":
                taskListArray.add(new ToDo(data[2].trim()));
                taskListArray.get(Task.maxTaskNumber).setTaskType("todo");
                break;
            case "deadline":
                taskListArray.add(new Deadline(data[2].trim(), LocalDate.parse(data[3].trim())));
                taskListArray.get(Task.maxTaskNumber).setTaskType("deadline");
                break;

            case "event":
                taskListArray.add(new Event(data[2].trim(), data[3].trim(), data[4].trim()));
                taskListArray.get(Task.maxTaskNumber).setTaskType("event");
                break;
            }
            if (data[1].trim().equals("X")) {
                taskListArray.get(Task.maxTaskNumber).setDone();
            }
            Task.maxTaskNumber++;
        }
    }

    public ArrayList<Task> getTaskListArray() {
        return taskListArray;
    }
}



