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
        this(null, null);
    }
    protected ToolBarComponent(String containerName) {
        this(containerName, null);
    }

    protected ToolBarComponent(Icon icon) {
        this(null, icon);
    }

    protected ToolBarComponent(String containerName, Icon icon) {
        if (containerName == null) {
            containerName = this.getContainerName();
        }
        String finalContainerName = containerName;

        this.setToolbarButton(new JButton(finalContainerName, icon));
        this.getToolbarButton().addActionListener(e -> {
            this.getCardLayout().show(this.getExpandablePanel(), finalContainerName);
            LOG.info("Clicked toolbar button switched to " + finalContainerName);
        });
    }


    public abstract JPanel getContainer();

    public abstract String getContainerName();
}
