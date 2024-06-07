package dev.aronba.javalin.common.plugin;

import dev.aronba.javalin.common.component.JavalinComponent;

public interface ComponentPlugin extends Plugin {
    JavalinComponent getComponent();
}
