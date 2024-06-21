package dev.aronba.javalin.component.menubar;

import dev.aronba.javalin.Javalin;
import dev.aronba.javalin.common.util.Compiler;
import dev.aronba.javalin.common.project.Project;
import dev.aronba.javalin.common.project.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private final static Logger LOG = LoggerFactory.getLogger(MenuBar.class);


    public MenuBar(Javalin javalin) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        newFile.addActionListener(actionEvent -> {
        });
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem editFile = new JMenuItem("Close");
        JMenuItem saveFile = new JMenuItem("Save");

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(editFile);
        fileMenu.add(saveFile);


        this.add(fileMenu);


        PluginMenuItem plugin = new PluginMenuItem(javalin.getPluginManager().getPluginLoader());
        this.add(plugin);

        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);

        JMenu gitMenu = new JMenu("Git");
        this.add(gitMenu);

        JMenu runMenu = new JMenu("Run");
        JMenuItem run = new JMenuItem("Run project");
        run.addActionListener(actionEvent -> {
            try {
                Compiler.compileProject(javalin.getProjectManager().getCurrentProject().getRootFile());
                Project project = javalin.getProjectManager().getCurrentProject();
                Runner.run(project);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }

        });
        runMenu.add(run);
        this.add(runMenu);
    }


}
