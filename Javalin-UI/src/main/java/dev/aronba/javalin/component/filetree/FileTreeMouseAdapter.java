package dev.aronba.javalin.component.filetree;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileTreeMouseAdapter extends MouseAdapter {

    private final FileTreeContextMenu fileTreeContextMenu;
    private final FileTree tree;

    FileTreeMouseAdapter(FileTreeContextMenu fileTreeContextMenu,FileTree tree) {
        this.fileTreeContextMenu = fileTreeContextMenu;
        this.tree = tree;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        TreePath path = tree.getTree().getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            tree.getTree().setSelectionPath(path);
            fileTreeContextMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
