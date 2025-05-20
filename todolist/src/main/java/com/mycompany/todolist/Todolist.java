/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.todolist;

import javax.swing.SwingUtilities;

/**
 *
 * @author yu
 */
public class Todolist {

 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinalKanbanWithReminders board = new FinalKanbanWithReminders();
            board.setVisible(true);
        });
    }
}
