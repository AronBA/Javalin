package dev.aronba.javalin.component.filetree;


import dev.aronba.javalin.common.component.ToolBarComponent;

import javax.swing.*;

public class FileTreeToolBarComponent extends ToolBarComponent {
    private final FileTree fileTree;

    public FileTreeToolBarComponent(FileTree fileTree) {
        super();
        this.fileTree = fileTree;

    }

    @Override
    public JPanel getContainer() {
        return fileTree;
    }

    @Override
    public String getContainerName() {
        return "FileTree";
    }
}
