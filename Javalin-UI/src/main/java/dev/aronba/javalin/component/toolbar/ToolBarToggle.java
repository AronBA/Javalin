package dev.aronba.javalin.component.toolbar;

import javax.swing.*;

@FunctionalInterface
public interface ToolBarToggle {

    void onButtonToggle(JPanel container);
}
