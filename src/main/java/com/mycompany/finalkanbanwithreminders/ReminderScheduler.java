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
package com.mycompany.finalkanbanwithreminders;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Duration;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Responsible for scheduling reminders for tasks.
 * It uses Java Timer to handle countdown and trigger the reminder alert.
 */
public class ReminderScheduler {

    /**
     * Schedules a reminder for the specified task.
     */
    public void schedule(Task task) {
        LocalDateTime taskDateTime = LocalDateTime.of(task.getDate(), task.getTime());
        LocalDateTime now = LocalDateTime.now();

        long delayInMillis = Duration.between(now, taskDateTime).toMillis();

        // If the reminder time is in the past, show a warning
        if (delayInMillis < 0) {
            JOptionPane.showMessageDialog(
                null,
                "Reminder time has already passed.",
                "Time Error",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Schedule the reminder
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showReminder(task);
                timer.cancel();
            }
        }, delayInMillis);
    }

    /**
     * Displays the reminder as a dialog window.
     */
    private void showReminder(Task task) {
        JOptionPane.showMessageDialog(
            null,
            "🔔 Reminder: " + task.getDescription(),
            "Task Reminder",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
