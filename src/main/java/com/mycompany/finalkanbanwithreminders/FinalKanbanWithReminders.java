// -----------------------------------------------------
// Mini Project 1 - To-Do List with Reminders
// Written by: 
// - Mahasen Al-Tamimi - 2021901162
// - Sulaf Ahmed Al-Titi - 2021904034
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
    
    public FinalKanbanWithReminders() {
        setTitle("Kanban Board with Reminders");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 240, 240));
        add(mainPanel);

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

        setupDropTarget(column);
        parent.add(column);
    }

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
        Color color = getPriorityColor(priority);
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        card.setPreferredSize(new Dimension(180, 80));

        JLabel textLabel = new JLabel("<html><center>" + text + "</center></html>");
        card.add(textLabel, BorderLayout.CENTER);

        String timeStr = formatReminderTime(reminderTime);
        JLabel timeLabel = new JLabel("<html><small>" + timeStr + "</small></html>", SwingConstants.RIGHT);
        card.add(timeLabel, BorderLayout.SOUTH);

        setupDragSource(card);
        column.add(card);
        column.revalidate();

        scheduleReminder(text, reminderTime);
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

    // ===== DRAG AND DROP IMPLEMENTATION =====
    private void setupDragSource(JPanel card) {
        card.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.MOVE);
            }
        });

        card.setTransferHandler(new TransferHandler("background") {
            @Override
            public int getSourceActions(JComponent c) {
                return TransferHandler.MOVE;
            }

            @Override
            protected Transferable createTransferable(JComponent c) {
                JPanel card = (JPanel)c;
                String text = ((JLabel)card.getComponent(0)).getText();
                String time = card.getComponentCount() > 1 ? ((JLabel)card.getComponent(1)).getText() : "";
                Color bg = card.getBackground();
                return new StringSelection(text + "||" + time + "||" + bg.getRGB());
            }

            @Override
            protected void exportDone(JComponent source, Transferable data, int action) {
                if (action == TransferHandler.MOVE) {
                    Container parent = source.getParent();
                    parent.remove(source);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
    }

    private void setupDropTarget(JPanel column) {
        column.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                column.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                return support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                try {
                    String data = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    String[] parts = data.split("\\|\\|");
                    
                    JPanel newCard = new JPanel(new BorderLayout());
                    newCard.setBackground(new Color(Integer.parseInt(parts[2])));
                    newCard.setBorder(BorderFactory.createLineBorder(new Color(220, 180, 180), 2));
                    newCard.setPreferredSize(new Dimension(180, 80));
                    
                    JLabel textLabel = new JLabel(parts[0]);
                    newCard.add(textLabel, BorderLayout.CENTER);
                    
                    if (!parts[1].isEmpty()) {
                        JLabel timeLabel = new JLabel(parts[1], SwingConstants.RIGHT);
                        newCard.add(timeLabel, BorderLayout.SOUTH);
                    }
                    
                    setupDragSource(newCard);
                    column.add(newCard);
                    column.revalidate();
                    
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    column.setBorder(BorderFactory.createLineBorder(new Color(220, 180, 180), 2));
                }
            }
        });
    }

    private void scheduleReminder(String taskText, java.util.Date reminderTime) {
        String timeStr = formatReminderTime(reminderTime);
        JOptionPane.showMessageDialog(
            this, 
            "Reminder set for:\n" + timeStr + "\n\nTask: " + taskText,
            "Reminder Scheduled",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
    // 🌟 Welcome Message 
    System.out.println("-------------------------------------------------------------");
    System.out.println("🌟 Welcome to the To-Do List with Reminders Application! 🌟");
    System.out.println("Developed by: Mahasen Al-Tamimi, Sulaf Ahmed Al-Titi, Ruqaia Mohammed");
    System.out.println("-------------------------------------------------------------");

    SwingUtilities.invokeLater(() -> {
        FinalKanbanWithReminders board = new FinalKanbanWithReminders();
        board.setVisible(true);
    });
}

}
