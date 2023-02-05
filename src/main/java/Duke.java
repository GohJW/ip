import java.lang.reflect.Array;
import java.time.format.TextStyle;
import java.util.Scanner;

public class Duke {

    public static String HorizontalLine = "__________________________\n";
    private static Task[] taskList = new Task[101];

    public static void main(String[] args) throws UnkownCommandException {
        System.out.println(HorizontalLine + "Hello! I'm Duke\n" + "What can I do for you?\n"
                + HorizontalLine);
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.equals("bye")) {
            try {
                String[] inputWords = input.split(" ", 2);
                switch (inputWords[0]) {
                case "list":
                    displayList();
                    break;
                case "mark":
                    markTask(inputWords);
                    break;
                case "unmark":
                    unmarkTask(inputWords);
                    break;
                case "todo":
                    createTodo(inputWords);
                    break;
                case "deadline":
                    createDeadline(inputWords);
                    break;
                case "event":
                    createEvent(inputWords);
                    break;
                default:
<<<<<<< Updated upstream
                    throw new UnkownCommandException("Unknown Command please try again.");
=======
                    throw new UnkownCommandException("Unknown command, please try again.");
>>>>>>> Stashed changes
                }
            } catch (InsufficientInputException e) {
                System.out.println(e.getMessage());
            } catch (UnkownCommandException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid task number");
            } catch (InvalidTaskNumber e) {
                System.out.println(e.getMessage());
            }
            input = in.nextLine();
        }
        System.out.println(HorizontalLine + "Goodbye!" + "\n" + HorizontalLine);
    }

    private static void createEvent(String[] inputWords) throws InsufficientInputException {
        if (inputWords.length < 2) {
            throw new InsufficientInputException("Event command has insufficient input, please try again.");
        }
        String[] event = inputWords[1].split(" /from | /to ", 3);
        if (event.length < 3) {
            throw new InsufficientInputException("Event command has insufficient input, please try again.");
        }
        taskList[Task.maxTaskNumber] = new Event(event[0], event[1], event[2]);
        Task.maxTaskNumber++;
        System.out.printf(HorizontalLine + "Event added: %s (from: %s to: %s)\n" + HorizontalLine, event[0], event[1], event[2]);
    }

    private static void createDeadline(String[] inputWords) throws InsufficientInputException {
        if (inputWords.length < 2) {
            throw new InsufficientInputException("Deadline command has insufficient input, please try again.");
        }
        String[] deadline = inputWords[1].split(" /by ", 2);
        if (deadline.length < 2) {
            throw new InsufficientInputException("Deadline command has insufficient input, please try again.");
        }
        taskList[Task.maxTaskNumber] = new Deadline(deadline[0], deadline[1]);
        Task.maxTaskNumber++;
        System.out.printf(HorizontalLine + "Deadline added: %s (by: %s)\n" + HorizontalLine, deadline[0], deadline[1]);
    }

    private static void createTodo(String[] inputWords) throws InsufficientInputException {
        if (inputWords.length < 2) {
            throw new InsufficientInputException("Todo command has insufficient input, please try again.");
        }
        taskList[Task.maxTaskNumber] = new ToDo(inputWords[1]);
        Task.maxTaskNumber++;
        System.out.println(HorizontalLine + "To do added: " + inputWords[1] + "\n" + HorizontalLine);
    }

    private static void unmarkTask(String[] inputWords) throws InvalidTaskNumber, InsufficientInputException {
        if(inputWords.length < 2) {
            throw new InsufficientInputException("Task number not specified, please try again");
        }
        int unmarkTaskNumber = Integer.valueOf(inputWords[1]);
        if (unmarkTaskNumber >= Task.maxTaskNumber) {
            throw new InvalidTaskNumber("Task number not found, please try again.");
            // System.out.println("No such task found\n" + HorizontalLine);
        } else {
            taskList[unmarkTaskNumber].unsetDone();
            System.out.println(HorizontalLine + "Task set as undone: " + taskList[unmarkTaskNumber].getTaskName() +
                    "\n" + HorizontalLine);
        }
    }

    private static void markTask(String[] inputWords) throws InvalidTaskNumber{
        int markTaskNumber = Integer.valueOf(inputWords[1]);
        if (markTaskNumber >= Task.maxTaskNumber) {
            throw new InvalidTaskNumber("Task number not found, please try again.");
            //System.out.println("No such task found\n" + HorizontalLine);
        } else {
            taskList[markTaskNumber].setDone();
            System.out.println(HorizontalLine + "Task set as done: " + taskList[markTaskNumber].getTaskName() + "\n"
                    + HorizontalLine);
        }
    }

    private static void displayList() {
        if (taskList[1] != null) {
            System.out.println(HorizontalLine + "List of Tasks: \n");
            for (int i = 1; i < Task.maxTaskNumber; i++) {
                System.out.printf("%d.", i);
                taskList[i].getTaskStatus();
            }
            System.out.println(HorizontalLine);
        } else {
            System.out.println(HorizontalLine + "no task added yet\n" + HorizontalLine);
        }
    }
}

