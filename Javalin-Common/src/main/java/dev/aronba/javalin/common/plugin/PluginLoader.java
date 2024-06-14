package dev.aronba.javalin.common.plugin;

import dev.aronba.javalin.common.dto.PluginLoadResult;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Getter
public class PluginLoader {
    private final static Logger LOG = LoggerFactory.getLogger(PluginLoader.class);
    private final List<File> jarFiles;
    private final ClassLoader classLoader;
    private Optional<PluginLoadResult> currentlyLoadedPlugins = Optional.empty();
    private final Class<?>[] injectables;

    public PluginLoader(File pluginFolder, Class<?>... injectables) {
        this.injectables = injectables;
        this.jarFiles = new ArrayList<>();
        if (!pluginFolder.exists() || !pluginFolder.isDirectory()) {
            throw new IllegalArgumentException("Plugin folder does not exist");
        }

        if (pluginFolder.listFiles() == null || pluginFolder.listFiles().length == 0) {
            throw new IllegalArgumentException("Plugin folder does not contain any files");
        }

        LOG.debug("{} files found", pluginFolder.listFiles().length);
        for (File file : pluginFolder.listFiles()) {
            if (!file.getName().endsWith(".jar")) continue;
            try {
                LOG.debug("found jar file {}", file.getAbsolutePath());
                this.jarFiles.add(file);
            } catch (Exception e) {
                LOG.error("error adding jar file {}", file.getAbsolutePath(), e);
            }
        }

        URL[] urls = getUrlFromJars();
        this.classLoader = new PluginClassLoader(urls);
    }

    public PluginLoadResult load() {
        LOG.info("loading plugins");
        PluginLoadResult.PluginLoadResultBuilder builder = PluginLoadResult.builder();

        List<String> clasNames = getAllClassNames();

        LOG.debug("loading classes");
        List<Class<?>> klass = loadKlasses(clasNames);

        List<Plugin> plugins = loadPluginsFromKlass(klass);
        builder.loadedPlugins(plugins);

        this.currentlyLoadedPlugins = Optional.of(builder.build());
        return builder.build();
    }

    private List<Plugin> loadPluginsFromKlass(List<Class<?>> klass) {
        List<Plugin> plugins = new ArrayList<>();
        for (Class<?> klassClass : klass) {
            if (Plugin.class.isAssignableFrom(klassClass)) {
                try {
                    Plugin plugin = (Plugin) klassClass.getDeclaredConstructor().newInstance();
                    klassClass.getA







                    plugins.add(plugin);
                    LOG.debug("loaded plugin {}", plugin.getName());
                } catch (Exception e) {
                    LOG.error("error loading plugin {}", klassClass.getName(), e);
                }
            }
        }
        return plugins;
    }

    private List<Class<?>> loadKlasses(List<String> clasNames) {
        List<Class<?>> klass = new ArrayList<>();

        for (String clasName : clasNames) {
            try {
                klass.add(this.classLoader.loadClass(clasName));
            } catch (ClassNotFoundException e) {
                LOG.error("error loading class {}", clasName, e);
            }
        }
        return klass;
    }

    private URL[] getUrlFromJars() {
        URL[] urls = new URL[jarFiles.size()];
        for (int i = 0; i < urls.length; i++) {
            try {
                urls[i] = jarFiles.get(i).toURI().toURL();
            } catch (MalformedURLException e) {
                LOG.error("Malformed URL", e);
            }
        }
        LOG.debug("added the following URLs to the ClassPath: {}", Arrays.toString(urls));
        return urls;
    }

    private List<String> getAllClassNames() {
        HashSet<String> classNames = new HashSet<>();
        for (File file : jarFiles) {
            try {
                HashSet<String> className = getClassNamesFromJar(file);
                if (!classNames.addAll(className)) {
                    LOG.warn("duplicate class names detected in {} !", file.getAbsolutePath());
                }
                LOG.debug("Classnames found in {}: {}", file.getAbsolutePath(), className);
            } catch (Exception e) {
                LOG.error("error getting class names from jar file {}", file.getAbsolutePath(), e);
            }
        }
        LOG.info("Found the following class names {}", classNames);
        return classNames.stream().toList();
    }

    private HashSet<String> getClassNamesFromJar(File file) {
        HashSet<String> classNames = new HashSet<>();
        try (JarFile jarFile = new JarFile(file)) {

            Enumeration<JarEntry> jarEntries = jarFile.entries();

            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String jarEntryName = jarEntry.getName();

                if (jarEntryName.endsWith(".class")) {
                    String className = jarEntryName.replace("/", ".").replace(".class", "");
                    if (!classNames.add(className)) {
                        LOG.warn("Duplicate class name detected: {}", className);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return classNames;
    }

}

