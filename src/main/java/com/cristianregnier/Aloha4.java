package com.cristianregnier;

import com.cristianregnier.filesystem.Filesystem;
import com.cristianregnier.shell.Shell;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;


public class Aloha4 {
   public static void main(String args[]) throws Exception {
      if (args.length == 0)
         System.exit(0);

      Scanner in = new Scanner(getFileFromResource(args[0]));
      Shell shell = new Shell(new Filesystem());
      int result = 0;
      while (result == 0) {
         result = shell.execute(in.nextLine());
      }
   }

   private static File getFileFromResource(String fileName) throws URISyntaxException {
      ClassLoader classLoader = Aloha4.class.getClassLoader();
      URL resource = classLoader.getResource(fileName);
      if (resource == null) {
         throw new IllegalArgumentException("file not found! " + fileName);
      } else {
         return new File(resource.toURI());
      }
   }
}


