package dev.aronba.javalin.common.plugin;

import dev.aronba.javalin.common.project.ProjectManager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PluginDependencyManager {

    List<Object> managedDependencies = new ArrayList<>();

    public PluginDependencyManager(Object... dependencies) {
        this.managedDependencies.addAll(Arrays.asList(dependencies));
    }
    public PluginDependencyManager(List<Object> dependencies) {
        this.managedDependencies.addAll(dependencies);
    }

    public void addDependency(Object dependency) {
        this.managedDependencies.add(dependency);
    }

    public Plugin injectDependencies(Constructor<?> constructor, List<Class<?>> dependencies) {
        try {
            List<Object> filteredObjects = managedDependencies.stream()
                    .filter(obj -> dependencies.stream().anyMatch(clazz -> clazz.isInstance(obj)))
                    .toList();
            return (Plugin) constructor.newInstance(filteredObjects.toArray());
        } catch (Exception e) {
            return null;
        }
    }
}
