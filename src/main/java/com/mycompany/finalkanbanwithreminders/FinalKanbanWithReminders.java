/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalkanbanwithreminders;

/**
 *
 * @author User
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinalKanbanWithReminders extends JFrame {
    
    public FinalKanbanWithReminders() {
        // Basic setup
        setTitle("Kanban Board with Reminders");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 240, 240)); // Light pink
        add(mainPanel);

        // Create columns
        JPanel columns = new JPanel(new GridLayout(1, 3, 15, 15));
        columns.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        addColumn(columns, "To Do", true);
        addColumn(columns, "In Progress", false);
        addColumn(columns, "Done", false);

        mainPanel.add(columns, BorderLayout.CENTER);
    }

    private void addColumn(JPanel parent, String title, boolean canAddTasks) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBackground(new Color(255, 230, 230)); // Column pink
        column.setBorder(BorderFactory.createLineBorder(new Color(220, 180, 180), 2));

        // Column header
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        column.add(titleLabel);
        column.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add task button (only for To Do)
        if (canAddTasks) {
            JButton addBtn = new JButton("+ Add Task");
            styleButton(addBtn);
            addBtn.addActionListener(e -> showAddTaskDialog(column));
            column.add(addBtn);
            column.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        parent.add(column);
    }

    private void showAddTaskDialog(JPanel targetColumn) {
        JDialog dialog = new JDialog(this, "Add Task with Reminder", true);
        dialog.setLayout(new GridLayout(0, 1, 10, 10));
        dialog.setSize(350, 300);

        // Task fields
        JTextField taskField = new JTextField();
        JComboBox<String> priorityCombo = new JComboBox<>(
            new String[]{"High", "Medium", "Low"});
        
        // Reminder time
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "MM/dd/yyyy hh:mm a");
        timeSpinner.setEditor(timeEditor);

        dialog.add(new JLabel("Task Description:"));
        dialog.add(taskField);
        dialog.add(new JLabel("Priority:"));
        dialog.add(priorityCombo);
        dialog.add(new JLabel("Reminder Time:"));
        dialog.add(timeSpinner);

        JButton saveBtn = new JButton("Save with Reminder");
        saveBtn.addActionListener(e -> {
            if (!taskField.getText().isEmpty()) {
                addTaskWithReminder(
                    targetColumn,
                    taskField.getText(),
                    (String) priorityCombo.getSelectedItem(),
                    (java.util.Date) timeSpinner.getValue()
                );
                dialog.dispose();
            }
        });
        dialog.add(saveBtn);
        dialog.setVisible(true);
    }

    private void addTaskWithReminder(JPanel column, String text, String priority, java.util.Date reminderTime) {
        // Create task card
        Color color = getPriorityColor(priority);
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        card.setPreferredSize(new Dimension(180, 80));

        // Add task text
        JLabel textLabel = new JLabel("<html><center>" + text + "</center></html>");
        card.add(textLabel, BorderLayout.CENTER);

        // Add reminder time
        JLabel timeLabel = new JLabel(
            "<html><small>" + formatReminderTime(reminderTime) + "</small></html>",
            SwingConstants.RIGHT
        );
        card.add(timeLabel, BorderLayout.SOUTH);

        column.add(card);
        column.revalidate();

        // Schedule reminder
        scheduleReminder(text, reminderTime);
    }

    private void scheduleReminder(String taskText, java.util.Date reminderTime) {
        // In a real app, you would:
        // 1. Use Google Calendar API OR
        // 2. Use java.util.Timer for local reminders
        
        // This just shows a confirmation
        String timeStr = formatReminderTime(reminderTime);
        JOptionPane.showMessageDialog(
            this, 
            "Reminder set for:\n" + timeStr + "\n\nTask: " + taskText,
            "Reminder Scheduled",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String formatReminderTime(java.util.Date time) {
        return DateTimeFormatter.ofPattern("MMM dd, hh:mm a")
            .format(LocalDateTime.ofInstant(time.toInstant(), java.time.ZoneId.systemDefault()));
    }

    private Color getPriorityColor(String priority) {
        return switch (priority) {
            case "High" -> new Color(255, 200, 200);
            case "Medium" -> new Color(255, 220, 180);
            case "Low" -> new Color(220, 255, 200);
            default -> Color.WHITE;
        };
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(255, 180, 180));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinalKanbanWithReminders board = new FinalKanbanWithReminders();
            board.setVisible(true);
        });
    }
}