package duke;

import duke.exceptions.InsufficientInputException;
import duke.exceptions.UnkownCommandException;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;


public class Duke {
    private String path = System.getProperty("user.dir") + File.separator + "data.txt";
    private Storage storage;
    private TaskList taskList;
    private Parser parser;
    private UI ui;

    public Duke() {
        storage = new Storage();
        taskList = new TaskList();
        storage.setTaskList(taskList, taskList.getTaskListArray());
        parser = new Parser();
        ui = new UI();
        storage.initializeFile(path);
    }
    public void run() {
        ui.printGreeting();
        String input = parser.readInput();
        while (!input.equals("bye")) {
            try {
                String[] inputWords = input.split(" ", 2);
                switch (inputWords[0]) {
                case "list":
                    taskList.displayList();
                    break;
                case "mark":
                    taskList.markTask(inputWords);
                    break;
                case "unmark":
                    taskList.unmarkTask(inputWords);
                    break;
                case "todo":
                    taskList.createTodo(inputWords);
                    break;
                case "deadline":
                    taskList.createDeadline(inputWords);
                    break;
                case "event":
                    taskList.createEvent(inputWords);
                    break;
                case "delete":
                    taskList.deleteTask(inputWords);
                    break;
                default:
                    throw new UnkownCommandException("Unknown command, please try again.");
                }
                storage.saveTasklist(path);
            } catch (InsufficientInputException e) {
                System.out.println(e.getMessage());
            } catch (UnkownCommandException e) {
                System.out.println(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid task number");
            } catch (IOException e) {
                System.out.println("Error occurred while saving file\n");
            } catch (DateTimeParseException e) {
                System.out.println("Please input date of format yyyy-mm-dd");
            }
            input = parser.readInput();
        }
        ui.printGoodbye();
    }
    public static void main(String[] args) {
        new Duke().run();
    }
}
