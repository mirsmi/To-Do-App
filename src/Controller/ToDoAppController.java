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
        view.getAddButton().addActionListener(e -> view.showAddTaskDialog(this));
        view.getMarkCompletedButton().addActionListener(e -> markTaskAsCompleted(view.getTable().getSelectedRow()));
        view.getDeleteButton().addActionListener(e -> deleteTask(view.getTable().getSelectedRow()));

        initialize();
    }

    /**
     * Method that loads the tasks and shows the main view
     */
    private void initialize() {
        try {
            loadTasks();
            view.setVisible(true);
        } catch (Exception e) {
            System.out.println("Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a new task to the task manager, updates the table, and saves the tasks
     *
     * @param title title of the task
     * @param description description of the task
     * @param dueDate due date of the task
     * @param category category of the task
     * @param priority priority of the task
     * @return true if task is added successfully
     */
    public boolean addTask(String title, String description, String dueDate, TaskCategory category, String priority) {
        String errorMessage = validateTask(title, dueDate, priority, category);
        if (errorMessage != null) {
            System.out.println("Failed to add task: " + errorMessage);
            return false;
        }

        Task task = new Task(title, description, dueDate, category.name(), priority, false);

        if (taskManager.addTask(task)) {
            updateTable(); //refresh the table to display the new task
            taskManager.saveTasks();
            return true;
        }
        else {
            System.out.println("Failed to add task: the task is null!");
            return false;
        }
    }

    /**
     * Method that validates the task
     * @param title task title
     * @param dueDate tasks due date
     * @param priority task priority
     * @param category task category
     * @return info about the validity of the task
     */
    private String validateTask(String title, String dueDate, String priority, TaskCategory category) {
        if (title == null || title.isBlank()) {
            return "Title cannot be null or blank!";
        }

        if (!isValidDate(dueDate)) {
            return "Due date must be in the format YYYY-MM-DD!";
        }

        if (!isValidPriority(priority)) {
            return "Task priority must be low, medium, or high!";
        }

        if (category == null) {
            return "Task category cannot be null1";
        }

        return null;
    }

    /**
     * Check if priority is valid
     * @param priority the task priority
     * @return true if valid
     */
    private boolean isValidPriority(String priority) {
        return "Low".equalsIgnoreCase(priority) || "Medium".equalsIgnoreCase(priority) || "High".equalsIgnoreCase(priority);
    }

    /**
     * Checks if date format is valid
     * @param dueDate the task due date
     * @return true if due date is valid
     */
    private boolean isValidDate(String dueDate) {
        if (dueDate == null) {
            return false;
        }

        //YYYY-MM-DD format
        String format = "\\d{4}-\\d{2}-\\d{2}";

        return dueDate.matches(format);
    }

    /**
     * Marks a selected task as completed
     * @param rowIndex index of the task in the table
     */
    public boolean markTaskAsCompleted(int rowIndex){
        String errorMessage = validateRowIndex(rowIndex);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(view, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean isMarked = taskManager.markTaskAsCompleted(rowIndex);

        if (isMarked) {
            taskManager.saveTasks(); //save changes after marking the task
            updateTable(); //refresh the table to reflect the updated status
            return true;
        }

        JOptionPane.showMessageDialog(view, "Failed to mark the task as completed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * Deletes a selected task from the task manager
     * @param rowIndex index of the task in the table
     */
    public boolean deleteTask(int rowIndex) {
        String errorMessage = validateRowIndex(rowIndex);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(view, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (taskManager.deleteTask(rowIndex)) {
            taskManager.saveTasks(); //save changes after deleting the task
            updateTable(); //refresh the table to remove the task
            return true;
        }

        JOptionPane.showMessageDialog(view, "Failed to delete the task. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * Method that validates the index
     * @param rowIndex the chosen index
     * @return true if index is valid
     */
    private String validateRowIndex(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= taskManager.getAllTasks().size()) {
            return "Please select a valid task!";
        }

        return null;
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
            tableModel.addRow(new Object[]{task.getTitle(), task.getDescription(), task.getDueDate(),
                    task.getCategory(), task.getPriority(), task.isCompleted() ? "Completed" : "Pending"
            });
        }
    }
}