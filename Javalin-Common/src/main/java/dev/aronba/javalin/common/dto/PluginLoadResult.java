package dev.aronba.javalin.common.dto;

import dev.aronba.javalin.common.plugin.Plugin;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PluginLoadResult {

    List<Plugin> loadedPlugins;
    List<Plugin> brokenPlugins;
}
