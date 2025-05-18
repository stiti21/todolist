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
package com.mycompany.todolist;               // ← CHANGED: added correct package declaration

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles the core logic for managing tasks in the application.
 * Allows adding, removing, listing, and querying tasks.
 */
public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public List<Task> getTasksByPriority(String priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getPriority().equalsIgnoreCase(priority)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Task> getTasksByDate(java.time.LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDate().equals(date)) {
                result.add(t);
            }
        }
        return result;
    }

    public Task findTaskByDescription(String description) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                return t;
            }
        }
        return null;
    }
}

