package dev.aronba.javalin.common.plugin;

import dev.aronba.javalin.common.dto.PluginLoadResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PluginManager {

    private final PluginLoader pluginLoader;
    private List<Plugin> registeredPlugins;
    private List<Plugin> loadedPlugins;
    private List<Plugin> enabledPlugins;
    private List<Plugin> disabledPlugins;
    private List<Plugin> unloadedPlugins;
    private List<Plugin> brokenPlugins;


    public PluginManager(final PluginLoader pluginLoader) {
        this.pluginLoader = pluginLoader;

        PluginLoadResult loadedPluginsDto = pluginLoader.load();
        this.loadedPlugins = loadedPluginsDto.getLoadedPlugins();
        this.brokenPlugins = loadedPluginsDto.getBrokenPlugins();

        this.registeredPlugins = new ArrayList<Plugin>();
        registeredPlugins.addAll(loadedPlugins);
        registeredPlugins.addAll(brokenPlugins);
    }
}
