package dev.aronba.javalin.common.project;

import dev.aronba.javalin.common.util.MainMethodFinder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void runMainClass(String mainClassName, File projectRoot) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-cp");
        command.add("out");
        command.add(mainClassName);
        processBuilder.command(command);
        processBuilder.directory(projectRoot);

        Process process = processBuilder.start();
        InputStream inputStream =  process.getErrorStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Execution failed.");
        } else {
            System.out.println("Execution successful.");
        }
    }

    public static void run(Project project) throws IOException, InterruptedException {
        List<String> mainClasses = MainMethodFinder.findMainMethods(project.getRootFile());
        if (!mainClasses.isEmpty()) {
            runMainClass(mainClasses.get(0),project.getRootFile());
        } else {
            System.out.println("No main method found.");
        }
    }
}
