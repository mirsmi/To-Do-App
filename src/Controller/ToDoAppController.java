package Controller;

import Model.Task;
import Model.TaskCategory;
import View.ToDoAppView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class responsible for managing the logic and interactions between the view and the model
 */
public class ToDoAppController {
    private final TaskManager taskManager; //manages the tasks
    private final ToDoAppView view; //handles the user interface

    /**
     * Constructor to initialize the controller, set up listeners, and load tasks
     */
    public ToDoAppController() {
        taskManager = new TaskManager();
        view = new ToDoAppView();

        //add listeners to buttons
        view.getAddButton().addActionListener(_ -> view.showAddTaskDialog(this));
        view.getMarkCompletedButton().addActionListener(_ -> markTaskAsCompleted(view.getTable().getSelectedRow()));
        view.getDeleteButton().addActionListener(_ -> deleteTask(view.getTable().getSelectedRow()));

        //load tasks initially
        loadTasks();
        view.setVisible(true); //make the application window visible
    }

    /**
     * Adds a new task to the task manager, updates the table, and saves the tasks
     * @param title The title of the task
     * @param description The description of the task
     * @param dueDate The due date of the task
     * @param category The category of the task
     * @param priority The priority of the task
     */
    public void addTask(String title, String description, String dueDate, TaskCategory category, String priority) {
        Task task = new Task(title, description, dueDate, category.name(), priority, false);
        taskManager.addTask(task);
        updateTable(); //refresh the table to display the new task
        taskManager.saveTasks(); //save the updated task list
    }

    /**
     * Marks a selected task as completed.
     * @param rowIndex The index of the task in the table
     */
    private void markTaskAsCompleted(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < taskManager.getAllTasks().size()) {
            taskManager.markTaskAsCompleted(rowIndex);
            taskManager.saveTasks(); //save changes after marking the task
            updateTable(); //refresh the table to reflect the updated status
        } else {
            JOptionPane.showMessageDialog(view, "Please select a valid task to mark as completed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes a selected task from the task manager
     * @param rowIndex The index of the task in the table
     */
    private void deleteTask(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < taskManager.getAllTasks().size()) {
            taskManager.deleteTask(rowIndex);
            taskManager.saveTasks(); //save changes after deleting the task
            updateTable(); //refresh the table to remove the task
        } else {
            JOptionPane.showMessageDialog(view, "Please select a valid task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads tasks from the task manager and populates the table
     */
    private void loadTasks() {
        if (taskManager.loadTasks()) {
            updateTable(); //populate the table with loaded tasks
        } else {
            JOptionPane.showMessageDialog(view, "Failed to load tasks.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the table view to reflect the current list of tasks
     */
    private void updateTable() {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0); //clear the table
        List<Task> tasks = taskManager.getAllTasks();

        //add each task as a row in the table
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getCategory(),
                    task.getPriority(),
                    task.isCompleted() ? "Completed" : "Pending"
            });
        }
    }
}