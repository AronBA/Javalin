package dev.aronba.javalin.common.plugin;


public interface Plugin {
    String getName();

    String getVersion();

    String getDescription();

    default String getAuthor() {
        return null;
    }
}
