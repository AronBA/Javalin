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
    private boolean expanded = true;

    protected ToolBarComponent() {

        this.setContainer(getContainer());
        this.setToolbarButton(new JButton(getContainerName()));
        this.getToolbarButton().addActionListener(e -> {
            LOG.info("showing on: " +this.expandablePanel +" "+ this.getContainerName());
            if (this.isExpanded()) {
                this.setExpanded(false);
                this.expandablePanel.setVisible(false);
            } else {
                this.cardLayout.show(this.expandablePanel, getContainerName());
                this.setExpanded(true);
                this.getExpandablePanel().setVisible(true);
            }
        });
    }

    public abstract JPanel getContainer();

    public abstract String getContainerName();
}
