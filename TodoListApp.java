import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * TodoListApp.java
 * ఇది మన అప్లికేషన్ యొక్క మెయిన్ క్లాస్. యూజర్ ఇంటరాక్షన్ మరియు ఫైల్ ఆపరేషన్స్ అన్నీ ఇక్కడే ఉంటాయి.
 */
public class TodoListApp {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final String FILE_NAME = "tasks.txt"; // టాస్క్‌లు సేవ్ అయ్యే ఫైల్ పేరు

    public static void main(String[] args) {
        loadTasksFromFile(); // అప్లికేషన్ స్టార్ట్ అవ్వగానే ఫైల్ నుండి పాత టాస్క్‌లను లోడ్ చేయండి

        // 'try-with-resources' వాడటం వలన Scanner αυτόματα close అవుతుంది. ఇది మంచి పద్ధతి.
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu(); // యూజర్‌కు ఆప్షన్లు చూపించండి

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the leftover newline character

                    switch (choice) {
                        case 1:
                            addTask(scanner);
                            break;
                        case 2:
                            viewTasks();
                            break;
                        case 3:
                            markTaskAsDone(scanner);
                            break;
                        case 4:
                            deleteTask(scanner);
                            break;
                        case 5:
                            saveTasksToFile();
                            System.out.println("Tasks saved. Goodbye!");
                            return; // ప్రోగ్రామ్‌ను ఆపండి
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // తప్పు ఇన్‌పుట్‌ను క్లియర్ చేయండి
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- To-Do List Menu ---");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Mark Task as Done");
        System.out.println("4. Delete Task");
        System.out.println("5. Save and Exit");
        System.out.print("Choose an option: ");
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter the task description: ");
        String description = scanner.nextLine();
        if (description.trim().isEmpty()) {
            System.out.println("Task description cannot be empty.");
        } else {
            tasks.add(new Task(description));
            System.out.println("Task added successfully!");
        }
    }

    private static void viewTasks() {
        System.out.println("\n--- Your Tasks ---");
        if (tasks.isEmpty()) {
            System.out.println("Your to-do list is empty!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void markTaskAsDone(Scanner scanner) {
        viewTasks();
        if (tasks.isEmpty()) {
            return;
        }

        System.out.print("Enter the task number to mark as done: ");
        try {
            int taskNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                tasks.get(taskNumber - 1).markAsDone();
                System.out.println("Task marked as done!");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private static void deleteTask(Scanner scanner) {
        viewTasks();
        if (tasks.isEmpty()) {
            return;
        }

        System.out.print("Enter the task number to delete: ");
        try {
            int taskNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                tasks.remove(taskNumber - 1);
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private static void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // ఫైల్ లేకపోతే, ఏమీ చేయవద్దు
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // description లో కామాలు ఉన్నా సమస్య రాకుండా ఉండటానికి, 2 భాగాలుగా మాత్రమే విడదీస్తున్నాం
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String description = parts[0];
                    boolean isDone = Boolean.parseBoolean(parts[1]);
                    tasks.add(new Task(description, isDone));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }
}
