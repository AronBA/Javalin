package dev.aronba.javalin.common.project;

import dev.aronba.javalin.common.project.exception.ProjectLoadingException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Getter
@Setter
public class Project {

    private String mainClassName;
    private File mainClassFile;
    private String readMeLocation;
    private static final Logger LOG = LoggerFactory.getLogger(Project.class);
    private String name;
    private String rootUrl;
    private final File rootFile;
    private final ProjectConfiguration projectConfiguration;

    public Project(File file, ProjectConfiguration projectConfiguration) {
        this.name = file.getName();
        this.rootFile = file;
        this.rootUrl = file.getAbsolutePath();
        this.projectConfiguration = projectConfiguration;
    }

    public static Project loadFromPath(String absolutPath) {
        try {
            LOG.info("Loading project from {}", absolutPath);
            File file = new File(absolutPath);

            if (!file.exists()) {
                throw new ProjectLoadingException("Project file not found: " + absolutPath);
            }

            if (!file.isDirectory()) {
                throw new ProjectLoadingException("Project file not found: " + absolutPath);
            }

            ProjectConfiguration projectConfiguration = ProjectConfiguration.Loader.loadFromProjectRoot(absolutPath);

            if (projectConfiguration.isValid()) {
                throw new ProjectLoadingException("Invalid project configuration: " + absolutPath);
            }

            return new Project(file,projectConfiguration);
        } catch (Exception e) {
            LOG.error("Error loading project from {}", absolutPath, e);
            throw new ProjectLoadingException(e);
        }
    }


    private void findMain(){

    }
    private void findReadme(){

    }

}
