package utilities;

import extensions.Verifications;

import org.apache.maven.surefire.shared.io.FileUtils;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class UsefulMethods {

    public final Verifications verifications;

    public UsefulMethods(Verifications verifications) {
        this.verifications = verifications;
    }


    public void uploadFileWithRobot(String imagePath) {
        StringSelection stringSelection = new StringSelection(imagePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Upload attachment failed! " + e);
        }
        assert robot != null;
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public String getData(String Configuration) {
        try {
            DocumentBuilder dBuilder;
            Document doc = null;
            File fXmlFile = new File("./Configuration/DataConfig.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            if (doc == null) {
                throw new IllegalStateException("Failed to parse configuration file");
            }
            doc.getDocumentElement().normalize();
            String value = doc.getElementsByTagName(Configuration).item(0).getTextContent();

            if (value == null || value.isEmpty()) {
                throw new IllegalStateException("Configuration value for " + Configuration + " not found");
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read configuration: " + e.getMessage(), e);
        }
    }

    public String getProjectPath() {
        return System.getProperty("user.dir");
    }

    public LocalDateTime getPCDateTime(String dateFormat) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current PC time and date is " + dtf.format(now));
        return now;
    }

    public String calculateTimeDifference(LocalDateTime baseTime, LocalDateTime newTime) {
        String timeDifferenceInMilliseconds = String.valueOf(ChronoUnit.MILLIS.between(baseTime, newTime));
        System.out.println("The time difference in milliseconds is " + timeDifferenceInMilliseconds);
        return timeDifferenceInMilliseconds;
    }

    public String[] reverseArray(String[] array) {
        int left = 0;               // Index of the leftmost element
        int right = array.length - 1;  // Index of the rightmost element
        // Swap elements from the beginning and end towards the middle
        while (left < right) {
            // Swap array[left] and array[right]
            String temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            // Move the indices toward the middle
            left++;
            right--;
        }
        return array;
    }



    public static void deleteFile(String filePath) throws IOException {
        Path path = FileSystems.getDefault().getPath(filePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException x) {
            System.err.println(x);
        }
        boolean isEmpty = isEmpty(path);
        if (isEmpty) System.out.println("Directory is empty");
    }

    public static boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            }
        }
        return false;
    }



    //Originally included file chooser that allows specifying file name and path, but didn't work so removed
    public static void saveDocument() {
        try {
            // Simulate pressing Ctrl+S
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(1000);
            // Simulate pressing Enter to save the file
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            System.out.println("Saving document failed " + e);
        }
    }

    public void modifyExistingFile(String oldFilePath, String newFilePath) {
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        try {
            FileUtils.moveFile(oldFile, newFile);
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.err.println("Failed to rename the file: " + e.getMessage());
        }
    }

    public boolean checkIfFileExists(String filePath) {
        File file = FileUtils.getFile(filePath);
        boolean existence;
        if (file != null && file.exists()) {
            System.out.println("File exists.");
            existence = true;
        } else {
            System.out.println("File does not exist.");
            existence = false;
        }
        return existence;
    }

    public void verifyFileExists(String filePath) {
        String verdict = String.valueOf(checkIfFileExists(filePath));
        assertEquals(verdict, "true");
    }

    public void verifyFileDoesNotExist(String filePath) {
        String verdict = String.valueOf(checkIfFileExists(filePath));
        assertEquals(verdict, "false");
    }

    //The folder itself remain
    public void deleteAllFilesInAGivenPath(String folderPath) throws InterruptedException {
        File f = new File(folderPath);
        String[] s = f.list();
        if (s != null && s.length == 0) System.out.println("The folder is already empty - nothing to delete");
        else if (s != null) {
            System.out.println("The folder initially contained " + s.length + " files");
            for (String s1 : s) {
                File f1 = new File(f, s1);
                System.out.println(f1);
            }
            for (String s1 : s) {
                File f1 = new File(f, s1);
                f1.delete();
                Thread.sleep(1000);
            }
            String[] s1 = f.list();
            if (s1 != null) {
                assertEquals(s1.length, 0);
                System.out.println("All files were successfully deleted - folder is empty");
            }
        }
    }

    public void deleteEntireFolderWithItsContent(String[] args) {
        // Specify the path to the folder you want to delete
        String folderPath = "C:\\path\\to\\your\\folder";

        // Create a File object for the folder
        File folder = new File(folderPath);

        // Check if the folder exists
        if (folder.exists()) {
            // Call the deleteFolder method to delete the folder and its contents
            if (deleteFolder(folder)) {
                System.out.println("Folder deleted successfully.");
            } else {
                System.err.println("Failed to delete folder.");
            }
        } else {
            System.err.println("Folder does not exist.");
        }
    }

    public boolean deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Recursively delete files and sub-folders
                    if (!deleteFolder(file)) {
                        return false;
                    }
                }
            }
        }
        // Delete the folder itself
        return folder.delete();
    }
}