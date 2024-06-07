package dev.aronba.javalin.project;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import dev.aronba.javalin.component.dialog.ProjectChooserDialog;
import dev.aronba.javalin.project.exception.ProjectFolderNotFoundException;
import dev.aronba.javalin.project.exception.ProjectLoadingException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.prefs.Preferences;

public class ProjectManager {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectManager.class);
    private static final String PROJECT_FOLDER = "C:\\Develop\\Folder\\Javalin\\Javalin-Examples\\JavalinProjects"; // TODO make this an argument which is set on installation
    private static final String KEY_LAST_PROJECT = "last_project";

    @Getter private Project currentProject;
    @Getter private List<Project> projects;

    private final Preferences preferences;

    public ProjectManager() {
        this.preferences = Preferences.userNodeForPackage(ProjectManager.class);
    }

    public void manage() {

        Optional<Project> lastProject = loadLastProject();

        if (lastProject.isPresent()) {
            LOG.info("Loaded last project");
            this.currentProject = lastProject.get();
            return;
        }

        LOG.info("No last project was found, starting the ProjectChooser");
        this.projects = loadProjects();

        Consumer<Project> choosenProjectConsumer = project -> this.currentProject = project;

        FlatAtomOneDarkIJTheme.setup();
        new ProjectChooserDialog(projects, choosenProjectConsumer);

    }

    public void saveLastProject(Project project) {
        LOG.info("Saving last project: {}", project.getRootUrl());
        String projectRootUrl = project.getRootUrl();
        this.preferences.put(KEY_LAST_PROJECT, projectRootUrl);
    }

    public void clearCurrentProject() {
        LOG.info("Removed last project: {}", this.currentProject.getRootUrl());
        this.currentProject = null;
        this.preferences.remove(KEY_LAST_PROJECT);
    }

    private List<Project> loadProjects() {

        File file = new File(PROJECT_FOLDER);
        List<Project> projects = new ArrayList<>();

        if (!file.exists() || !file.isDirectory()) throw new ProjectFolderNotFoundException("No Project Folder Found at: " + file.getAbsolutePath());

        File[] files = file.listFiles();

        if (files == null) return projects;

        for (File f : files) {
            if (f.isDirectory()) {
                Project project = Project.loadFromPath(f.getAbsolutePath());
                projects.add(project);
            }
        }
        return projects;
    }

    private Optional<Project> loadLastProject() {
        LOG.info("Attempting to load the last project");
        String lastProjectPath = preferences.get(KEY_LAST_PROJECT, null);
        if (lastProjectPath == null) {
            LOG.info("No last project found");
            return Optional.empty();
        }
        try{
            Project project = Project.loadFromPath(lastProjectPath);
            LOG.info("Successfully loaded last project");
            return Optional.of(project);
        } catch (ProjectLoadingException e){
            LOG.warn("Failed to load last project", e);
            return Optional.empty();
        }
    }

}
