package com.cristianregnier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Solution {
   public static void main(String args[]) throws Exception {
      /* Enter your code here. Read input from STDIN. Print output to STDOUT */
      Filesystem fs = new Filesystem();
      Shell shell = new Shell(fs);

      Scanner in = new Scanner(System.in);
      while (true) {
         shell.execute(in.nextLine());
      }
   }
}

abstract class FileSystemComponent {
   private String name;
   private FileSystemComponent parent;

   public FileSystemComponent(String name, FileSystemComponent parent) {
      this.name = name;
      this.parent = parent;
   }

   public String getName() {
      return name;
   }

   public FileSystemComponent getParent() {
      return parent;
   }

   public String getLocation() {
      if (parent == null) {
         return "/" + name;
      } else {
         return parent.getLocation() + "/" + name;
      }
   }
}

class Directory extends FileSystemComponent {
   private final List<FileSystemComponent> children;

   public Directory(String name, Directory parent) {
      super(name, parent);
      this.children = new ArrayList<>();
   }

   public void addChild(FileSystemComponent fileSystemComponent) {
      children.add(fileSystemComponent);
   }

   public void removeChild(FileSystemComponent fileSystemComponent) {
      children.remove(fileSystemComponent);
   }

   public List<FileSystemComponent> getChildren() {
      return children;
   }

}

class File extends FileSystemComponent {

   public File(String name, FileSystemComponent parent) {
      super(name, parent);
   }
}

class Filesystem {
   private final List<FileSystemComponent> nodes;

   public Filesystem() {
      nodes = new ArrayList<>();
      nodes.add(new Directory("root", null));
   }

   public Directory getRoot() {
      return (Directory) nodes.get(0);
   }
}


class Shell {
   private Filesystem fs;
   private Directory workingDirectory;

   public Shell(Filesystem fs) {
      this.fs = fs;
      workingDirectory = fs.getRoot();
   }

   public void execute(String command) {
      Command cmd = parse(command);
      process(cmd);
   }

   private Command parse(String command) {
      String[] aux = command.split(" ");
      Command cmd = new Command(aux[0], Arrays.copyOfRange(aux, 1, aux.length));
      return cmd;
   }

   private class Command {
      public final String command;
      public final String[] args;

      public Command(String command, String[] args) {
         this.command = command;
         this.args = args;
      }

      public String toString() {
         StringBuilder strBuilder = new StringBuilder(command);
         if (args.length > 0) {
            strBuilder.append(" ");
            strBuilder.append(Arrays.toString(args).replace("[", "").replace("]", ""));
         }
         return strBuilder.toString();
      }
   }

   private void process(Command command) {
      switch (command.command) {
         case "pwd":
            printWorkingDirectory();
            break;
         case "ls":
            listContents();
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
            quit();
         default:
            System.out.println("Unrecognized command");
      }
   }

   private void printWorkingDirectory() {
      System.out.println(workingDirectory.getLocation());
   }

   private void listContents() {
      String result = workingDirectory.getChildren().stream()
              .map(FileSystemComponent::getName)
              .reduce("", (acc, n2) -> acc + " " + n2);
      System.out.println(result.trim());
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
            if ((Directory) workingDirectory.getParent() != null)
               workingDirectory = (Directory) workingDirectory.getParent();
            break;
         default:
            for (FileSystemComponent child : workingDirectory.getChildren()) {
               if (child instanceof Directory) {
                  Directory directory = (Directory) child;
                  if (dirName.equals(directory.getName())) {
                     workingDirectory = directory;
                     break;
                  }
               }
               System.out.println("Directory not found");
            }
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

   private void quit() {
      System.exit(0);
   }

}
