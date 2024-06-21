package dev.aronba.javalin.common.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectRunner {
    Project project;

    ProjectRunner(Project project) {
        this.project = project;

        if (project.getMainClassName() == null || project.getMainClassName().isEmpty() || project.getMainClassName().isBlank()) {
            throw new IllegalArgumentException("Main class name cannot be empty");
        }
        if (project.getMainClassFile() == null
                || !project.getMainClassFile().exists()
                || !project.getMainClassFile().isFile()
        ) {
            throw new IllegalArgumentException("Main class file cannot be empty");
        }
        //should do something
    }

//    public void run() {
//        List<String> command = new ArrayList<>();
//        command.add("javac");
//        command.add("-d");
//        command.add(binDir);
//
//        // Recursively add all Java source files in the src directory
//        addSourceFiles(new File(project.getRootFile()), command);
//
//        ProcessBuilder processBuilder = new ProcessBuilder(command);
//        processBuilder.redirectErrorStream(true);
//
//        Process process = processBuilder.start();
//        printProcessOutput(process);
//
//        int exitCode = process.waitFor();
//        if (exitCode != 0) {
//            throw new RuntimeException("Compilation failed.");
//        }
//
//    }
//

    private void compile() {

    }


}
