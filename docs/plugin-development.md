# Plugin Development
Developing your own plugin to extend Javalins functionality is pretty easy. In order to this you need to understand some core concepts of the Plugin system

## Plugin Class
Every Plugin needs to inherit from the Plugin Class in order to be loaded by the PluginLoader.
The onInit Function is called **AFTER** the Plugin is loaded and **AFTER** the constructor of the class. There are some fields on the Plugin class which should be set on init:
- Name
- Version
- Description  

All other fields are completely Optional.

```java
import dev.aronba.javalin.common.plugin.Plugin;


public class ExamplePlugin extends Plugin {
    @Override
    public void onInit() {
        this.setName("MyExamplePlugin");
        this.setVersion("1.0.0");
        this.setDescription("A simple plugin");
        System.out.println("Hello World, from my Plugin");
    }
}
```
## Dependency Injection of Managers
Most tasks are managed by a "manager" those classes usually manage some sort of functionality in the IDE. Examples are:
- PluginManager : Manages the Plugins, allows you to access the Plugins
- ComponentManager : Manages the UI Component, allows to add new UI components
- ProjectManager : Manages the Projects, allows you to access projects

Those managers provide APIs to help you develop your plugins. You can access these managers over Dependency Injection. You need to Annotate the field of the Dependencies with the `@JavalinInjectable` Annotation in order for the DependencyManager to find the requested dependency. 

Note: Lombok is **NOT** required in order to perform the dependency injection but helps you to keep the code clean. You can also use a normal constructor injection.

```java
import dev.aronba.javalin.common.plugin.JavalinInjectable;
import dev.aronba.javalin.common.plugin.Plugin;
import dev.aronba.javalin.common.project.ProjectManager;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class NoUsePlugin extends Plugin {

    @JavalinInjectable
    private final ProjectManager projectManager;

    @Override
    public void onInit() {
        this.setName("NoUsePlugin");
        this.setVersion("1.0.0");
        this.setDescription("Very useless Plugin");

        Project currentSelectedProject = this.projectManager.getCurrentProject();
    }
}
```



