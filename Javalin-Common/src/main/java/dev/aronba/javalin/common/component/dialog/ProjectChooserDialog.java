package dev.aronba.javalin.common.component.dialog;

import dev.aronba.javalin.common.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.function.Consumer;

public class ProjectChooserDialog extends JDialog {
    private final Consumer<Project> projectConsumer;
    private JList<Project> projectList;

    public ProjectChooserDialog(List<Project> projects, Consumer<Project> callback) {

        super((Frame) null, true);
        this.projectConsumer = callback;
        this.setSize(400, 400);
        this.setTitle("Welcome to Javelin!");
        this.setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        addTopPanel();
        addProjectionSelection(projects);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);
    }

    private void addTopPanel() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JButton("Create"));
        jPanel.add(new JButton("Open"));
        jPanel.add(new JButton("Clone"));
        add(jPanel, BorderLayout.NORTH);
    }

    private void addProjectionSelection(List<Project> projects) {
        projectList = new JList<>(new DefaultListModel<>());
        DefaultListModel<Project> model = (DefaultListModel<Project>) projectList.getModel();
        for (Project project : projects) {
            model.addElement(project);
        }

        projectList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getName());
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            label.setOpaque(true);
            return label;
        });


        projectList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Project selectedProject = projectList.getSelectedValue();
                    if (selectedProject != null) {
                        projectConsumer.accept(selectedProject);
                        dispose();
                    }
                }
            }
        });

        add(projectList, BorderLayout.CENTER);
    }
}



