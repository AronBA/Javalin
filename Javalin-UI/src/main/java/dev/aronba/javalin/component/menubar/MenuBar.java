package dev.aronba.javalin.component.menubar;

import dev.aronba.javalin.Javalin;
import dev.aronba.javalin.util.FileUtils;

import javax.swing.*;

public class MenuBar extends JMenuBar {


    public MenuBar(Javalin javalin) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        newFile.addActionListener(actionEvent -> {});
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem editFile = new JMenuItem("Close");
        JMenuItem saveFile = new JMenuItem("Save");

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(editFile);
        fileMenu.add(saveFile);


        this.add(fileMenu);


        PluginMenuItem plugin = new PluginMenuItem(javalin.getPluginManager());
        this.add(plugin);

        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);

        JMenu gitMenu = new JMenu("Git");
        this.add(gitMenu);

        JMenu runMenu = new JMenu("Run");
        this.add(runMenu);
    }


}
