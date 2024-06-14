package dev.aronba.javalin.common.plugin;

import java.net.URL;
import java.net.URLClassLoader;

class PluginClassLoader extends URLClassLoader {

    protected PluginClassLoader(URL[] urls) {
        super(urls);
    }
}
