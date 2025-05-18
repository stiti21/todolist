// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Solaf Ahmed Al-Titi - 2021904034
// - Ruqaia  Manwar - 2023905070
// -----------------------------------------------------
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalkanbanwithreminders;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single Task in the Kanban board.
 * It holds the task description, priority, date, and time.
 */
public class Task {
    private String description;
    private String priority;
    private LocalDate date;
    private LocalTime time;

    /**
     * Constructor to initialize the Task object
     */
    public Task(String description, String priority, LocalDate date, LocalTime time) {
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.time = time;
    }

    /**
     * Getters for each attribute
     */
    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
