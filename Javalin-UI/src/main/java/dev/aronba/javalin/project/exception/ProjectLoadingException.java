package dev.aronba.javalin.project.exception;

public class ProjectLoadingException extends RuntimeException {
    public ProjectLoadingException(String message) {
        super(message);
    }

    public ProjectLoadingException(Exception e) {
        super(e);
    }
}
