package dev.aronba.javalin.component.menubar;

import dev.aronba.javalin.common.plugin.PluginLoader;
import dev.aronba.javalin.common.component.dialog.PluginDialog;

import javax.swing.*;

public class PluginMenuItem extends JMenu {

    PluginMenuItem(PluginLoader pluginLoader){
    super("Plugin");


    JMenuItem menuItem = new JMenuItem("Manage");
    menuItem.addActionListener(e -> {
        if (pluginLoader.getCurrentlyLoadedPlugins().isEmpty()){
            return;
        }
        new PluginDialog(pluginLoader.getCurrentlyLoadedPlugins().get());

    });

        this.add(menuItem);
    }




}
