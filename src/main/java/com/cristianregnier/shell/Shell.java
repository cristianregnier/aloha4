package com.cristianregnier.shell;

import com.cristianregnier.filesystem.Directory;
import com.cristianregnier.filesystem.File;
import com.cristianregnier.filesystem.FileSystemComponent;
import com.cristianregnier.filesystem.Filesystem;

public class Shell {
   private CommandParser commandParser;
   private Filesystem filesystem;
   private Directory workingDirectory;

   public Shell(Filesystem filesystem) {
      this.commandParser = new CommandParser();
      this.filesystem = filesystem;
      workingDirectory = filesystem.getRoot();
   }

   public int execute(String command) {
      Command cmd = commandParser.parse(command);
      return process(cmd);
   }

   private int process(Command command) {
      switch (command.command) {
         case "pwd":
            printWorkingDirectory();
            break;
         case "ls":
            listContents(command);
            break;
         case "mkdir":
            makeDirectory(command);
            break;
         case "cd":
            changeDirectory(command);
            break;
         case "touch":
            createFile(command);
            break;
         case "quit":
            return -1;
         default:
            System.out.println("Unrecognized command");
      }
      return 0;
   }

   private void printWorkingDirectory() {
      System.out.println(workingDirectory.getLocation());
   }

   private void listContents(Command command) {
      // Checks recursive option
      boolean isRecursive = false;
      if (command.args.length > 0) {
         if ("-r".equals(command.args[0])) {
            isRecursive = true;
         } else {
            System.out.println("Invalid command");
            return;
         }
      }

      // If there are no items prints nothing
      if (workingDirectory.getChildren().isEmpty()) {
         System.out.println("");
         return;
      }

      // TODO Print recursivly

      // Prints the contents of the current directory. It writes a single item per line.
      workingDirectory.getChildren().stream()
              .forEach(child -> System.out.println(child.getName()));
   }

   private void makeDirectory(Command command) {
      if (command.args.length == 0) {
         System.out.println("Invalid Command");
         return;
      }

      String dirName = command.args[0];
      if (dirName.length() > 100) {
         System.out.println("Invalid File or Folder Name");
         return;
      }

      Long exists = workingDirectory.getChildren().stream()
              .filter(component -> component instanceof Directory && dirName.equals(component.getName()))
              .count();

      if (exists > 0) {
         System.out.println("Directory already exists");
         return;
      }

      workingDirectory.addChild(new Directory(dirName, workingDirectory));
   }

   private void changeDirectory(Command command) {
      if (command.args.length == 0) {
         System.out.println("Invalid Command");
         return;
      }

      String dirName = command.args[0];
      switch (dirName) {
         case "..":
            if (workingDirectory.getParent() != null)
               workingDirectory = (Directory) workingDirectory.getParent();
            break;
         default:
            for (FileSystemComponent child : workingDirectory.getChildren()) {
               if (child instanceof Directory) {
                  Directory directory = (Directory) child;
                  if (dirName.equals(directory.getName())) {
                     workingDirectory = directory;
                     return;
                  }
               }
            }
            System.out.println("Directory not found");
      }
   }

   private void createFile(Command command) {
      if (command.args.length == 0) {
         System.out.println("Invalid Command");
         return;
      }

      String fileName = command.args[0];
      if (fileName.length() > 100) {
         System.out.println("Invalid File or Folder Name");
         return;
      }

      Long exists = workingDirectory.getChildren().stream()
              .filter(component -> component instanceof File && fileName.equals(component.getName()))
              .count();

      if (exists > 0) {
         System.out.println("File already exists");
         return;
      }

      workingDirectory.addChild(new File(fileName, workingDirectory));
   }

}
