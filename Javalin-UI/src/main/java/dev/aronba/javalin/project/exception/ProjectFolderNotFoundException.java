package dev.aronba.javalin.project.exception;

public class ProjectFolderNotFoundException extends RuntimeException{
    public ProjectFolderNotFoundException(String message) {
        super(message);
    }
}
