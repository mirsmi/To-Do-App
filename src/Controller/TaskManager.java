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
    private final String filePath = "todolist.txt"; //file path

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
     * @return true if the tasks were successfully saved, false otherwise
     */
    public boolean saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(tasks); //write the tasks list to the file
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
            e.printStackTrace();
            return false;
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
     * @param index index of the task to be marked as completed
     * @return true if task is marked as complete
     */
    public boolean markTaskAsCompleted(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        Task task = tasks.get(index);

        if (task != null) {
            task.setCompleted(true); //update the completed status of the task
            return true;
        }
        return false;
    }

    /**
     * Adds a new task to the list
     * @param task task to be added
     * @return true if task is added
     */
    public boolean addTask(Task task) {
        if (task == null) {
            return false; //task is null, cannot be added
        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return false;
        }
        
        tasks.add(task); //add task to the list
        return true;
    }

    /**
     * Deletes a task from the list based on its index
     * @param index The index of the task to be deleted
     * @return true if task is deleted
     */
    public boolean deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false; //index is invalid
        }

        //remove the task from  the list
        tasks.remove(index);
        return true;
    }

}
