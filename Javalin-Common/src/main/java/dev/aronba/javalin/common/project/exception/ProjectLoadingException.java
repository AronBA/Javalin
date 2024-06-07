package dev.aronba.javalin.common.project.exception;

public class ProjectLoadingException extends RuntimeException {
    public ProjectLoadingException(String message) {
        super(message);
    }

    public ProjectLoadingException(Exception e) {
        super(e);
    }
}
