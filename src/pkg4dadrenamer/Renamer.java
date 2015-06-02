package pkg4dadrenamer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author obyte
 */
public class Renamer {

    private File dir;
    private List<File> itemsList;
    private static final String matcher1 = "\\d{1,4}\\.\\s[\\w\\s\\S]++";
    private static final String matcher2 = "\\d{1,4}\\s-\\s[\\w\\s\\S]++";
    private static final String matcher3 = "\\d{1,4}\\s[\\w\\s\\S]++";

    public Renamer(File dir) throws FileNotFoundException {
        this.dir = dir;
        itemsList = new ArrayList<>();
        if (!isDirCorrect()) {
            throw new FileNotFoundException();
        }
    }

    public final boolean isDirCorrect() {
        if (!dir.exists()) {
            System.out.println("Directory " + dir + " does not exists");
            return false;
        }
        if (!dir.isDirectory()) {
            System.out.println("File is not a directory");
            return false;
        }
        return true;
    }

    private boolean removePrefix(File file) {
        String fileName = file.getName();
        if (fileName.matches(matcher1)) {
            //||  || str.matches(matcher3)) {
            String newFileName = fileName.substring(fileName.indexOf(". ") + 2, fileName.length());
            file.renameTo(new File(dir, newFileName));
            return true;
        } else if (fileName.matches(matcher2)) {
            String newFileName = fileName.substring(fileName.indexOf(" - ") + 2, fileName.length());
            file.renameTo(new File(dir, newFileName));
            return true;
        } else if (fileName.matches(matcher3)) {
            String newFileName = fileName.substring(fileName.indexOf(" "), fileName.length());
            file.renameTo(new File(dir, newFileName));
            return true;
        }
        return false;
    }

    public void scanDir() {
        itemsList.clear();
        File[] fileList = dir.listFiles();
        if (fileList.length == 0) {
            System.out.println("Dir is empty, nothing to do");
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                continue;
            }
            itemsList.add(fileList[i]);
        }
    }

    public void addPrefixToFiles() {
        for (int i = 0; i < itemsList.size(); i++) {
            String newName = (i + 1) + ". " + itemsList.get(i).getName();
            itemsList.get(i).renameTo(new File(dir, newName));
        }
        scanDir();
    }

    public void removePrefixFromFiles() {
        int renamedCout = 0;
        for (int i = 0; i < itemsList.size(); i++) {
            if (removePrefix(itemsList.get(i))) {
                renamedCout++;
            }
        }
        System.out.println(renamedCout + " files from " + itemsList.size() 
                + " was renamed");
        scanDir();
    }

    public void shuffleItems() {
        Collections.shuffle(itemsList);
    }

    public void sortItemsByName() {
        Collections.sort(itemsList);
    }

    public void soutItemsList() {
        System.out.println("----------------------");
        System.out.println("Your list content:");
        for (int i = 0; i < itemsList.size(); i++) {
            System.out.println(itemsList.get(i));
        }
        System.out.println("----------------------");

    }

    public List<File> getItemsList() {
        return itemsList;
    }
}
