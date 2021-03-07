package com.cristianregnier.shell;

import java.util.Arrays;

public class Command {
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
         strBuilder.append(Arrays.toString(args)
                 .replace("[", "")
                 .replace("]", ""));
      }
      return strBuilder.toString();
   }
}

