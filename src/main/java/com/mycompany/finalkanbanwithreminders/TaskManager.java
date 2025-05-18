// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Sulaf Ahmed Al-Titi - 2021904034
// - Ruqaia  Manwar - 2023905070
// -----------------------------------------------------
/*
 * This class handles the core logic for managing tasks in the application.
 * It allows adding, removing, and listing tasks.
 * It also maintains a list of all tasks currently managed in the application.
 * TaskManager acts as the main connection between the GUI and the data layer.
 */


package com.mycompany.finalkanbanwithreminders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Mahasen Al-Tamimi
 * @description This class is responsible for managing tasks (Add, Remove, Display)
 */

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description, String priority, LocalDate date, LocalTime time) {
        Task task = new Task(description, priority, date, time);
        tasks.add(task);
        System.out.println("Task added: " + description);
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

