import dev.aronba.javalin.common.component.ComponentManager;
import dev.aronba.javalin.common.plugin.JavalinInjectable;
import dev.aronba.javalin.common.plugin.Plugin;
import dev.aronba.javalin.common.plugin.SideBarComponent;
import dev.aronba.javalin.common.project.ProjectManager;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;


@RequiredArgsConstructor
public class GitPlugin extends Plugin implements SideBarComponent {

    @JavalinInjectable
    private final ProjectManager projectManager;

    @JavalinInjectable
    private final ComponentManager componentManager;


    @Override
    public void onInit() {
        this.setName("GitPlugin");
        this.setVersion("1.0.0");
        this.setDescription("A simple git plugin");
        this.componentManager.addSideBarComponent(this);
        System.out.println("Hello World!");

    }

    private void createRepo() {
        System.out.println("Creating a new repository");
    }

    @Override
    public JPanel getSideBarComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton("create Repository");
        button.addActionListener(a -> createRepo());

        panel.add(button, BorderLayout.EAST);
        panel.add(new JLabel("Hello World!"), BorderLayout.WEST);
        return panel;
    }
}
