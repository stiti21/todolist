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


package com.mycompany.finalkanbanwithreminders;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    // Modified to accept Task directly
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added: " + task.getDescription());
    }

    public void removeTask(String description) {
        tasks.removeIf(task -> task.getDescription().equals(description));
        System.out.println("Task removed: " + description);
    }

    public void displayAllTasks() {
        System.out.println("Current Tasks:");
        for (Task task : tasks) {
            System.out.println("- " + task.getDescription() + " | Priority: " + task.getPriority());
        }
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
