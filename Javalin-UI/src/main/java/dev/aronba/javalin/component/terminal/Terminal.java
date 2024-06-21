package dev.aronba.javalin.component.terminal;

import javax.swing.*;
import java.awt.*;

public class Terminal extends JPanel {


    JTextArea textArea;

    public Terminal() {
        this.setLayout(new BorderLayout());
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.add(textArea, BorderLayout.CENTER);
    }


}
