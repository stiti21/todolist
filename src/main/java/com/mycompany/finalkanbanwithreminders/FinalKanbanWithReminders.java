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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class FinalKanbanWithReminders extends JFrame {

    // Main TaskManager to handle all tasks
    private final TaskManager taskManager;
    // Scheduler for handling task reminders
    private final ReminderScheduler reminderScheduler;

    /**
     * Main Constructor to set up the GUI window
     */
    public FinalKanbanWithReminders() {
        setTitle("Kanban Board with Reminders");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        taskManager = new TaskManager();            // Initialize Task Manager
        reminderScheduler = new ReminderScheduler(); // Initialize Reminder Scheduler

        // Handle window closing event with confirmation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit the application?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Thank you for using the To-Do List Application. Goodbye! 👋",
                        "Program Terminated",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });

        // Main Panel Layout Setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 240, 240));
        add(mainPanel);

        // Setup columns for Kanban Board (To Do, In Progress, Done)
        JPanel columns = new JPanel(new GridLayout(1, 3, 15, 15));
        columns.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        addColumn(columns, "To Do", true);
        addColumn(columns, "In Progress", false);
        addColumn(columns, "Done", false);

        mainPanel.add(columns, BorderLayout.CENTER);
    }

    /**
     * Method to create a Kanban column
     */
    private void addColumn(JPanel parent, String title, boolean canAddTasks) {
        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBackground(new Color(255, 230, 230));
        column.setBorder(BorderFactory.createLineBorder(new Color(220, 180, 180), 2));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        column.add(titleLabel);
        column.add(Box.createRigidArea(new Dimension(0, 10)));

        if (canAddTasks) {
            JButton addBtn = new JButton("+ Add Task");
            styleButton(addBtn);
            addBtn.addActionListener(e -> showAddTaskDialog(column));
            column.add(addBtn);
            column.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        parent.add(column);
    }

    /**
     * Method to show the dialog for adding a new task
     */
    private void showAddTaskDialog(JPanel targetColumn) {
        JDialog dialog = new JDialog(this, "Add Task with Reminder", true);
        dialog.setLayout(new GridLayout(0, 1, 10, 10));
        dialog.setSize(350, 300);

        JTextField taskField = new JTextField();
        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"High", "Medium", "Low"});
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

        // Add Task to the TaskManager and the UI
        saveBtn.addActionListener(e -> {
            if (!taskField.getText().isEmpty()) {
                Date reminderTime = (Date) timeSpinner.getValue();

                // Create the task object
                Task task = new Task(
                    taskField.getText(),
                    (String) priorityCombo.getSelectedItem(),
                    LocalDate.now(),
                    LocalTime.now()
                );

                // Add to TaskManager and UI
                taskManager.addTask(task); // ✅ This will work correctly now
                addTaskWithReminder(targetColumn, task, reminderTime);
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

    /**
     * Method to add a visual representation of the task to the board
     */
    private void addTaskWithReminder(JPanel column, Task task, Date reminderTime) {
        TaskCard card = new TaskCard(task);
        column.add(card);
        column.revalidate();
        reminderScheduler.schedule(task);
    }

    /**
     * Method to style the button consistently
     */
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(220, 220, 220));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FinalKanbanWithReminders::new);
    }
}
