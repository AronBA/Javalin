package dev.aronba.javalin.component.filetree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

public class FileTreeCellRenderer extends DefaultTreeCellRenderer {
    private Icon folderIcon;
    private Icon fileIcon;

    public FileTreeCellRenderer() {
        folderIcon = UIManager.getIcon("FileView.directoryIcon");
        fileIcon = UIManager.getIcon("FileView.fileIcon");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        FileNode fileNode = (FileNode) node.getUserObject();
        File file = fileNode.getFile();

        if (file.isDirectory()) {
            setIcon(folderIcon);
        } else {
            setIcon(fileIcon);
        }
        return this;
    }
}
