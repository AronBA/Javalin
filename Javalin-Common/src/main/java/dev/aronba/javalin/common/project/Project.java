package dev.aronba.javalin.common.project;

import dev.aronba.javalin.common.project.exception.ProjectLoadingException;
import dev.aronba.javalin.common.util.MainMethodFinder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Getter
@Setter
public class Project {

    private static final Logger LOG = LoggerFactory.getLogger(Project.class);

    private final File mainClassFile;
    private final File readMeFile;
    private final File rootFile;
    private final ProjectConfiguration projectConfiguration;
    private String name;
    private String rootUrl;

    public Project(File file, ProjectConfiguration projectConfiguration) {
        this.name = file.getName();
        this.rootFile = file;
        this.rootUrl = file.getAbsolutePath();
        this.projectConfiguration = projectConfiguration;

        this.mainClassFile = findMain();

        this.readMeFile = findReadme();


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

            return new Project(file, projectConfiguration);
        } catch (Exception e) {
            LOG.error("Error loading project from {}", absolutPath, e);
            throw new ProjectLoadingException(e);
        }
    }


    private File findFileInProjectByName(String fileName) {
        return new File(rootFile, fileName);
    }

    private File findMain() {
        try {
            List<String> mainMethods = MainMethodFinder.findMainMethods(this.rootFile);
            LOG.info("found main methods: {}", mainMethods);
            return new File(rootFile, mainMethods.get(0));
        } catch (Exception e) {
            LOG.error("Error finding main method", e);
            return null;
        }
    }

    private File findReadme() {
        return findFileInProjectByName("");
    }

}
