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

