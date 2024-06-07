import dev.aronba.javalin.common.component.ToolBarComponent;

import javax.swing.*;
import java.awt.*;

public class Component extends ToolBarComponent {
    private final JPanel panel;

    public Component() {
        super();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        jPanel.add(new JTextArea("Write your notes here!"), BorderLayout.CENTER);

        this.panel = jPanel;
    }

    @Override
    public JPanel getContainer() {
        return panel;
    }

    @Override
    public String getContainerName() {

        return "LoadedByPlugin";
    }
}
