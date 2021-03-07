package com.cristianregnier.filesystem;

public abstract class FileSystemComponent {
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
