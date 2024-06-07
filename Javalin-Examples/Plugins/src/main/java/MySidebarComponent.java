import dev.aronba.javalin.common.component.JavalinComponent;
import dev.aronba.javalin.common.plugin.ComponentPlugin;

public class MySidebarComponent implements ComponentPlugin {
    JavalinComponent component;
    public MySidebarComponent() {
        this.component = new Component();
    }

    @Override
    public String getName() {
        return "MyFirstToolBarComponentPlugin";
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public JavalinComponent getComponent() {
        return this.component;
    }
}
