package ics211tester.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class packageFixer {
	public static void main(String[] args) {
		String hw = "h03";
		
		File folder = new File("src/edu/ics211/" + hw);
		changePackageDeclarations(folder, "edu.ics211." + hw);
	}
	
    public static void changePackageDeclarations(File folder, String newPackage) {
        //File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        String updatedContent = replacePackageDeclaration(content, newPackage);

                        if (!updatedContent.equals(content)) {
                            Files.write(file.toPath(), updatedContent.getBytes());
                            System.out.println("Updated package declaration in: " + file.getName());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String replacePackageDeclaration(String content, String newPackage) {
        Pattern pattern = Pattern.compile("^\\s*package\\s+.*?;", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.replaceFirst("package " + newPackage + ";");
        } else {
            return "package " + newPackage + ";\n\n" + content;
        }
    }
}
