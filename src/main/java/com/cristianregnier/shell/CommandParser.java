package com.cristianregnier.shell;

import java.util.Arrays;

public class CommandParser {
   public Command parse(String command) {
      String[] aux = command.split(" ");
      Command cmd = new Command(aux[0], Arrays.copyOfRange(aux, 1, aux.length));
      return cmd;
   }
}
