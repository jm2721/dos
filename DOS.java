import java.util.Scanner;
import java.util.InputMismatchException;

/** DOS filesystem simulation.  */
public final class DOS {

    /** Entry class.
    */
    private static class Entry {
        String name;

    }
    /** Directory class.
    */
    private static class Directory extends Entry {

        /** Constructor for Directory.
            @param initialName the value to set directory name to
        */
        public Directory(String initialName) {
            this.name = initialName;
        }
    }
    /** File class.
    */
    private static class File extends Entry {

        /** Constructor for File.
            @param initialName the value to set file name to
        */
        public File(String initialName) {
            this.name = initialName;
        }
    }

    /** Global tree representing the filesystem. */
    public static Tree<Entry> filesystem = new TreeImplementation<Entry>();
    /** Current working directory in filesystem. */
    public static Position<Entry> cwd;
    private DOS() {}

    /** Main method.
        @param args the argument vector
    */
    public static void main(String[] args) {
        Directory root = new Directory("/");
        cwd = filesystem.insertRoot(root);
        Scanner kb = new Scanner(System.in);

        System.out.print("> ");
        String command = "";
        String argument = "";
        while (kb.hasNext()) {
            try {
                command = kb.nextLine();
            } catch (InputMismatchException i) {
                System.out.print("?Command not recognized");
            }
            if (command.indexOf(" ") != -1) {
                int index = command.indexOf(" ");
                argument = command.substring(index + 1, command.length());
                command = command.substring(0, index);
                process(command, argument);
            } else {
                process(command);
            }
            System.out.print("> ");
        }
    }

    /** Processes the input command string (no argument).
        @param cmd the command to process
    */
    public static void process(String cmd) {
        switch (cmd) {
            case "ls":
                System.out.print(list(cwd));
                break;
            case "pwd":
                System.out.print(getAbsPath(cwd));
                break;
            case "quit":
                System.exit(0);
                break;
            default:
                System.out.println("?Command not recognized");
                break;
        }
    }

    /** Processes the input command string (with argument).
        @param cmd the command to process
        @param arg the argument to use
    */
    public static void process(String cmd, String arg) {
        switch (cmd) {
            case "cd":
                changeDir(arg);
                break;
            case "mkdir":
                makeDir(arg);
                break;
            case "rmdir":
                remDir(arg);
                break;
            case "mk":
                make(arg);
                break;
            case "rm":
                remove(arg);
                break;
            default:
                System.out.println("?Command not recognized");
                break;
        }
    }

    /** "ls" method, list all files in directory.
        @param dir the directory from which to show files.
        @return list a string representing all directories/files in dir
    */
    public static String list(Position<Entry> dir) {
        String list = "";
        String[] names = sortAlphabetical(dir);

        for (int i = 0; i < names.length; i++) {
            list += names[i] + "\n";
        }
        /*for (Position<Entry> e : filesystem.children(dir)) {
            if (e.get() instanceof Directory) {
                list += e.get().name + "/\n";
            } else {
                list += e.get().name + "\n";
            }
        }*/
        return list;
    }

    private static String[] sortAlphabetical(Position<Entry> dir) {
        /*
            Put the names of the directories into an array.
            Then sort that array in alphabetical order with
            a bubble sort. Then return it for use with list method.
        */
        int count = 0;
        for (Position<Entry> e : filesystem.children(dir)) {
            count += 1;
        }
        String[] alpha = new String[count];

        int index = 0;
        for (Position<Entry> e : filesystem.children(dir)) {
            if (e.get() instanceof Directory) {
                alpha[index] = e.get().name + "/";
            } else {
                alpha[index] = e.get().name;
            }
            index += 1;
        }
        for (int i = count - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (alpha[j].compareToIgnoreCase(alpha[j + 1]) > 0) {
                    String temp = alpha[j];
                    alpha[j] = alpha[j + 1];
                    alpha[j + 1] = temp;
                }
            }
        }
        return alpha;
    }

    /** pwd method, gets absolute path to location.
        @param current the current directory
        @return abs a string representing the absolute path
    */
    public static String getAbsPath(Position<Entry> current) {
        String abs = "";
        if (filesystem.isRoot(cwd)) {
            abs = "/";
        }
        Position<Entry> copy = current;

        while (filesystem.hasParent(copy)) {
            abs = "/" + copy.get().name + abs;
            copy = filesystem.parent(copy);
        }
        abs += "\n";
        return abs;
    }
    /** "cd" method, change current working directory.
        @param newDir the directory to go into
    */
    public static void changeDir(String newDir) {
        boolean exists = false;

        if (newDir.equals("..")) {
            if (!filesystem.isRoot(cwd)) {
                cwd = filesystem.parent(cwd);
            }
        } else if (find(newDir) == null) {
            System.out.println("?Directory does not exist");
        } else if (find(newDir).get() instanceof File) {
            System.out.println("?Target is file, not directory");
        } else {
            cwd = find(newDir);
        }
    }
    /** "mkdir" method, make a new directory.
        @param newDir the new name of directory
    */
    public static void makeDir(String newDir) {
        Entry newDirectory = new Directory(newDir);
        if (find(newDir) == null) {
            if (newDir.equals("..")) {
                System.out.println("?Can't name a directory \"..\"");
            } else {
                filesystem.insertChild(cwd, newDirectory);
            }
        } else if (find(newDir).get() instanceof Directory) {
            System.out.println("?A directory already exists by that name");
        } else if (find(newDir).get() instanceof File) {
            System.out.println("?A file already exists by that name");
        }
    }

    /** "rmdir" method, remove a directory.
        @param dir the name of directory to remove
    */
    public static void remDir(String dir) {

        if (find(dir) == null)  {
            System.out.println("?Directory does not exist");
        } else if (find(dir).get() instanceof File) {
            System.out.println("?To remove files, use rm");
        } else {
            try {
                filesystem.removeAt(find(dir));
            } catch (RemovalException ex) {
                System.out.println("?The directory is not empty");
            }
        }
    }

    /** "make" method, change current working directory.
        @param newFile the name of the file to make
    */
    public static void make(String newFile) {
        Entry nFile = new File(newFile);
        if (find(newFile) == null) {
            if (newFile.equals("..")) {
                System.out.println("?Can't name a file \"..\"");
            } else {
                filesystem.insertChild(cwd, nFile);
            }
        } else if (find(newFile).get() instanceof Directory) {
            System.out.println("?A directory already exists by that name");
        } else if (find(newFile).get() instanceof File) {
            System.out.println("?A file already exists by that name");
        }
    }

    /** "rm" method, change current working directory.
        @param file the name of the file to remove
    */
    public static void remove(String file) {

        if (find(file) == null) {
            System.out.println("?File does not exist");
        } else if (find(file).get() instanceof Directory) {
            System.out.println("?To remove directories, use rmdir");
        } else {
            filesystem.removeAt(find(file));
        }
    }

    private static Position<Entry> find(String entryName) {
        for (Position<Entry> e : filesystem.children(cwd)) {
            if (e.get().name.equals(entryName)) {
                return e;
            }
        }
        return null;
    }
}
