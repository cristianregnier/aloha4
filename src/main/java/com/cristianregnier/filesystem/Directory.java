package com.cristianregnier.filesystem;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemComponent {
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
