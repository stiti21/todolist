/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 *
 * @author yu
 */
public class Task {



    private String taskDescription;
    private String taskPriority;
    private LocalDate date;
    private LocalTime time;
    
    
    public Task(String description, String priority,  LocalDate date, LocalTime time) {
        this.taskDescription = description;
        this.taskPriority = priority;
        this.date = date;
        this.time = time;
    
}
    public String getDescription() {
        return taskDescription; 
    }
    public String getPriority(){
        return taskPriority;
    }
     public LocalDate getDate() {
        return date;
    }
      public LocalTime getTime() {
        return time;
    }
}

    


