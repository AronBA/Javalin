package dev.aronba.javalin.component.toolbar;

import dev.aronba.javalin.common.component.ToolBarComponent;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ToolBar extends JPanel {
    private static final Logger LOG = LoggerFactory.getLogger(ToolBar.class);

    private JPanel expandablePanel;
    @Getter
    private CardLayout cardLayout;
    private JToolBar toolBar;


    public ToolBar(List<ToolBarComponent> components) {
        this.setLayout(new BorderLayout());

        this.toolBar = new JToolBar(JToolBar.VERTICAL);
        this.cardLayout = new CardLayout();

        LOG.info("Found toolbar components: {}", components.size());

        expandablePanel = new JPanel();
        expandablePanel.setLayout(cardLayout);
        expandablePanel.setVisible(true);

        this.add(expandablePanel, BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.WEST);


        //Not sure if legit but basically injects on runtime the necessary fields for the ToolbarComponents
        for (ToolBarComponent component : components) {
            component.setCardLayout(cardLayout);
            component.setExpandablePanel(expandablePanel);

            this.expandablePanel.add(component.getContainer(), component.getContainerName());
            this.toolBar.add(component.getToolbarButton());
        }

    }


}
