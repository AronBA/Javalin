import dev.aronba.javalin.common.component.ToolBarComponent;
import dev.aronba.javalin.common.project.ProjectManager;
import org.eclipse.jgit.api.Git;

import javax.swing.*;
import java.awt.*;

public class GitPluginToolbarContainer extends ToolBarComponent {

    ProjectManager projectManager;
    Git git;

    GitPluginToolbarContainer(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public JPanel getContainer() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        if (git == null){
            JLabel jLabel = new JLabel("No Repository found");
            JButton create = new JButton("create Repository");
            create.addActionListener(a -> createRepo());
            JButton clone = new JButton("clone Repository");
            clone.addActionListener(a -> cloneRepo());

            panel.add(create, BorderLayout.WEST);
            panel.add(clone, BorderLayout.EAST);
            panel.add(jLabel, BorderLayout.NORTH);
        } else {

            panel.add(new JLabel("Hello World!"), BorderLayout.CENTER);
        }





        return panel;
    }

    private void cloneRepo() {

    }

    @Override
    public String getContainerName() {
        return "GitPlugin";
    }

    private void createRepo() {

        try {
            this.git = Git.init().setDirectory(projectManager.getCurrentProject().getRootFile().getAbsoluteFile()).call();
        } catch (Exception e) {

        }

    }
}
