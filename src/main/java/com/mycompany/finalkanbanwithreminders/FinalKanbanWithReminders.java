// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Solaf Ahmed Al-Titi - 2021904034
// - Ruqaia  Manwar - 2023905070
// -----------------------------------------------------
/*
 * This class is responsible for the main graphical user interface (GUI) of the application.
 * It initializes the Kanban Board with three main columns: "To Do", "In Progress", and "Done".
 * Each column allows adding, moving, and removing tasks with visual representation.
 * The class also handles task creation and the integration with ReminderScheduler for notifications.
 */

package com.mycompany.finalkanbanwithreminders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.datatransfer.*;

public class FinalKanbanWithReminders extends JFrame {

    // Constructor to set up the main GUI window
    public FinalKanbanWithReminders() {
        setTitle("Kanban Board with Reminders");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent automatic close
        setLocationRelativeTo(null);

        // 🔹 Add a Window Listener to handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Confirmation message before closing
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit the application?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
                );

                // If user clicks "Yes", show goodbye message and close
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Thank you for using the To-Do List Application. Goodbye! 👋",
                        "Program Terminated",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0); // Terminate the program
                }
            }
        });

        // Main panel for the entire Kanban Board
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 240, 240));
        add(mainPanel);

        // Setup the three main columns (To Do, In Progress, Done)
        JPanel columns = new JPanel(new GridLayout(1, 3, 15, 15));
        columns.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Add columns to the board
        addColumn(columns, "To Do", true);
        addColumn(columns, "In Progress", false);
        addColumn(columns, "Done", false);

        mainPanel.add(columns, BorderLayout.CENTER);
    }

    // Method to create and style columns in the Kanban Board
    private void addColumn(JPanel parent, String title, boolean canAddTasks) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBackground(new Color(255, 230, 230));
        column.setBorder(BorderFactory.createLineBorder(new Color(220, 180, 180), 2));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        column.add(titleLabel);
        column.add(Box.createRigidArea(new Dimension(0, 10)));

        // If adding tasks is allowed, show the button
        if (canAddTasks) {
            JButton addBtn = new JButton("+ Add Task");
            styleButton(addBtn);
            addBtn.addActionListener(e -> showAddTaskDialog(column));
            column.add(addBtn);
            column.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        setupDropTarget(column); // Enable drag-and-drop
        parent.add(column);
    }

    // Dialog window to create a new task
    private void showAddTaskDialog(JPanel targetColumn) {
        JDialog dialog = new JDialog(this, "Add Task with Reminder", true);
        dialog.setLayout(new GridLayout(0, 1, 10, 10));
        dialog.setSize(350, 300);

        // Input fields for task description, priority, and reminder time
        JTextField taskField = new JTextField();
        taskField.setToolTipText("Enter the task description"); // Tooltip for clarity
        
        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        priorityCombo.setToolTipText("Select the priority level"); // Tooltip for clarity
        
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "MM/dd/yyyy hh:mm a");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setToolTipText("Select the reminder date and time"); // Tooltip for clarity

        // Add labels and inputs to the dialog
        dialog.add(new JLabel("Task Description:"));
        dialog.add(taskField);
        dialog.add(new JLabel("Priority:"));
        dialog.add(priorityCombo);
        dialog.add(new JLabel("Reminder Time:"));
        dialog.add(timeSpinner);

        // Save button to add the task
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
            } else {
                JOptionPane.showMessageDialog(
                    dialog,
                    "❌ Task description cannot be empty!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        dialog.add(saveBtn);
        dialog.setVisible(true);
    }

    // Create and add the task card to the board
    private void addTaskWithReminder(JPanel column, String text, String priority, java.util.Date reminderTime) {
        Color color = getPriorityColor(priority);
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        card.setPreferredSize(new Dimension(180, 80));

        JLabel textLabel = new JLabel("<html><center>" + text + "</center></html>");
        card.add(textLabel, BorderLayout.CENTER);

        // Display the reminder time
        String timeStr = formatReminderTime(reminderTime);
        JLabel timeLabel = new JLabel("<html><small>" + timeStr + "</small></html>", SwingConstants.RIGHT);
        card.add(timeLabel, BorderLayout.SOUTH);

        setupDragSource(card); // Enable drag-and-drop
        column.add(card);
        column.revalidate();

        scheduleReminder(text, reminderTime); // Schedule the reminder
    }

    // Format the reminder time to a readable format
    private String formatReminderTime(java.util.Date time) {
        return DateTimeFormatter.ofPattern("MMM dd, hh:mm a")
            .format(LocalDateTime.ofInstant(time.toInstant(), java.time.ZoneId.systemDefault()));
    }
}
