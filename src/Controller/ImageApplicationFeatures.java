package controller;

import java.awt.image.BufferedImage;
import java.io.File;

import model.OperationsV3;
import view.IView;


/**
 * The ImageApplicationFeatures class implements the Features interface,
 * providing the core logic for handling image processing operations.
 * This class interacts with the CommandHandler to process
 * user commands and with the OperationsV3 model
 * for performing image manipulation tasks.
 * The IView is used to communicate with the user
 * interface, enabling interaction between the model and the view.
 * This class serves as a central point for coordinating the image processing logic,
 * processing commands, and updating the view accordingly.
 */
public class ImageApplicationFeatures implements Features {
  private final CommandHandler commandHandler;
  private final OperationsV3 model;
  private final IView view;

  /**
   * Constructs an ImageApplicationFeatures object that acts as a controller
   * to facilitate communication between the model and the
   * view in the image processing application.
   *
   * @param model the image processing model handling
   *              the application's core operations.
   * @param view  the user interface of the application
   *              for user interactions and display.
   */
  public ImageApplicationFeatures(OperationsV3 model, IView view) {
    this.model = model;
    this.view = view;
    this.commandHandler = new CommandHandler(this.model);
  }

  /**
   * Checks if the provided image name is valid (not  null).
   * This method basically checks if the image is loaded or not.
   * If the image name is null, it notifies the user via the view
   * and returns false. Otherwise, it returns true.
   *
   * @param imageName the name of the image to validate.
   * @return true if the image name is valid; false otherwise.
   */
  private boolean checkImage(String imageName) {
    if (imageName == null) {
      this.view.showImageNotPresent();
      return false;
    }
    return true;
  }

  /**
   * Generates a histogram for the specified image and
   * returns it as a BufferedImage.
   * This method constructs a histogram command,
   * executes it using the command handler,
   * and converts the resulting histogram image
   * into a format suitable for display.
   *
   * @param imageName the name of the image for which
   *                  the histogram is to be generated.
   * @return the histogram image as a BufferedImage.
   */
  private BufferedImage histogram(String imageName) {
    String command = "histogram " + imageName + " " + imageName + "hist";
    this.commandHandler.readCommand(command.split(" "));
    return this.convertToDisplay(imageName + "hist");
  }

  /**
   * Displays an image and its corresponding histogram in the view.
   * This method retrieves the image by its name,
   * converts it for display, and shows it in the
   * view along with its histogram.
   *
   * @param name the name of the image to be displayed.
   */
  private void displayImage(String name) {
    BufferedImage image = this.convertToDisplay(name);
    this.view.showImage(name, image);
    BufferedImage histogram = this.histogram(name);
    this.view.showHistogram(histogram);
  }

  /**
   * Displays the split view of an image in the view.
   * This method retrieves the image by its name, converts
   * it for display, and shows it in the split view panel
   * of the application.
   *
   * @param name the name of the image to be displayed
   *             in the split view.
   */
  private void displaySplitImage(String name) {
    BufferedImage image = this.convertToDisplay(name);
    this.view.showSplitImage(image);
  }

  /**
   * Converts an image represented by a 3D array
   * into a BufferedImage.
   * This method retrieves the image data as a 3D array
   * (RGB values) from the model,and then constructs a
   * BufferedImage with the corresponding width, height,
   * and pixel data for display.
   *
   * @param name the name of the image to be converted
   * @return a BufferedImage representation of the image
   */
  private BufferedImage convertToDisplay(String name) {
    int[][][] arr = this.model.saveImage(name);
    int width = arr[0].length;
    int height = arr.length;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = arr[i][j][0] << 16 | arr[i][j][1] << 8 |
                arr[i][j][2];
        img.setRGB(j, i, rgb);
      }
    }
    return img;
  }

  /**
   * Generates a command based on the given operation and
   * image name, then executes it.
   * This method processes the operation,
   * constructs a command string, and passes it
   * to the command handler for execution.
   * After executing the command, it displays
   * the resulting image in the view.
   *
   * @param operation the operation to be performed (e.g., "blur", "sharpen")
   * @param imageName the name of the image to apply the operation on
   */
  @Override
  public void commandGenerator(String operation, String imageName) {
    String endName = operation;
    if (operation.length() != 1) {
      String[] temp = operation.split(" ");
      endName = temp[0];
    }
    String command = operation + " " + imageName + " " + imageName + "_" + endName;

    this.commandHandler.readCommand(command.split(" "));
    this.displayImage(imageName + "_" + endName);
  }

  /**
   * Generates a command for splitting an image based on the
   * specified operation and percentage, then executes it.
   * This method constructs a command string with the operation,
   * image name, and split percentage, passes the command to
   * the command handler for execution, and
   * displays the resulting split image.
   *
   * @param operation  the operation to be applied (e.g., "blur", "sharpen").
   * @param percentage the percentage for the split operation (e.g., "50").
   * @param imageName  the name of the image to be processed.
   */
  private void commandGeneratorSplit(String operation,
                                     String percentage, String imageName) {
    String command = operation + " " + imageName + " " + imageName + "_" + operation
            + "_Split" + " split " + percentage;
    this.commandHandler.readCommand(command.split(" "));
    this.displaySplitImage(imageName + "_" + operation + "_Split");
  }

  /**
   * Loads an image from the file system and displays it on GUI.
   * This method retrieves the file path from the view,
   * checks if the image is loaded or not, and then constructs
   * a command to load the image. After the image is loaded,
   * it is displayed in the view.
   */
  @Override
  public void loadImage() {
    if (!this.view.checkImage()) {
      return;
    }
    File path = this.view.getFilePath();
    if (path == null) {
      return;
    }
    String temp = path.getName();
    String name = temp.substring(0, temp.lastIndexOf("."));
    String command = "load " + path.getAbsolutePath() + " " + name;
    this.commandHandler.readCommand(command.split(" "));
    this.displayImage(name);
  }

  /**
   * Saves an image to the specified file path.
   * This method checks if the image is loaded or not,
   * retrieves the save file path from the view,
   * and constructs a command to save the image.
   * If the save is successful, a success message is shown.
   *
   * @param imageName the name of the image to be saved
   */
  @Override
  public void saveImage(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    File path = this.view.getSaveFilePath();
    if (path != null) {
      String command = "save " + path.getAbsolutePath() + " " + imageName;
      this.commandHandler.readCommand(command.split(" "));
      this.view.showSaveSuccess();
    }
  }

  /**
   * Applies the sepia effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the sepia effect in the view.
   *
   * @param imageName the name of the image to apply the sepia effect.
   */
  @Override
  public void applySepia(String imageName) {
    this.applySplitPreview(imageName, "sepia");
  }

  /**
   * Applies the blur effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the blur effect in the view.
   *
   * @param imageName the name of the image to apply the blur effect.
   */
  @Override
  public void applyBlur(String imageName) {
    this.applySplitPreview(imageName, "blur");
  }

  /**
   * Applies the sharpen effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the sharpen effect in the view.
   *
   * @param imageName the name of the image to apply the sharpen effect.
   */
  @Override
  public void applySharp(String imageName) {
    this.applySplitPreview(imageName, "sharpen");
  }

  /**
   * Applies the horizontal-flip effect to the specified image
   * and display it on GUI.
   * This method checks if the image is loaded or not
   * and then applies the horizontal-flip operation on image.
   *
   * @param imageName the name of the image to apply the horizontal effect.
   */
  @Override
  public void applyHorizontalFlip(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("horizontal-flip", imageName);
  }

  /**
   * Applies the vertical-flip effect to the specified image
   * and display it on GUI.
   * This method checks if the image is loaded or not
   * and then applies the vertical-flip operation on image.
   *
   * @param imageName the name of the image to apply the vertical effect.
   */
  @Override
  public void applyVerticalFlip(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.commandGenerator("vertical-flip", imageName);
  }

  /**
   * Applies the color-correct effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the color-correct effect in the view.
   *
   * @param imageName the name of the image to apply the color-correct effect.
   */
  @Override
  public void applyColorCorrect(String imageName) {
    this.applySplitPreview(imageName, "color-correct");
  }

  /**
   * Applies the Luma-Component effect to the specified image
   * and preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the luma effect in the view.
   *
   * @param imageName the name of the image to apply the luma effect.
   */
  @Override
  public void applyLuma(String imageName) {
    this.applySplitPreview(imageName, "luma-component");
  }

  /**
   * Applies the red-component effect to the specified image
   * and preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the red-component effect in the view.
   *
   * @param imageName the name of the image to apply the red-component effect.
   */
  @Override
  public void applyRed(String imageName) {
    this.applySplitPreview(imageName, "red-component");
  }

  /**
   * Applies the green-component effect to the specified image
   * and preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the green-component effect in the view.
   *
   * @param imageName the name of the image to apply the green-component effect.
   */
  @Override
  public void applyGreen(String imageName) {
    this.applySplitPreview(imageName, "green-component");
  }

  /**
   * Applies the blue-component effect to the specified image
   * and preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the blue-component effect in the view.
   *
   * @param imageName the name of the image to apply the blue-component effect.
   */
  @Override
  public void applyBlue(String imageName) {
    this.applySplitPreview(imageName, "blue-component");
  }

  /**
   * This method basically call an additional frame in the GUI
   * which provides the support to preview various operations
   * like sepia, sharpen, blur etc.
   * This method checks if the image is loaded or not
   * and then triggers the split preview panel for
   * the command provided as input effect in the view.
   *
   * @param imageName   the image on which operation is to be done.
   * @param commandName the command to be carried out.
   */
  private void applySplitPreview(String imageName, String commandName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    if (this.view.splitPreview(commandName, this)) {
      this.commandGeneratorSplit(commandName, "50", imageName);
    }
  }

  /**
   * Applies a split operation to the specified
   * image based on the provided percentage.
   * This method checks if the percentage is
   * valid, and if so, it triggers the split operation
   * by generating and executing the corresponding command.
   *
   * @param percentage  the percentage value for the split operation
   * @param commandName the name of the command for the split operation
   * @param imageName   the name of the image to be processed
   */
  @Override
  public void applySplit(String percentage, String commandName, String imageName) {
    if (this.percentageCheck(percentage)) {
      return;
    }
    this.commandGeneratorSplit(commandName, percentage, imageName);
  }

  /**
   * Checks if the provided percentage is valid.
   * This method attempts to parse the percentage
   * as a double and checks if the value is within
   * the valid range 0 to 100.
   * If the value is invalid or the format is incorrect, an error
   * message is displayed.
   *
   * @param percentage the percentage value to be validated
   * @return true if the percentage is invalid, false otherwise
   */
  private boolean percentageCheck(String percentage) {
    try {
      double value = Double.parseDouble(percentage);
      if (value < 0 || value > 100) {
        this.view.showError("Provide valid value of percentage.");
        return true;
      }
      return false;
    } catch (NumberFormatException e) {
      this.view.showError("Provide valid value of percentage.");
      return true;
    }
  }

  /**
   * Applies the levels-adjust effect to the specified image
   * and preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the levels-adjust preview panel for
   * the levels-adjust effect in the view.
   *
   * @param imageName the name of the image to apply the levels-adjust effect.
   */
  @Override
  public void applyLevelAdjust(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.levelAdjust(this);
  }

  /**
   * Adjusts the levels of the specified image based on the
   * provided parameters and displays the result.
   * This method checks if the level adjustment properties are valid,
   * parses the percentage value,and constructs a
   * command to adjust the image levels.
   * It then executes the command and displays
   * the adjusted image in the split view.
   *
   * @param imageName  the name of the image to be adjusted
   * @param bValue     the black point value for the adjustment
   * @param mValue     the mid-point value for the adjustment
   * @param wValue     the white point value for the adjustment
   * @param percentage the percentage to apply for the split by default the
   *                   value is 100.
   */
  @Override
  public void getLevelAdjust(String imageName, String bValue, String mValue, String wValue,
                             String percentage) {

    if (checkLevelAdjustProp(bValue, mValue, wValue)) {
      return;
    }
    double value;
    if (percentage.isEmpty()) {
      value = 100.0;
    } else {
      if (percentageCheck(percentage)) {
        return;
      } else {
        value = Double.parseDouble(percentage);
      }
    }
    int b = Integer.parseInt(bValue);
    int m = Integer.parseInt(mValue);
    int w = Integer.parseInt(wValue);
    String command = "levels-adjust " + b + " " + m + " " + w + " " + imageName + " " +
            imageName + "_adjusted " +
            "split " + value;
    this.commandHandler.readCommand(command.split(" "));
    this.displaySplitImage(imageName + "_adjusted");
  }

  /**
   * Checks if the level adjustment properties
   * black, mid, and white values are valid.
   * This method validates that the black, mid, and white
   * values are integers between 0 and 255, and that the
   * values are in ascending order (B < M < W).
   * If any validation fails, an error
   * message is shown to the user.
   *
   * @param bValue the black point value
   * @param mValue the mid-point value
   * @param wValue the white point value
   * @return true if the values are invalid, false if they are valid
   */
  private boolean checkLevelAdjustProp(String bValue, String mValue, String wValue) {
    try {
      int b = Integer.parseInt(bValue);
      int m = Integer.parseInt(mValue);
      int w = Integer.parseInt(wValue);
      if (b < 0 || b > 255 || m < 0 || m > 255 || w < 0 || w > 255) {
        this.view.showError("All values (B, M, and W) must be between 0 to 255");
        return true;
      }
      if (!(b < m && m < w)) {
        this.view.showError("Value of B,M and W must be in ascending order (B < M < W)");
        return true;
      }
      return false;
    } catch (NumberFormatException e) {
      this.view.showError("All values (B, M, and W) must be provided and all must be whole value.");
    }
    return true;
  }

  /**
   * Generates and applies a level adjustment to the
   * specified image based on the provided values.
   * This method first checks the validity of the
   * black, mid, and white values.
   * If the values are valid, it constructs a command
   * for adjusting the levels of the image and executes it.
   *
   * @param bValue    the black point value for the adjustment
   * @param mValue    the mid-point value for the adjustment
   * @param wValue    the white point value for the adjustment
   * @param imageName the name of the image to be adjusted
   * @return true if the adjustment was applied successfully, false if any validation failed
   */
  @Override
  public boolean generateLevelAdjust(String bValue,
                                     String mValue, String wValue, String imageName) {
    if (checkLevelAdjustProp(bValue, mValue, wValue)) {
      return false;
    }
    int b = Integer.parseInt(bValue);
    int m = Integer.parseInt(mValue);
    int w = Integer.parseInt(wValue);
    this.commandGenerator("levels-adjust " + b + " " + m + " " + w, imageName);
    return true;
  }

  /**
   * Applies compression to the specified image.
   * This method checks if the image is loaded or not
   * and then triggers the image compression pre-view
   * in the view.
   *
   * @param imageName the name of the image to be compressed
   */
  @Override
  public void applyCompress(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.compressImage(this);
  }

  /**
   * Generates and applies compression to the specified
   * image based on the given percentage.
   * This method validates the compression percentage,
   * constructs the necessary command,
   * and applies the compression to the image.
   *
   * @param percentage the percentage for image compression
   * @param imageName  the name of the image to be compressed
   * @param checker    a boolean value to check if the preview or apply button is pressed.
   * @return true if the compression was applied successfully, false if the percentage is invalid
   */
  @Override
  public boolean generateCompress(String percentage, String imageName, boolean checker) {
    if (this.percentageCheck(percentage)) {
      return false;
    }
    if (checker) {
      String command = "compress" + " " + percentage + " " + imageName + " "
              + imageName + "_Compress";
      this.commandHandler.readCommand(command.split(" "));
      this.displaySplitImage(imageName + "_Compress");
    } else {
      this.commandGenerator("compress " + percentage, imageName);
    }
    return true;
  }

  /**
   * Applies brightness to the specified image.
   * This method checks if the image is loaded or not
   * and then triggers the image brightness pre-view
   * in the view.
   *
   * @param imageName the name of the image to be compressed
   */
  @Override
  public void applyBrightness(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    this.view.brightness(this);
  }

  /**
   * Generates and applies brightness adjustment to the
   * specified image based on the provided value.
   * This method validates the brightness change value,
   * constructs the necessary command,
   * and applies the brightness adjustment to the image.
   *
   * @param imageName the name of the image to be adjusted
   * @param value     the brightness adjustment value
   * @param checker   a boolean value to check if the preview or apply button is pressed.
   * @return true if the brightness was applied successfully, false if the value is invalid
   */
  @Override
  public boolean generateBright(String imageName, String value, boolean checker) {
    try {
      int change = Integer.parseInt(value);
      if (checker) {
        String command = "brighten" + " " + change + " " + imageName + " "
                + imageName + "_Brighten";
        this.commandHandler.readCommand(command.split(" "));
        this.displaySplitImage(imageName + "_Brighten");
      } else {
        this.commandGenerator("brighten " + change, imageName);
      }
      return true;
    } catch (NumberFormatException e) {
      this.view.showError("Value entered must be integer value.");
      return false;
    }
  }

  /**
   * Applies downscaling to the specified image.
   * This method checks if the image is loaded or not,
   * retrieves the image data, and triggers the downscaling
   * process preview in the view and passed the image's dimensions.
   *
   * @param imageName the name of the image to be downscaled
   */
  @Override
  public void applyDownScale(String imageName) {
    boolean t = this.checkImage(imageName);
    if (!t) {
      return;
    }
    int[][][] temp = this.model.saveImage(imageName);
    this.view.downScale(temp.length, temp[0].length, this);
  }

  /**
   * Generates and applies downscaling to the specified
   * image based on the provided width and height.
   * This method checks if the downscaling parameters
   * are valid, applies the downscaling, and
   * displays the resulting image.
   *
   * @param imageName the name of the image to be downscaled
   * @param width     the target width for downscaling
   * @param height    the target height for downscaling
   * @param checker   a boolean value to check if the preview or apply button is pressed.
   * @return true if downscaling was applied successfully, false if the parameters are invalid
   */
  @Override
  public boolean generateDown(String imageName, String width, String height, boolean checker) {
    boolean t = this.checkForDownScale(imageName, width, height);
    if (!t) {
      return false;
    }
    if (checker) {
      this.displaySplitImage(imageName + "_DownScale");
    } else {
      this.displayImage(imageName + "_DownScale");
    }
    return true;
  }

  /**
   * Checks if the downscaling parameters  are valid for the specified image.
   * This method attempts to parse the width and height,
   * then checks if the downscaling can be applied.
   * It ensures the new dimensions are smaller than the original ones and non-zero.
   * If any validation fails, an error message is displayed.
   *
   * @param imageName the name of the image to be downscaled
   * @param width     the target width for downscaling
   * @param height    the target height for downscaling
   * @return true if the downscaling parameters are valid, false otherwise
   */
  private boolean checkForDownScale(String imageName, String width, String height) {
    try {
      int newWidth = Integer.parseInt(width);
      int newHeight = Integer.parseInt(height);
      DownScaling temp = new DownScaling(imageName, newHeight, newWidth,
              imageName + "_DownScale");
      if (!temp.execute(this.model)) {
        this.view.showError("The height and width must be smaller " +
                "than the original values and positive integer.");
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      this.view.showError("Value of height and width entered must be integer value.");
      return false;
    }
  }

}
