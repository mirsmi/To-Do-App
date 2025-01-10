package Model;

import java.io.Serializable;


/**
 * Class that represents a task
 */
public class Task implements Serializable {
    private final String title;
    private final String dueDate;
    private final String category;
    private final String priority;
    private final String description;
    private boolean isCompleted;


    /**
     * Task constructor
     * @param title the title of the task
     * @param description task description
     * @param dueDate due date
     * @param category task category
     * @param priority task priority
     * @param isCompleted a boolean to check if a task is completed
     */
    public Task(String title, String description, String dueDate, String category, String priority, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
