package dev.aronba.javalin;

import dev.aronba.javalin.common.plugin.Plugin;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoadedPlugins {
    List<Plugin> loadedPlugins;
    List<Plugin> brokenPlugins;
}
