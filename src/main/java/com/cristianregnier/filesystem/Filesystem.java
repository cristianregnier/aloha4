package com.cristianregnier.filesystem;

import java.util.ArrayList;
import java.util.List;

public class Filesystem {
   private final List<FileSystemComponent> nodes;

   public Filesystem() {
      nodes = new ArrayList<>();
      nodes.add(new Directory("root", null));
   }

   public Directory getRoot() {
      return (Directory) nodes.get(0);
   }
}
