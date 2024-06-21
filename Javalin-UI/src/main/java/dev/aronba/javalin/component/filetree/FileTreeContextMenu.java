package dev.aronba.javalin.component.filetree;


import dev.aronba.javalin.common.util.FileUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class FileTreeContextMenu extends JPopupMenu {
    private final FileTree fileTree;

    FileTreeContextMenu(FileTree fileTree) {
        this.fileTree = fileTree;
        addAddItem();
        addRenameItem();
        addDeleteItem();
    }

    private void addRenameItem() {
        JMenuItem menuItem = new JMenuItem("Rename File");
        menuItem.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getTree().getLastSelectedPathComponent();
            if (selectedNode.getUserObject() instanceof FileNode fileNode) {
                String filename = JOptionPane.showInputDialog(null, "rename to:");
                FileUtils.renameFile(fileNode.getFile(), filename);
            }
        });
        this.add(menuItem);
    }

    private void addAddItem() {
        JMenuItem menuItem = new JMenuItem("New File");
        menuItem.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getTree().getLastSelectedPathComponent();
            if (selectedNode.getUserObject() instanceof FileNode fileNode) {
                String filename = JOptionPane.showInputDialog(null, "Filename");
                String path = (fileNode.getFile().isDirectory()) ? fileNode.getFile().getAbsolutePath() : fileNode.getFile().getParent();
                path = path + File.separator + filename;
                FileUtils.createNewEmptyFile(path);
            }
        });
        this.add(menuItem);
    }

    private void addDeleteItem() {
        JMenuItem menuItem = new JMenuItem("Delete");
        menuItem.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getTree().getLastSelectedPathComponent();
            if (selectedNode.getUserObject() instanceof FileNode fileNode) {
                FileUtils.deleteFile(fileNode.getFile());
            }
        });
        this.add(menuItem);
    }

}
