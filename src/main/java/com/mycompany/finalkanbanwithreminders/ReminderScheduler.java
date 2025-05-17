/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalkanbanwithreminders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class ReminderScheduler {
    
    /**
     * Schedules the reminder based on the task's date and time.
     * If the task time is in the past, it shows an error.
     * If the task time is now, it shows the reminder immediately.
     * If the task time is in the future, it schedules the reminder.
     */
    public void schedule(Task task) {
        // Get the date and time from the Task object
        LocalTime taskTime = task.getTime();
        LocalDate taskDate = task.getDate();
        LocalDateTime taskDateTime = LocalDateTime.of(taskDate, taskTime);
        LocalDateTime now = LocalDateTime.now();
        
        // Calculate the time difference in milliseconds
        long delayInMillis = Duration.between(now, taskDateTime).toMillis();
        
        // If the reminder time has already passed, show a warning message
        if (delayInMillis < 0) {
            JOptionPane.showMessageDialog(null, "Reminder time has already passed.", "Time Error", JOptionPane.WARNING_MESSAGE);
        } 
        // If the reminder time is exactly now, show the reminder immediately
        else if (delayInMillis == 0) {
            showReminder(task);
        } 
        // If the reminder is in the future, schedule it
        else {
            String remaining = getTimeRemaining(now, taskDateTime);
            JOptionPane.showMessageDialog(null, remaining, "Time Remaining", JOptionPane.INFORMATION_MESSAGE);
            
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showReminder(task);
                    timer.cancel(); // Stop the timer after execution
                }
            }, delayInMillis);
        }
    }

    /**
     * Displays the reminder as a dialog window.
     * It uses JOptionPane to make it visible to the user.
     */
    private void showReminder(Task task) {
        JOptionPane.showMessageDialog(
            null,
            "🔔 Reminder: " + task.getDescription(),
            "Task Reminder",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Calculates the remaining time until the reminder and formats it.
     * @param now - the current date and time
     * @param taskTime - the scheduled date and time of the task
     * @return - formatted string showing hours, minutes, and seconds remaining
     */
    public String getTimeRemaining(LocalDateTime now, LocalDateTime taskTime) {
        Duration duration = Duration.between(now, taskTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("Time remaining: %02d hours %02d minutes %02d seconds", hours, minutes, seconds);
    }
}

