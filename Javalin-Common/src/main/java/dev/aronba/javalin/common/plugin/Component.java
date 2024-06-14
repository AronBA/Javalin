package dev.aronba.javalin.common.plugin;

import dev.aronba.javalin.common.component.JavalinComponent;
import dev.aronba.javalin.common.project.ProjectManager;
import lombok.Getter;

public abstract class Component implements JavalinComponent {

    private ProjectManager projectManager;
    Component(){}

    Component(ProjectManager projectManager){
        this.projectManager = projectManager;
    }


}
