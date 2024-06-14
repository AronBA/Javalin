import dev.aronba.javalin.common.plugin.Plugin;
import dev.aronba.javalin.common.project.ProjectManager;


public class GitPlugin extends Plugin {

    @JavalinInjectable
    ProjectManager projectManager;

    GitPlugin() {
        this.setName("GitPlugin");
        this.setVersion("1.0.0");
        this.setDescription("A simple git plugin");
    }

    @Override
    public void onInit() {
        System.out.println("Hello World!");
    }

}
