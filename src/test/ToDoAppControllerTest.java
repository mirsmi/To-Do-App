package test;

import Controller.ToDoAppController;
import Model.TaskCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoAppControllerTest {


    //test cases for the add method
    @Test
    void addValidTask(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        assertTrue(result);
    }

    @Test
    void addNullTask(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask(null, null, null, null, null);
        assertFalse(result);
    }

    @Test
    void addTaskWithNullTitle(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask(null, "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        assertFalse(result);
    }

    @Test
    void addTaskWithEmptyTitle(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask("", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        assertFalse(result);
    }

    @Test
    void addTaskWithInvalidDueDate(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask("Title", "Description", "20251010" ,TaskCategory.ERRANDS, "low");
        assertFalse(result);
    }

    @Test
    void addTaskWithNullCategory(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask("Task title", "Description", "2025-10-10" , null, "low");
        assertFalse(result);
    }

    @Test
    void addTaskWithInvalidPriority(){
        ToDoAppController toDoAppController = new ToDoAppController();
        boolean result = toDoAppController.addTask("Task title", "Description", "2025-10-10" , TaskCategory.ERRANDS, "priority");
        assertFalse(result);
    }



    //test cases for the delete method
    @Test
    void deleteWithValidIndex(){
        ToDoAppController toDoAppController = new ToDoAppController();
        toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        boolean result = toDoAppController.deleteTask(0);
        assertTrue(result);
    }

    @Test
    void deleteWithIndexOutOfBounds(){
        ToDoAppController toDoAppController = new ToDoAppController();
        toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        boolean result = toDoAppController.deleteTask(55);
        assertFalse(result);
    }

    @Test
    void deleteWithNegativeIndex(){
        ToDoAppController toDoAppController = new ToDoAppController();
        toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        boolean result = toDoAppController.deleteTask(-1);
        assertFalse(result);
    }



    //test cases for the markTaskAsCompleted method
    @Test
    void markTaskAsCompletedWithValidIndex(){
        ToDoAppController toDoAppController = new ToDoAppController();
        toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        boolean result = toDoAppController.markTaskAsCompleted(0);
        assertTrue(result);
    }

    @Test
    void markTaskAsCompletedWithNegativeIndex(){
        ToDoAppController toDoAppController = new ToDoAppController();
        toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        boolean result = toDoAppController.markTaskAsCompleted(-1);
        assertFalse(result);
    }

    @Test
    void markTaskAsCompletedWithOutOfBoundsIndex(){
        ToDoAppController toDoAppController = new ToDoAppController();
        toDoAppController.addTask("Title", "Description", "2025-10-10" ,TaskCategory.ERRANDS, "low");
        boolean result = toDoAppController.markTaskAsCompleted(55);
        assertFalse(result);
    }


}
