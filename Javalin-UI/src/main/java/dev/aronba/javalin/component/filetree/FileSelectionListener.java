package dev.aronba.javalin.component.filetree;


import java.io.File;

@FunctionalInterface
public interface FileSelectionListener {
    void onFileSelected(File file);
}
