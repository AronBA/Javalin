package dev.aronba.javalin.common.project.exception;

public class ProjectFolderNotFoundException extends RuntimeException{
    public ProjectFolderNotFoundException(String message) {
        super(message);
    }
}
