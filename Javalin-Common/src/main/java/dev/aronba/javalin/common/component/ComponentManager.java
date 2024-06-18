package dev.aronba.javalin.common.component;

import dev.aronba.javalin.common.exception.NotImplementedException;
import dev.aronba.javalin.common.plugin.SideBarComponent;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ComponentManager {
    private final List<SideBarComponent> sideBarComponents = new ArrayList<>();


    public void addSideBarComponent(SideBarComponent sideBarComponent) {
        this.sideBarComponents.add(sideBarComponent);
    }

    public void addMenuBarComponent(JMenuBar menuBarComponentj) {
        throw new NotImplementedException();
    }
}
