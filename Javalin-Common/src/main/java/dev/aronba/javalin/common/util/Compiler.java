package dev.aronba.javalin.common.util;


import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Compiler {
    private static final Logger LOG = LoggerFactory.getLogger(Compiler.class);

    public static void compileProject(File projectDir) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        List<String> command = new ArrayList<>();
        command.add("javac");
        command.add("-d");
        command.add("out");
        command.addAll(findAllJavaFiles(projectDir));

        processBuilder.command(command);
        processBuilder.directory(projectDir);
        Process process = processBuilder.start();
        InputStream inputStream = process.getErrorStream();
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
            System.err.println("Compilation failed.");
        } else {
            System.out.println("Compilation successful.");
        }
    }

    private static List<String> findAllJavaFiles(File dir) {
        List<String> files = new ArrayList<>();
        findAllJavaFilesRecursive(dir, files);
        return files;
    }


    private static void findAllJavaFilesRecursive(File dir, List<String> files) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                findAllJavaFilesRecursive(file, files);
            } else if (file.getName().endsWith(".java")) {
                files.add(file.getPath());
            }
        }
    }
}
