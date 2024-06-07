package dev.aronba.javalin.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;

@Getter
@Data
public class ProjectConfiguration {

    private boolean valid;
    private String name;
    private File rootFile;
    private File mainClass;




    static class Loader {

        public static ProjectConfiguration loadFromProjectRoot(String projectRoot) {
            try {

                String pathToConfigFolder = projectRoot + "\\.javelin";

                File configFolder = new File(pathToConfigFolder);

                if (!configFolder.exists() || !configFolder.isDirectory()) {
                    throw new FileNotFoundException("No Config Folder" + projectRoot + "\\.javelin");
                }

                File configFile = new File(pathToConfigFolder + "\\workspace.json");

                if (!configFile.exists() || !configFile.isFile()) {
                    throw new FileNotFoundException("No Config File in Config Folder");
                }

                ObjectMapper objectMapper = new ObjectMapper();

                return objectMapper.readValue(configFile, ProjectConfiguration.class);
            } catch (Exception e) {
                return new ProjectConfiguration();
            }
        }
    }
}
