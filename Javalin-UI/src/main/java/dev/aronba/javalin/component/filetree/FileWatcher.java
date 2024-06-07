// File: src/dev/aronba/javelin/component/filetree/FileWatcher.java
package dev.aronba.javalin.component.filetree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

class FileWatcher implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(FileWatcher.class);

    private final Path rootPath;
    private final FileTree filetree;
    private final WatchService watchService;

    private FileWatcher(File root, FileTree filetree) throws IOException {

        this.rootPath = root.toPath();
        this.filetree = filetree;
        this.watchService = FileSystems.getDefault().newWatchService();
        registerAll(rootPath);

    }

    public static FileWatcher initialize(File root, FileTree filetree) {
        try {
            LOG.info("Initializing FileWatcher");
            return new FileWatcher(root, filetree);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    private void registerAll(final Path start) throws IOException {
        start.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
    }


    @Override
    public void run() {
        LOG.info("Starting file watcher on: {}", rootPath);
        try {
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                   this.filetree.refreshTreeModel();
                }
                key.reset();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }
}
