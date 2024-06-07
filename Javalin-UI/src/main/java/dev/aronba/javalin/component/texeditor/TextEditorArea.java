package dev.aronba.javalin.component.texeditor;

import lombok.Getter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class TextEditorArea extends JPanel {


    private File currentlyOpenFile;
    private RSyntaxTextArea textArea;

    public TextEditorArea() {
        this.setLayout(new BorderLayout());

        this.textArea = new RSyntaxTextArea();
        textArea.setCodeFoldingEnabled(true);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        RSyntaxTextArea.setTemplatesEnabled(true);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        this.add(scrollPane, BorderLayout.CENTER);

        String path = "/style.xml";
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(path));
            theme.apply(textArea);
        } catch (IOException e) {
            //todo-> stupid ass log logger.warn("could not load theme from" + path);
        }
        this.textArea.getDocument().addDocumentListener(new TextEditorAutoSaver(this));
    }


    public void openFileInEditor(File file) {
        try {
            String fileConent = Files.readString(Path.of(file.getAbsolutePath()));
            this.textArea.setText(fileConent);
            this.currentlyOpenFile = file;
        } catch (Exception e) {
        }
    }
}
