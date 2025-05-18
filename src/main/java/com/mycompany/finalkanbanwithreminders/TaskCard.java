// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Sulaf Ahmed Al-Titi - 2021904034
// - Ruqaia  Manwar - 2023905070
// -----------------------------------------------------
/*
 * This class represents the visual display of a single task in the Kanban Board.
 * It creates a card with the task description, priority color, and reminder time.
 * TaskCard is added to the respective column (To Do, In Progress, Done).
 * It supports drag-and-drop functionality to move between columns.
 */

package com.mycompany.finalkanbanwithreminders;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TaskCard extends JPanel {
    private final Task task;

    // Constructor to initialize the task card with task data
    public TaskCard(Task task) {
        this.task = task;
        setupCard(); // Prepare the visual appearance of the card
    }

    // Method to set up the card's layout and display information
    private void setupCard() {
        setLayout(new BorderLayout());
        
        // Set the background color based on priority level
        setBackground(getPriorityColor(task.getPriority()));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setPreferredSize(new Dimension(180, 80));

        // Display the task description
        JLabel descriptionLabel = new JLabel("<html><center>" + task.getDescription() + "</center></html>");
        add(descriptionLabel, BorderLayout.CENTER);

        // Display the date and time if available
        if (task.getDate() != null && task.getTime() != null) {
            String timeText = task.getDate() + " " + 
                task.getTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
            
            // Display the formatted time on the card
            JLabel timeLabel = new JLabel(timeText, SwingConstants.RIGHT);
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            add(timeLabel, BorderLayout.SOUTH);
        }
    }

    // Method to get the color associated with the task priority
    private Color getPriorityColor(String priority) {
        return switch (priority) {
            case "High" -> new Color(255, 200, 200); // Red for High Priority
            case "Medium" -> new Color(255, 220, 180); // Orange for Medium Priority
            case "Low" -> new Color(220, 255, 200); // Green for Low Priority
            default -> Color.WHITE;
        };
    }

    // Getter to retrieve the task object
    public Task getTask() { 
        return task; 
    }
}
