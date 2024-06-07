package dev.aronba.javalin.component.terminal;

import javax.swing.*;
import java.awt.*;

public class Terminal extends JPanel {


    JTextArea textArea;
    JTextField textField;

    public Terminal(){

        this.setLayout(new BorderLayout());

        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.add(textArea, BorderLayout.CENTER);

        this.textField = new JTextField();
        this.add(textField, BorderLayout.SOUTH);

    }



}
