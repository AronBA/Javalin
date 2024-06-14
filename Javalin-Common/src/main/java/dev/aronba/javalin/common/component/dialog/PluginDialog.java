package dev.aronba.javalin.common.component.dialog;

import dev.aronba.javalin.common.dto.PluginLoadResult;
import dev.aronba.javalin.common.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PluginDialog extends JDialog {
    private static final Logger LOG = LoggerFactory.getLogger(PluginDialog.class);
    private JList<Plugin> pluginList;
    private PluginLoadResult loadedPluginsDto;
    private JPanel centerDetailView;
    private CardLayout centerDetailViewCardLayout;

    public PluginDialog(PluginLoadResult loadedPluginsDto) {
        super((Frame) null, true);
        this.pluginList = new JList<>();
        this.loadedPluginsDto = loadedPluginsDto;
        this.centerDetailView = new JPanel();
        this.centerDetailViewCardLayout = new CardLayout();
        this.centerDetailView.setLayout(centerDetailViewCardLayout);
        this.centerDetailView.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        this.setSize(600, 400);
        this.setTitle("Plugins");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));
        this.add(centerDetailView, BorderLayout.CENTER);
        setupSidebarList();
        this.setVisible(true);
    }


    private void setupSidebarList() {

        pluginList = new JList<>(new DefaultListModel<>());
        DefaultListModel<Plugin> model = (DefaultListModel<Plugin>) pluginList.getModel();
        for (Plugin plugin : loadedPluginsDto.getLoadedPlugins()) {
            model.addElement(plugin);
            JPanel panel = buildPanel(plugin);
            this.centerDetailView.add(panel);
            this.centerDetailViewCardLayout.addLayoutComponent(panel, plugin.getName());
        }


        pluginList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getName());
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            label.setOpaque(true);
            return label;
        });

        pluginList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    Plugin plugin = pluginList.getSelectedValue();
                    centerDetailViewCardLayout.show(centerDetailView, plugin.getName());
                }
            }
        });
        this.add(pluginList, BorderLayout.WEST);
    }

    private JPanel buildPanel(Plugin plugin) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(plugin.getName() + "(" + plugin.getVersion() + ")"));
        panel.add(new JLabel(plugin.getDescription()));
        return panel;
    }
}



