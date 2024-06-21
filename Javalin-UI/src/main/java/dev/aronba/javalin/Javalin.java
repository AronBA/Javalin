package dev.aronba.javalin;


import com.formdev.flatlaf.intellijthemes.FlatVuesionIJTheme;
import dev.aronba.javalin.common.component.ComponentManager;
import dev.aronba.javalin.common.component.ToolBarComponent;
import dev.aronba.javalin.common.plugin.PluginDependencyManager;
import dev.aronba.javalin.common.plugin.PluginLoader;
import dev.aronba.javalin.common.plugin.PluginManager;
import dev.aronba.javalin.common.project.ProjectManager;
import dev.aronba.javalin.component.filetree.FileTree;
import dev.aronba.javalin.component.filetree.FileTreeToolBarComponent;
import dev.aronba.javalin.component.menubar.MenuBar;
import dev.aronba.javalin.component.texeditor.TextEditorArea;
import dev.aronba.javalin.component.toolbar.ToolBar;
import dev.aronba.javalin.settings.SettingsManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Javalin extends JFrame {

    private static final String APPLICATION_NAME = "Javelin";
    private static final Logger LOG = LoggerFactory.getLogger(Javalin.class);
    private final PluginManager pluginManager;
    private final ProjectManager projectManager;
    private final SettingsManager settingsManager;
    private final ComponentManager componentManager;
    private final List<ToolBarComponent> toolBarComponentList = new ArrayList<>();

    public Javalin(PluginManager pluginManager, ProjectManager projectManager, SettingsManager settingsManager, ComponentManager componentManager) {
        this.pluginManager = pluginManager;
        this.projectManager = projectManager;
        this.settingsManager = settingsManager;
        this.componentManager = componentManager;

        this.setLayout(new BorderLayout());

//        setExtendedState(JFrame.MAXIMIZED_BOTH);

        TextEditorArea textEditorArea = new TextEditorArea();

        FileTree fileTree = new FileTree(this.projectManager.getCurrentProject().getRootFile());
        fileTree.addFileSelectionListener(textEditorArea::openFileInEditor);

        toolBarComponentList.add(new FileTreeToolBarComponent(fileTree));
        loadToolbarFromPlugins();
        ToolBar westToolBar = new ToolBar(toolBarComponentList);
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westToolBar, textEditorArea);

        this.add(jSplitPane, BorderLayout.CENTER);
        this.setJMenuBar(new MenuBar(this));
        this.projectManager.saveLastProject(this.projectManager.getCurrentProject());
        this.setTitle(APPLICATION_NAME);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void run(String... args) {
        LOG.info("{} starting...", APPLICATION_NAME);

        LOG.info("loading projects...");
        ProjectManager pjt = new ProjectManager();
        pjt.manage();

        LOG.info("loading component manager...");
        ComponentManager cm = new ComponentManager();

        LOG.info("loading plugins...");
        PluginDependencyManager pluginDependencyManager = new PluginDependencyManager(pjt, cm);

        PluginLoader plg = new PluginLoader(new File("C:\\Develop\\Folder\\Javalin\\Plugins"), pluginDependencyManager);
        PluginManager plm = new PluginManager(plg);
        plm.initialize();

        LOG.info("loading settings...");
        SettingsManager stg = new SettingsManager();

        setTheme();
        LOG.info("application started up");
        SwingUtilities.invokeLater(() -> new Javalin(plm, pjt, stg, cm));
    }

    public static void setTheme() {
//       FlatAtomOneDarkIJTheme.setup();
//        FlatGitHubDarkIJTheme.setup();
//        FlatXcodeDarkIJTheme.setup();
        FlatVuesionIJTheme.setup();
    }

    private void loadToolbarFromPlugins() {
        List<ToolBarComponent> toolBarComponents = this.componentManager.getToolBarComponents();
        toolBarComponentList.addAll(toolBarComponents);
    }


}

