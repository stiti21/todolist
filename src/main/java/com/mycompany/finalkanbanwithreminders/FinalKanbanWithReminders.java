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
import java.awt.datatransfer.*;
import java.time.*;
import java.time.format.*;

public class FinalKanbanWithReminders extends JFrame {
    
    public FinalKanbanWithReminders() {
        setTitle("Kanban Board");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel cols = new JPanel(new GridLayout(1,3,15,15));
        cols.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        
        addColumn(cols, "To Do", true);
        addColumn(cols, "In Progress", false);
        addColumn(cols, "Done", false);
        
        main.add(cols);
        add(main);
    }

    void addColumn(JPanel p, String title, boolean canAdd) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBorder(BorderFactory.createLineBorder(new Color(220,180,180),2));
        
        col.add(new JLabel(title){{setFont(new Font("Arial",Font.BOLD,16));}});
        col.add(Box.createRigidArea(new Dimension(0,10)));
        
        if(canAdd) col.add(new JButton("+ Add Task"){{
            setBackground(new Color(255,180,180));
            setForeground(Color.WHITE);
            addActionListener(e->showAddDialog(col));
        }});
        
        setupDrop(col);
        p.add(col);
    }

    void showAddDialog(JPanel col) {
        JDialog d = new JDialog(this,"Add Task",true);
        d.setLayout(new GridLayout(0,1,10,10));
        
        JTextField tf = new JTextField();
        JComboBox<String> cb = new JComboBox<>(new String[]{"High","Medium","Low"});
        JSpinner sp = new JSpinner(new SpinnerDateModel());
        sp.setEditor(new JSpinner.DateEditor(sp,"MM/dd/yyyy hh:mm a"));
        
        d.add(new JLabel("Task:"));
        d.add(tf);
        d.add(new JLabel("Priority:"));
        d.add(cb);
        d.add(new JLabel("Reminder:"));
        d.add(sp);
        d.add(new JButton("Save"){{
            addActionListener(e->{
                if(!tf.getText().isEmpty()) {
                    addTask(col,tf.getText(),(String)cb.getSelectedItem(),(java.util.Date)sp.getValue());
                    d.dispose();
                }
            });
        }});
        
        d.setSize(350,300);
        d.setVisible(true);
    }

    void addTask(JPanel col, String text, String pri, java.util.Date time) {
        Color c = switch(pri) {
            case "High"->new Color(255,200,200);
            case "Medium"->new Color(255,220,180);
            default->new Color(220,255,200);
        };
        
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(c);
        card.setBorder(BorderFactory.createLineBorder(c.darker(),2));
        card.setPreferredSize(new Dimension(180,80));
        
        card.add(new JLabel("<html><center>"+text+"</center></html>"));
        String t = DateTimeFormatter.ofPattern("MMM dd, hh:mm a")
                   .format(LocalDateTime.ofInstant(time.toInstant(),ZoneId.systemDefault()));
        card.add(new JLabel("<html><small>"+t+"</small></html>",SwingConstants.RIGHT),BorderLayout.SOUTH);
        
        setupDrag(card);
        col.add(card);
        col.revalidate();
        
        JOptionPane.showMessageDialog(this,"Reminder set for:\n"+t+"\n\nTask: "+text);
    }

    void setupDrag(JPanel card) {
        card.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ((JComponent)e.getSource()).getTransferHandler().exportAsDrag((JComponent)e.getSource(),e,TransferHandler.MOVE);
            }
        });
        
        card.setTransferHandler(new TransferHandler("bg") {
            protected Transferable createTransferable(JComponent c) {
                JPanel p = (JPanel)c;
                return new StringSelection(
                    ((JLabel)p.getComponent(0)).getText()+"||"+
                    (p.getComponentCount()>1?((JLabel)p.getComponent(1)).getText():"")+"||"+
                    p.getBackground().getRGB()
                );
            }
            public int getSourceActions(JComponent c) { return TransferHandler.MOVE; }
            protected void exportDone(JComponent c, Transferable t, int a) {
                if(a==TransferHandler.MOVE) {
                    c.getParent().remove(c);
                    c.getParent().revalidate();
                }
            }
        });
    }

    void setupDrop(JPanel col) {
        col.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferSupport s) {
                col.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
                return s.isDataFlavorSupported(DataFlavor.stringFlavor);
            }
            public boolean importData(TransferSupport s) {
                try {
                    String[] d = ((String)s.getTransferable().getTransferData(DataFlavor.stringFlavor)).split("\\|\\|");
                    JPanel card = new JPanel(new BorderLayout());
                    card.setBackground(new Color(Integer.parseInt(d[2])));
                    card.setBorder(BorderFactory.createLineBorder(new Color(220,180,180),2));
                    card.setPreferredSize(new Dimension(180,80));
                    card.add(new JLabel(d[0]));
                    if(!d[1].isEmpty()) card.add(new JLabel(d[1],SwingConstants.RIGHT),BorderLayout.SOUTH);
                    setupDrag(card);
                    col.add(card);
                    col.revalidate();
                    return true;
                } catch(Exception e) { return false; }
                finally { col.setBorder(BorderFactory.createLineBorder(new Color(220,180,180),2)); }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new FinalKanbanWithReminders().setVisible(true));
    }
}
