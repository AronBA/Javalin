import dev.aronba.javalin.common.component.JavalinComponent;
import dev.aronba.javalin.common.plugin.ComponentPlugin;

public class MySidebarComponent implements ComponentPlugin {
    JavalinComponent component;
    public MySidebarComponent() {
        this.component = new Component();
    }

    @Override
    public String getName() {
        return "Notes";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return "This is a Plugin which allows you to take notes in the sidebar!";
    }

    @Override
    public JavalinComponent getComponent() {
        return this.component;
    }
}
