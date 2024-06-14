package dev.aronba.javalin.common.plugin;


import dev.aronba.javalin.common.plugin.lifecycle.OnInit;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.UUID;

@Getter
public abstract class Plugin implements OnInit {

    private final UUID uuid;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private String version;

    @Setter
    private String author;

    @Setter
    private Icon icon;

    protected Plugin() {
        this.uuid = UUID.randomUUID();
        this.name = getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "Plugin [uuid=" + uuid + ", name=" + name + ", version=" + version + "]";
    }
}
