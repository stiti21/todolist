// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Solaf Ahmed Al-Titi - 2021904034
// - Ruqaia  Manwar - 2023905070
// -----------------------------------------------------
/*
 * This class handles the core logic for managing tasks in the application.
 * It allows adding, removing, and listing tasks.
 * It also maintains a list of all tasks currently managed in the application.
 * TaskManager acts as the main connection between the GUI and the data layer.
 */
package com.mycompany.todolist; // Adjust this if Task.java is in a different package

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles the core logic for managing tasks in the application.
 * Allows adding, removing, listing, and querying tasks.
 */
public class TaskManager {

    // Store all current tasks
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a new task to the list
     * @param task the Task to be added
     * @throws IllegalArgumentException if the task is null
     */
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
    }

    /**
     * Removes an existing task from the list
     * @param task the Task to be removed
     * @return true if the task was present and removed, false otherwise
     */
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    /**
     * Returns an unmodifiable list of all current tasks
     * @return unmodifiable List of tasks
     */
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Finds tasks by priority (e.g., "High", "Medium", "Low")
     * @param priority the priority string to filter by
     * @return a list of tasks matching the given priority
     */
    public List<Task> getTasksByPriority(String priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getPriority().equalsIgnoreCase(priority)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Finds tasks scheduled for a specific date
     * @param date the date to filter tasks by
     * @return a list of tasks matching the given date
     */
    public List<Task> getTasksByDate(java.time.LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDate().equals(date)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Finds a task by its description, if it exists
     * @param description the description text of the task
     * @return the matching task, or null if none is found
     */
    public Task findTaskByDescription(String description) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                return t;
            }
        }
        return null;
    }
}
