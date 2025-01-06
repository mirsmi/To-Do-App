package Controller;

import Model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskManager class handles the management of tasks
 */
public class TaskManager {
    private final List<Task> tasks = new ArrayList<>(); //list to store tasks
    private final String filePath = "todolist.txt"; //file path for task persistence

    /**
     * Loads the list of tasks from a file
     * @return true if the tasks were successfully loaded, false otherwise
     */
    public boolean loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Task> loadedTasks = (List<Task>) ois.readObject(); //read tasks from the file
            tasks.clear(); //clear the current tasks list
            tasks.addAll(loadedTasks); //add all loaded tasks to the list
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false; //return false if an IOException or ClassNotFoundException occurs
        }
    }

    /**
     * Saves the list of tasks to a file
     */
    public void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(tasks); //write the tasks list to the file
        } catch (IOException e) {
            System.out.println("Failed to save tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all tasks
     * @return A new list containing all tasks
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); //return a copy of the tasks list
    }

    /**
     * Marks a task as completed based on its index in the list
     * @param index The index of the task to be marked as completed
     */
    public void markTaskAsCompleted(int index) {
        tasks.get(index).setCompleted(true); //update the completed status of the task
    }

    /**
     * Adds a new task to the list
     * @param task The task to be added
     */
    public void addTask(Task task) {
        tasks.add(task); //add the task to the list
    }

    /**
     * Deletes a task from the list based on its index
     * @param index The index of the task to be deleted
     */
    public void deleteTask(int index) {
        tasks.remove(index); //remove the task from the list
    }
}
