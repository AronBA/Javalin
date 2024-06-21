import dev.aronba.javalin.common.component.ComponentManager;
import dev.aronba.javalin.common.component.ToolBarComponent;
import dev.aronba.javalin.common.plugin.JavalinInjectable;
import dev.aronba.javalin.common.plugin.Plugin;
import dev.aronba.javalin.common.project.ProjectManager;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class GitPlugin extends Plugin {

    @JavalinInjectable
    private final ProjectManager projectManager;

    @JavalinInjectable
    private final ComponentManager componentManager;

    private ToolBarComponent toolBarComponent;

    @Override
    public void onInit() {
        this.setName("GitPlugin");
        this.setVersion("1.0.0");
        this.setDescription("A simple git plugin");



        this.toolBarComponent = new GitPluginToolbarContainer(projectManager);
        this.componentManager.addSideBarComponent(this.toolBarComponent);
        System.out.println("Hello World! from GitPlugin");

    }
}
