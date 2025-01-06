package View;

import Controller.ToDoAppController;
import Model.DateLabelFormatter;
import Model.TaskCategory;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Class responsible for user interface
 */
public class ToDoAppView extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private JButton addButton;
    private JButton markCompletedButton;
    private JButton deleteButton;

    public ToDoAppView() {
        //set up the main frame
        configureMainFrame();

        //create UI components
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = createTableModel();
        table = createTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel buttonPanel = createButtonPanel();

        //add components to the frame
        panel.add(scrollPane, BorderLayout.CENTER); //table with scroll pane
        panel.add(buttonPanel, BorderLayout.SOUTH); //buttons at the bottom
        add(panel);
    }

    /**
     * Configures the main frame properties
     */
    private void configureMainFrame() {
        setTitle("To-Do List Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //center the frame on screen
    }

    /**
     * Creates the table model with column headers for task attributes
     * @return A DefaultTableModel instance
     */
    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"Title", "Description", "Due Date", "Category", "Priority", "Status"},
                0
        );
    }

    /**
     * Creates a JTable to display tasks using the provided table model
     * @param model The table model to use
     * @return A JTable instance
     */
    private JTable createTable(DefaultTableModel model) {
        return new JTable(model);
    }

    /**
     * Creates a panel with buttons for adding, marking tasks as completed, and deleting tasks
     * @return A JPanel containing the buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        addButton = new JButton("Add Task");
        markCompletedButton = new JButton("Mark as Completed");
        deleteButton = new JButton("Delete Task");

        buttonPanel.add(addButton);
        buttonPanel.add(markCompletedButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    /**
     * Displays a dialog for adding a new task and handles user input
     * @param controller The ToDoAppController instance to handle task creation
     */
    public void showAddTaskDialog(ToDoAppController controller) {
        JTextField titleField = createTextField();
        JTextField descriptionField = createTextField();
        JComboBox<TaskCategory> categoryField = createCategoryComboBox();
        JComboBox<String> priorityBox = createPriorityComboBox();

        JPanel datePickerPanel = createDatePickerPanel();

        JPanel panel = createTaskInputPanel(titleField, descriptionField, categoryField, priorityBox, datePickerPanel);

        //show the dialog and handle the result
        int result = JOptionPane.showConfirmDialog(this, panel, "Add new task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleTaskDialogSubmission(controller, titleField, descriptionField, categoryField, priorityBox, datePickerPanel);
        }
    }

    /**
     * Creates a panel for the date picker component to select a due date
     * @return A JPanel containing the date picker
     */
    private JPanel createDatePickerPanel() {
        JPanel datePickerPanel = new JPanel();
        JLabel dateLabel = new JLabel("Due Date:");
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerPanel.add(dateLabel);
        datePickerPanel.add(datePicker);
        return datePickerPanel;
    }

    /**
     * Creates a panel with fields for entering task details
     * @return A JPanel containing the input fields
     */
    private JPanel createTaskInputPanel(JTextField titleField, JTextField descriptionField, JComboBox<TaskCategory> categoryField, JComboBox<String> priorityBox, JPanel datePickerPanel) {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityBox);
        panel.add(datePickerPanel);
        return panel;
    }

    /**
     * Handles task submission by retrieving user input and calling the controller's addTask method
     * @param controller The ToDoAppController instance
     * @param titleField The title input field
     * @param descriptionField The description input field
     * @param categoryField The category dropdown
     * @param priorityBox The priority dropdown
     * @param datePickerPanel The date picker panel
     */
    private void handleTaskDialogSubmission(ToDoAppController controller, JTextField titleField, JTextField descriptionField, JComboBox<TaskCategory> categoryField, JComboBox<String> priorityBox, JPanel datePickerPanel) {
        JDatePickerImpl datePicker = (JDatePickerImpl) datePickerPanel.getComponent(1);
        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        String dueDate = selectedDate != null ? new SimpleDateFormat("yyyy-MM-dd").format(selectedDate) : "No Date";
        controller.addTask(titleField.getText(), descriptionField.getText(), dueDate,
                (TaskCategory) categoryField.getSelectedItem(), (String) priorityBox.getSelectedItem());
    }

    /**
     * Creates a dropdown for selecting task priority
     * @return A JComboBox containing priority options - high, medium and low
     */
    private JComboBox<String> createPriorityComboBox() {
        return new JComboBox<>(new String[]{"High", "Medium", "Low"});
    }

    /**
     * Creates a text field for user input
     * @return A JTextField instance
     */
    private JTextField createTextField() {
        return new JTextField();
    }

    /**
     * Creates a dropdown for selecting task categories
     * @return A JComboBox containing TaskCategory values
     */
    private JComboBox<TaskCategory> createCategoryComboBox() {
        return new JComboBox<>(TaskCategory.values());
    }

    /**
     * Getter for the task table
     * @return The JTable displaying tasks
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Getter for the table model
     * @return The DefaultTableModel used by the table
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Getter for the Add Task button
     * @return The JButton for adding tasks
     */
    public JButton getAddButton() {
        return addButton;
    }

    /**
     * Getter for the Mark as Completed button
     * @return The JButton for marking tasks as completed
     */
    public JButton getMarkCompletedButton() {
        return markCompletedButton;
    }

    /**
     * Getter for the Delete Task button
     * @return The JButton for deleting tasks
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }
}
