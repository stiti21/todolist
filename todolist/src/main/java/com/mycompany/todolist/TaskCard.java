/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author yu
 */
public class TaskCard extends JPanel {
    private final Task task;

    public TaskCard(Task task) {
        this.task = task;
        setupCard();
    }

    private void setupCard() {
        setLayout(new BorderLayout());
        setBackground(getPriorityColor(task.getPriority()));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setPreferredSize(new Dimension(180, 80));

        JLabel descriptionLabel = new JLabel("<html><center>" + task.getDescription() + "</center></html>");
        add(descriptionLabel, BorderLayout.CENTER);

        if (task.getDate() != null && task.getTime() != null) {
            String timeText = task.getDate() + " " + 
                task.getTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
            JLabel timeLabel = new JLabel(timeText, SwingConstants.RIGHT);
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            add(timeLabel, BorderLayout.SOUTH);
        }
    }

    private Color getPriorityColor(String priority) {
        return switch (priority) {
            case "High" -> new Color(255, 200, 200);
            case "Medium" -> new Color(255, 220, 180);
            case "Low" -> new Color(220, 255, 200);
            default -> Color.WHITE;
        };
    }

    public Task getTask() { return task; }
}
