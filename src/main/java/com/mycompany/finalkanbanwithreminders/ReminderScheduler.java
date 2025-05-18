// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Solaf Ahmed Al-Titi - 2021904034
// - Ruqaia  Manwar - 2023905070
// -----------------------------------------------------

/*
 * This class is responsible for scheduling reminders for tasks.
 * It calculates the remaining time before the reminder and triggers a notification.
 * If the task time has passed, it notifies the user immediately.
 * It uses Java Timer to handle the countdown and trigger the popup alert.
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist;
import java.time.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
/**
 *
 * @author roqaia
 */
public class ReminderScheduler {
    public void schedule(Task task){
    LocalTime taskTime= task.getTime();
    LocalDate taskDate=task.getDate();
    LocalDateTime taskDateTime = LocalDateTime.of(taskDate, taskTime);
    LocalDateTime now = LocalDateTime.now();
    long delayInMillis = Duration.between(now, taskDateTime).toMillis(); //in millis 
    if (delayInMillis < 0) {
        JOptionPane.showMessageDialog(null,"Reminder time has already passed.");
       
    }
    else if (delayInMillis == 0) {
        showReminder(task);
       
    }
    else{
     String remaining = getTimeRemaining(now, taskDateTime);
     JOptionPane.showMessageDialog(null, remaining, "Time Remaining", JOptionPane.INFORMATION_MESSAGE);
     Timer timer = new Timer();
     timer.schedule(new TimerTask() {
        @Override
        public void run() {
            showReminder(task);
            timer.cancel();
        }
    }, delayInMillis);
    }
    }
    private void showReminder(Task task) {
    JOptionPane.showMessageDialog(
        null,
        "🔔 Reminder: " + task.getDescription(),
        "Task Reminder",
        JOptionPane.INFORMATION_MESSAGE
    );
}
    public String getTimeRemaining(LocalDateTime now, LocalDateTime taskTime) {

    Duration duration = Duration.between(now, taskTime);
    long hours = duration.toHours();
    long minutes = duration.toMinutes() % 60;
    long seconds = duration.getSeconds() % 60;
    return String.format("Time remaining: %02d hours %02d minutes %02d seconds", hours, minutes, seconds);
}

}

