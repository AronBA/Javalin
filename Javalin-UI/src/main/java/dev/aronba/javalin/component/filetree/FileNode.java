package dev.aronba.javalin.component.filetree;

import dev.aronba.javalin.common.util.FileUtils;
import lombok.Getter;

import java.io.File;

 class  FileNode {
    @Getter
    private File file;
    private final String displayName;

    public FileNode(File file) {
        this.file = file;
        this.displayName = file.isDirectory() ? file.getName() : FileUtils.getFileNameWithoutExtension(file.getName());
    }

    @Override
    public String toString() {
        return displayName;
    }
}
