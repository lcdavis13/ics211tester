package ics211tester.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
    
    public static void main(String[] args) {
        String rootDirectory = "./submissions/h06";
        extractFiles(new File(rootDirectory));
    }
    
    public static void extractFiles(File rootDir) {
        if (rootDir.isDirectory()) {
            File[] subFiles = rootDir.listFiles();
            if (subFiles != null) {
                for (File file : subFiles) {
                    extractFiles(file); // Recursively call extractFiles for directories
                }
            }
        } else {
            if (rootDir.getName().endsWith(".zip") || rootDir.getName().endsWith(".jar")) {
                // Delete existing extracted directory if it exists
                File extractedDir = new File(rootDir.getAbsolutePath() + "_extracted");
                if (extractedDir.exists()) {
                    deleteDirectory(extractedDir);
                }
                extractZip(rootDir.getAbsolutePath());
            }
        }
    }

    public static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file); // Recursively call deleteDirectory for directories
                }
            }
        }
        directory.delete();
    }

    
    public static void extractZip(String filePath) {
        File dir = new File(filePath + "_extracted");
        if (!dir.exists()) dir.mkdirs();
        
        try (ZipFile zipFile = new ZipFile(filePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(dir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (FileOutputStream out = new FileOutputStream(entryDestination);
                         InputStream in = zipFile.getInputStream(entry)) {  // Corrected line
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = in.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error extracting file " + filePath + ": " + e.getMessage());
        }

    }
}
