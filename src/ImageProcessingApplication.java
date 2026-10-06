import java.io.InputStreamReader;

import controller.CommandReader;
import controller.ImageAppController;
import controller.MVCController;
import model.ImageOperationsV3;
import model.OperationsV3;
import view.GuiView;
import view.IView;
import view.ProgramView;
import view.ViewHandler;

import java.io.Reader;
import java.io.StringReader;

/**
 * This is the main class of the entire application.
 * This class is the entry point to the entire program.
 */

public class ImageProcessingApplication {

  /**
   * Main function to start the application.
   * Function initializes the objects of view, controller
   * and model.
   * From here the entire control is given to the controller class.
   * Also from here the program determines which how will the user
   * interacts with the program, whether GUI, direct script based or
   * command line based.
   *
   * @param args the standard argument given to read data from
   *             command prompt.
   */
  public static void main(String[] args) {
    OperationsV3 operations = new ImageOperationsV3();
    if (args.length != 0) {
      ImageAppController src;
      ProgramView view = new ViewHandler(System.out);
      if (args[0].equals("-file") && args.length == 2) {
        StringBuilder nonInteractive = new StringBuilder();
        for (String arg : args) {
          nonInteractive.append(arg).append(" ");
        }
        nonInteractive.append("\n").append("quit");
        Reader in = new StringReader(nonInteractive.toString());
        src = new CommandReader(operations, in, view);
      } else if (args[0].equals("-text") && args.length == 1) {
        src = new CommandReader(operations, new InputStreamReader(System.in), view);
      } else {
        return;
      }
      src.startApplication();
    } else {
      IView view = new GuiView("Image Processing Application");
      ImageAppController mvcController = new MVCController(operations, view);
      mvcController.startApplication();
    }
  }
}
