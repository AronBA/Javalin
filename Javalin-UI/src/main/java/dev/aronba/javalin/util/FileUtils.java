package dev.aronba.javalin.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@UtilityClass
public class FileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            String errMsg = "An unexpected error has occurred trying to delete the file: " + file.getAbsoluteFile() + "Error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean saveStringToFile(File file, String content) {
        try {
            Files.writeString(file.toPath(), content);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            String errMsg = "An unexpected error has occurred trying to write the file: " + file.getAbsoluteFile() + "Error: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errMsg, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void createNewEmptyFile(String path) {
        try {
            Files.createFile(Path.of(path));
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
            String errMsg = "An unexpected error has occurred trying to create the file: " + path + "Error: " + ex.getMessage();
            JOptionPane.showMessageDialog(null, errMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static void renameFile(File file, String newName) {
        try {
            String path = file.getParent() + File.separator + newName;
            File renamedFile = new File(path);
            file.renameTo(renamedFile);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            String errMsg = "An unexpected error has occurred trying to  the file: " + file.getAbsolutePath() + "Error: " + ex.getMessage();
            JOptionPane.showMessageDialog(null, errMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static File findFileByName(String path) {
        return new File(path);
    }
    public static File findFilesByExtension(String extension) {
        return new File("");
    }
}
