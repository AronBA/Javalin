package plugin;

import dev.aronba.javalin.common.dto.PluginLoadResult;
import dev.aronba.javalin.common.plugin.Plugin;
import dev.aronba.javalin.common.plugin.PluginLoader;
import dev.aronba.javalin.common.plugin.PluginManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PluginManagerTest {

    PluginLoader pluginLoader = mock(PluginLoader.class);

    Plugin mockedPlugin = mock(Plugin.class);


    @Test
    void shouldCallPluginLoader() {
        PluginLoadResult result = PluginLoadResult.builder().loadedPlugins(List.of(mockedPlugin)).brokenPlugins(List.of(mockedPlugin)).build();
        when(pluginLoader.load()).thenReturn(result);
        PluginManager pluginManager = new PluginManager(pluginLoader);

        assertEquals(1, pluginManager.getLoadedPlugins().size());
    }
}
