package dev.aronba.javalin.common.component;

import dev.aronba.javalin.common.exception.NotImplementedException;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ComponentManager {
    private final List<ToolBarComponent> toolBarComponents = new ArrayList<>();


    public void addSideBarComponent(ToolBarComponent toolbarComponent) {
        this.toolBarComponents.add(toolbarComponent);
    }

    public void addMenuBarComponent(JMenuBar menuBarComponentj) {
        throw new NotImplementedException();
    }
}
