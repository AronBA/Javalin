package dev.aronba.javalin.common.component;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public abstract class ToolBarComponent implements JavalinComponent {
    private Logger LOG = LoggerFactory.getLogger(ToolBarComponent.class);
    private CardLayout cardLayout;
    private JButton toolbarButton;
    private JPanel container;
    private JPanel expandablePanel;


    protected ToolBarComponent() {
        initialize(this.getContainerName(), null);
    }

    protected ToolBarComponent(String containerName) {
        initialize(containerName, null);
    }

    protected ToolBarComponent(Icon icon) {
        initialize(null, icon);
    }

    protected ToolBarComponent(String containerName, Icon icon) {
        initialize(containerName, icon);
    }

    private void initialize(String containerName, Icon icon) {
        this.setToolbarButton(new JButton(containerName, icon));

        this.getToolbarButton().addActionListener(e -> this.getCardLayout().show(this.getExpandablePanel(), containerName == null ? getContainerName() : containerName));
    }

    public abstract JPanel getContainer();

    public abstract String getContainerName();
}
