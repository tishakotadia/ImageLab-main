package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import model.OperationsV3;
import view.ProgramView;


/**
 * The CommandReader class is responsible for reading and executing
 * commands from a script file or the command-line. It utilizes a CommandHandler to
 * process commands parsed from the file.
 * Class implements the ImageAppController interface.
 * This class provides methods to read commands from a specified file
 * path, ensuring that the file is in the correct format and does not
 * contain comments.
 */


public class CommandReader implements ImageAppController {
  private final CommandHandler handler;
  private final Readable in;
  private final ProgramView view;

  /**
   * Constructs a new CommandReader instance for interactive mode.
   * This constructor initializes the CommandHandler instance
   * used to execute commands read from the script/command-line.
   */

  public CommandReader(OperationsV3 operations, Readable in, ProgramView view) {
    this.handler = new CommandHandler(operations);
    this.in = in;
    this.view = view;
  }

  /**
   * Reads commands from the specified script file and executes them.
   * This method reads each line of the file, ignoring empty lines and
   * comments (lines starting with '#'). It trims whitespace, splits
   * the lines into tokens, and converts the command to lowercase before
   * passing it to the CommandHandler for execution.
   *
   * @param filepath the path to the script file to be read.
   */

  private void scriptReader(String filepath) {
    try {
      File file = new File(filepath);
      BufferedReader br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null) {
        if (!st.isEmpty() && st.charAt(0) != '#') {
          st = st.trim();
          st = st.replaceAll("\\s+", " ");
          String[] tokens = st.split(" ");
          tokens[0] = tokens[0].toLowerCase();
          try {
            boolean t = this.handler.readCommand(tokens);
            this.outputMessage(t, tokens[0]);
          } catch (Exception e) {
            this.view.setOutput(String.format(e.getMessage() + "\n"));
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("File not found");
    }
  }

  /**
   * Displays a message in the output view indicating whether a specific command
   * was executed successfully or not.
   * This method is typically used to provide feedback to the user
   * or log the status of an operation performed in the application.
   *
   * @param t   a boolean flag representing the success status of the command:
   * @param cmd the name of the command being reported. This is used to construct
   */
  private void outputMessage(boolean t, String cmd) {
    if (t) {
      this.view.setOutput(String.format(cmd + " executed successfully\n"));
    } else {
      this.view.setOutput(String.format(cmd + " not executed successfully\n"));
    }
  }

  /**
   * Prompts the user for a script file path and validates the input.
   * User can also provide the input from the command line also.
   * This method checks that the provided file has a .txt extension,
   * and if valid, invokes the scriptReader method to process
   * the commands in the file. If the file extension is incorrect,it
   * throws an IllegalArgumentException.
   *
   * @throws IllegalArgumentException if the provided file is not a
   *                                  .txt file.
   * @throws IOException              if an error occurs while reading
   *                                  the file.
   */

  private void getCommand() throws IOException {
    Scanner scn = new Scanner(this.in);
    while (scn.hasNextLine()) {
      String script = scn.nextLine();
      script = script.trim();
      script = script.replaceAll("\\s+", " ");
      String[] init = script.split(" ");
      if (!(init[0].equals("run") || init[0].equals("-file"))) {
        try {
          boolean t = this.handler.readCommand(init);
          this.outputMessage(t, init[0]);
        } catch (Exception e) {
          this.view.setOutput(String.format(e.getMessage() + "\n"));
        }
      } else {
        try {
          if (init.length != 2) {
            throw new IllegalArgumentException("Command is not valid");
          }
          this.checkScriptFile(init[1]);
        } catch (Exception e) {
          this.view.setOutput(String.format(e.getMessage() + "\n"));
        }
      }
    }
  }


  /**
   * Starts the application by loading the necessary resources,
   * such as the script path required for the application's execution.
   * This method initiates the process by invoking getCommand. If an error
   * occurs during this process (e.g., file not found, permission issues),
   * an IOException will be caught, and an appropriate error message
   * will be printed to the console.
   * Implementations should ensure proper error handling and provide
   * informative error messages to guide users when issues arise.
   */

  @Override
  public void startApplication() {
    try {
      this.view.setOutput("Enter the Command:\n");
      this.getCommand();
    } catch (IOException e) {
      throw new IllegalArgumentException("Something went wrong");
    }
  }


  /**
   * Helper method to validate the file path and check for the .txt
   * extension and execute the commands in the script file.
   */

  private void checkScriptFile(String path) throws IllegalArgumentException {
    String ext = path.substring(path.lastIndexOf(".") + 1);
    if (!ext.equals("txt")) {
      throw new IllegalArgumentException("Provide txt File only");
    }
    this.scriptReader(path);
  }
}
