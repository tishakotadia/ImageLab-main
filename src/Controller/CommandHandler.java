package controller;


import model.OperationsV3;


import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * The CommandHandler class is responsible for managing various commands
 * related to image processing. It utilizes a HashMap to associate command
 * strings with their respective command classes, allowing for the dynamic
 * creation of command objects using BiFunction.
 */


class CommandHandler {
  private final OperationsV3 operations;
  private final Map<String, BiFunction<String[], Integer, AbstractCommandExecuter>> commandMap;

  /**
   * Constructs a new instance of CommandHandler.
   * This constructor initializes an ImageOperations instance
   * and sets up a command map that associates command names with their
   * corresponding operation creators.
   * The command map includes operations such as loading and
   * saving images, applying filters like brighten,
   * blur, and sharpen, and manipulating color components.
   */

  CommandHandler(OperationsV3 operations) {
    this.operations = operations;
    commandMap = new HashMap<>();
    commandMap.put("load", (cmd, a) -> new Load(cmd, 3));
    commandMap.put("save", (cmd, a) -> new Save(cmd, 3));
    commandMap.put("brighten", (cmd, a) -> new Brighten(cmd, 4));
    commandMap.put("horizontal-flip", (cmd, a) -> new HorizontalFlip(cmd, 3));
    commandMap.put("vertical-flip", (cmd, a) -> new VerticalFlip(cmd, 3));
    commandMap.put("blur", (cmd, a) -> new Blur(cmd, 3));
    commandMap.put("sharpen", (cmd, a) -> new Sharpen(cmd, 3));
    commandMap.put("sepia", (cmd, a) -> new Sepia(cmd, 3));
    commandMap.put("red-component", (cmd, a) -> new ColorComponent(cmd, 3));
    commandMap.put("green-component", (cmd, a) -> new ColorComponent(cmd, 3));
    commandMap.put("blue-component", (cmd, a) -> new ColorComponent(cmd, 3));
    commandMap.put("value-component", (cmd, a) -> new BrightnessComponent(cmd, 3));
    commandMap.put("luma-component", (cmd, a) -> new BrightnessComponent(cmd, 3));
    commandMap.put("intensity-component", (cmd, a) -> new BrightnessComponent(cmd,
            3));
    commandMap.put("rgb-split", (cmd, a) -> new RGBSplit(cmd, 5));
    commandMap.put("rgb-combine", (cmd, a) -> new RGBCombine(cmd, 5));
    commandMap.put("compress", (cmd, a) -> new Compress(cmd, 4));
    commandMap.put("histogram", (cmd, a) -> new Histogram(cmd, 3));
    commandMap.put("color-correct", (cmd, a) -> new ColorCorrect(cmd, 3));
    commandMap.put("levels-adjust", (cmd, a) -> new LevelsAdjust(cmd, 6));
    commandMap.put("quit", (cmd, a) -> new Quit(cmd, 1));
  }

  /**
   * Reads and executes a command from the given input array.
   * This method processes the input command, and retrieves
   * the corresponding command executor from the command map.
   * If the command is recognized, it is executed with the specified
   * ImageOperations instance. If the command is not found in the
   * command map, an IllegalArgumentException is thrown.
   *
   * @param input An array of strings representing the command and its
   *              arguments.
   *              The first element is expected to be the command name.
   * @throws IllegalArgumentException if the command is unknown or not
   *                                  found in the command map.
   */

  boolean readCommand(String[] input) throws IllegalArgumentException {
    String command = input[0];
    BiFunction<String[], Integer, AbstractCommandExecuter> cmd = this.commandMap.get(command);
    if (cmd == null) {
      throw new IllegalArgumentException("Unknown command: " + command);
    }
    AbstractCommandExecuter ex = cmd.apply(input, 0);
    return ex.execute(operations);
  }


}
