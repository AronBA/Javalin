package dev.aronba.javalin.component.texeditor;

import dev.aronba.javalin.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextEditorAutoSaver implements DocumentListener {

    private static final Logger LOG = LoggerFactory.getLogger(TextEditorAutoSaver.class);

    private final Timer autoSaveTimer;


    public TextEditorAutoSaver(TextEditorArea textEditorArea) {
        this.autoSaveTimer = new Timer(1000, e -> {
            FileUtils.saveStringToFile(textEditorArea.getCurrentlyOpenFile(), textEditorArea.getTextArea().getText());
            LOG.debug("Auto save complete.");
        });
        autoSaveTimer.setRepeats(false);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        resetAutoSaveTimer();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        resetAutoSaveTimer();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        resetAutoSaveTimer();
    }

    private void resetAutoSaveTimer() {
        if (autoSaveTimer.isRunning()) {
            autoSaveTimer.restart();
        } else {
            autoSaveTimer.start();
        }
    }
}
