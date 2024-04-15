package ics211tester.tools;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FolderScanner {
    private String baseFolder;
    private String expectedSubdirectory;
    private List<String> requiredFiles;

    public FolderScanner(String baseFolder, String expectedSubdirectory, String[] requiredFiles) {
        this.baseFolder = baseFolder;
        this.expectedSubdirectory = expectedSubdirectory;
        this.requiredFiles = Arrays.asList(requiredFiles);
    }

    public List<String> findSubfoldersWithoutAllFiles() {
        List<String> subfoldersWithoutAllFiles = new ArrayList<>();
        File root = new File(baseFolder);
        File[] subdirectories = root.listFiles(File::isDirectory);

        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                File targetDir = new File(subdirectory, expectedSubdirectory);
                if (!containsAllFiles(targetDir)) {
                    subfoldersWithoutAllFiles.add(subdirectory.getName());
                }
            }
        }

        return subfoldersWithoutAllFiles;
    }

    private boolean containsAllFiles(File directory) {
        if (!directory.exists()) {
            return false;
        }

        List<File> stack = new ArrayList<>();
        stack.add(directory);

        while (!stack.isEmpty()) {
            File current = stack.remove(stack.size() - 1);

            File[] filesAndDirs = current.listFiles();
            if (filesAndDirs == null) continue;

            List<String> currentFiles = new ArrayList<>();
            for (File entry : filesAndDirs) {
                if (entry.isDirectory()) {
                    stack.add(entry);
                } else if (entry.isFile()) {
                    currentFiles.add(entry.getName());
                }
            }

            if (requiredFiles.stream().allMatch(currentFiles::contains)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String assignment = "h09";
        String[] filenames = {"HW09.java"};

        String submissionFolder = "submissions/" + assignment + "/";
        String submissionSubdirectory = "Submission attachment(s)/";

        FolderScanner scanner = new FolderScanner(submissionFolder, submissionSubdirectory, filenames);
        List<String> subfoldersWithoutAllFiles = scanner.findSubfoldersWithoutAllFiles();

        System.out.println("Subfolders without all required files:");
        for (String folder : subfoldersWithoutAllFiles) {
            System.out.println(folder);
        }
    }
}
