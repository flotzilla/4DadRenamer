package pkg4dadrenamer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author obyte
 */
public class FileRenamer {

    private Renamer renamer = null;
    private Scanner sc = new Scanner(System.in);

    public void handleCommand() {
        String command;
        System.out.println("Enter your command or ? for help");
        do {
            System.out.print("-> ");
            command = sc.nextLine();
            parseCommand(command);
        } while (!command.equals("quit"));
        System.out.println("Closing...");
    }

    private void parseCommand(String command) {
        command = command.toLowerCase();
        switch (
                command) {
            case "?":
                soutHelp();
                break;
            case "setdir":
                setDir();
                break;
            case "shuffle":
                renamer.shuffleItems();
                break;
            case "sort":
                renamer.sortItemsByName();
                break;
            case "print":
                renamer.soutItemsList();
                break;
            case "rescan":
                renamer.scanDir();
                break;
            case "addpref":
                renamer.addPrefixToFiles();
                break;
            case "rempref":
                renamer.removePrefixFromFiles();
                break;
            case "quit":
                break;
            default:
                System.out.println("Wrong command syntax");
                break;
        }
    }

    private void soutHelp() {
        System.out.println("List of commands:");
        System.out.println("    quit - close programm");
        System.out.println("    setdir - choose your directory, with wich one you wanna work");
        System.out.println("    rescan - scan your directory again (if your files"
                + "comes out of date)");
        System.out.println("    print - Show rezults of previos operation");
        System.out.println("    addpref - add numeric prefixes to your files");
        System.out.println("    rempref - remove numeric prefixes from your files in folder "
                + "if they exists");
        System.out.println("    shuffle - shuffle your files randomly in list");
        System.out.println("    sort - sorting your files by name in list");
    }

    private void setDir() {
        boolean corrct = false;
        do {
            System.out.println("Set path to your folder:");
            try {
                renamer = new Renamer(new File(sc.nextLine()));
                corrct = renamer.isDirCorrect();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found. Specify your filepath again");
            }
        } while (!corrct);
        if (renamer != null) {
            System.out.println("Scannig directory");
            renamer.scanDir();
            System.out.println(renamer.getItemsList().size()
                    + " files was found");
        }
    }

    public static void main(String[] args) {
        FileRenamer fr = new FileRenamer();
        fr.setDir();
        fr.handleCommand();
    }
}
