package dev.aronba.javalin.component.filetree;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.io.File;

public class FileTree extends JPanel {

    private final Logger LOG = LoggerFactory.getLogger(FileTree.class);
    private final File root;
    @Getter
    private final JTree tree;
    private FileSelectionListener fileSelectionListener;


    public FileTree(File root) {
        LOG.info("Creating a JTree with {}", root);

        this.root = root;
        this.tree = new JTree(createTreeModel(root));
        this.tree.setCellRenderer(new FileTreeCellRenderer());
        this.tree.addTreeSelectionListener(new FileTreeSelectionListener());

        setLayout(new BorderLayout());
        add(new JScrollPane(tree), BorderLayout.CENTER);
        FileTreeContextMenu fileTreeContextMenu = new FileTreeContextMenu(this);

        tree.addMouseListener(new FileTreeMouseAdapter(fileTreeContextMenu, this));

        Thread thread = new Thread(FileWatcher.initialize(root, this));
        thread.start();
    }

    public void addFileSelectionListener(FileSelectionListener fileSelectionListener) {
        this.fileSelectionListener = fileSelectionListener;
    }

    public void refreshTreeModel() {
        LOG.info("refreshTreeModel");
        SwingUtilities.invokeLater(() -> {
            TreeModel newModel = createTreeModel(root);
            tree.setModel(newModel);
        });
    }

    private DefaultTreeModel createTreeModel(File root) {
        DefaultMutableTreeNode rootNode = createTreeNode(root);
        return new DefaultTreeModel(rootNode);
    }

    private DefaultMutableTreeNode createTreeNode(File file) {
        FileNode fileNode = new FileNode(file);

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(fileNode);
        File[] files = file.listFiles();
        if (files != null) {
            for (File child : files) {
                if (child.isDirectory()) {
                    treeNode.add(createTreeNode(child));
                } else {
                    treeNode.add(new DefaultMutableTreeNode(new FileNode(child)));
                }
            }
        }
        return treeNode;
    }

    private class FileTreeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            if (fileSelectionListener == null) return;
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            if (node.getUserObject() instanceof FileNode selectedFile) {
                if (selectedFile.getFile().isDirectory()) return;
                fileSelectionListener.onFileSelected(selectedFile.getFile());
                LOG.info("File selected: {}", selectedFile.getFile().getPath());
            }
        }
    }
}


