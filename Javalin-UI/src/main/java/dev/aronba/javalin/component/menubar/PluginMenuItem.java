package dev.aronba.javalin.component.menubar;

import dev.aronba.javalin.Javalin;
import dev.aronba.javalin.common.component.dialog.PluginDialog;
import dev.aronba.javalin.common.plugin.PluginLoader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PluginMenuItem extends JMenu {

    PluginMenuItem(PluginLoader pluginLoader) {
        super("Plugin");


        JMenuItem list = new JMenuItem("List Plugins");
        list.addActionListener(e -> {
            if (pluginLoader.getCurrentlyLoadedPlugins().isEmpty()) {
                return;
            }
            new PluginDialog(pluginLoader.getCurrentlyLoadedPlugins().get());
        });

        JMenuItem add = new JMenuItem("Add Plugin");
        add.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".jar");
                }

                @Override
                public String getDescription() {
                    return "JAR Files (*.jar)";
                }
            });

            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    Files.move(file.toPath(), Javalin.PLUGIN_FOLDER.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        this.add(add);
        this.add(list);
    }


}
