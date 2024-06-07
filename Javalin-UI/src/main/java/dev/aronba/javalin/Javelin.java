package dev.aronba.javalin;


import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import dev.aronba.javalin.common.component.ToolBarComponent;
import dev.aronba.javalin.common.plugin.ComponentPlugin;
import dev.aronba.javalin.common.plugin.Plugin;
import dev.aronba.javalin.component.filetree.FileTree;
import dev.aronba.javalin.component.filetree.FileTreeToolBarComponent;
import dev.aronba.javalin.component.texeditor.TextEditorArea;
import dev.aronba.javalin.component.toolbar.ToolBar;
import dev.aronba.javalin.project.ProjectManager;
import dev.aronba.javalin.settings.SettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Javelin extends JFrame {

    private static final String APPLICATION_NAME = "Javelin";
    private static final Logger LOG = LoggerFactory.getLogger(Javelin.class);
    private final PluginLoader pluginManager;
    private final ProjectManager projectManager;
    private final SettingsManager settingsManager;

    public Javelin(PluginLoader pluginManager, ProjectManager projectManager, SettingsManager settingsManager) {
        this.pluginManager = pluginManager;
        this.projectManager = projectManager;
        this.settingsManager = settingsManager;

        this.setLayout(new BorderLayout());


        List<Plugin> plugins = pluginManager.load().getLoadedPlugins();


//        setExtendedState(JFrame.MAXIMIZED_BOTH);

        TextEditorArea textEditorArea = new TextEditorArea();

        List<ToolBarComponent> toolBarComponentList = new ArrayList<>();

        FileTree fileTree = new FileTree(this.projectManager.getCurrentProject().getRootFile());
        fileTree.addFileSelectionListener(textEditorArea::openFileInEditor);
        toolBarComponentList.add(new FileTreeToolBarComponent(fileTree));

        for (Plugin plugin : plugins) {
            if (plugin instanceof ComponentPlugin componentPlugin) {
                if (componentPlugin.getComponent() instanceof ToolBarComponent toolbarComponent) {
                    LOG.info("Loaded tool bar component: {} from {}", toolbarComponent.getContainerName(),plugin.getName());
                    toolBarComponentList.add(toolbarComponent);
                }
            }
        }


        ToolBar westToolBar = new ToolBar(toolBarComponentList);
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westToolBar, textEditorArea);
        this.add(jSplitPane, BorderLayout.CENTER);

        this.projectManager.saveLastProject(this.projectManager.getCurrentProject());
        this.setTitle(APPLICATION_NAME);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    public static void run(String... args) {
        LOG.info("{} starting...", APPLICATION_NAME);

        LOG.info("loading plugins...");
        PluginLoader plg = new PluginLoader(new File("C:\\Develop\\Folder\\Javalin\\Plugins"));

        LOG.info("loading settings...");
        SettingsManager stg = new SettingsManager();

        LOG.info("loading projects...");
        ProjectManager pjt = new ProjectManager();
        pjt.manage();

        LOG.info("application started up");
        FlatAtomOneDarkIJTheme.setup();
        SwingUtilities.invokeLater(() -> new Javelin(plg, pjt, stg));
    }

}

