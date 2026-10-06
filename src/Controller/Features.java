package controller;

/**
 * The interface defines the core image manipulation
 * operations for the image processing application. It provides a contract
 * for the classes that implement it to interact with the graphical user
 * interface (GUI), handling image operations such as loading, saving,
 * applying effects (sepia, blur, sharpen, etc.), and adjusting various
 * image properties (e.g., brightness, compression, downscaling).
 * Implementing classes should provide the actual logic for each operation,
 * which is then invoked by the GUI based on user interactions.
 * Implementing this interface allows a clear, consistent way to handle
 * image processing features while keeping the user interface logic
 * encapsulated in the GUI classes.
 */
public interface Features {

  /**
   * Loads an image from the file system and displays it
   * on GUI.
   * This method retrieves the file path from the view,
   * checks if the image is loaded or not, and then constructs
   * a command to load the image. After the image is loaded,
   * it is displayed in the view.
   */
  void loadImage();

  /**
   * Saves an image to the specified file path.
   * This method checks if the image is loaded or not,
   * retrieves the save file path from the view,
   * and constructs a command to save the image.
   * If the save is successful, a success message is shown.
   *
   * @param imageName the name of the image to be saved
   */
  void saveImage(String imageName);

  /**
   * Applies the sepia effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the sepia effect in the view.
   *
   * @param imageName the name of the image to apply the sepia effect.
   */
  void applySepia(String imageName);

  /**
   * Applies the blur effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the blur effect in the view.
   *
   * @param imageName the name of the image to apply the blur effect.
   */
  void applyBlur(String imageName);

  /**
   * Applies the sharpen effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the sharpen effect in the view.
   *
   * @param imageName the name of the image to apply the sharpen effect.
   */
  void applySharp(String imageName);

  /**
   * Applies the horizontal-flip effect to the specified image
   * and display it on GUI.
   * This method checks if the image is loaded or not
   * and then applies the horizontal-flip operation on image.
   *
   * @param imageName the name of the image to apply the horizontal effect.
   */
  void applyHorizontalFlip(String imageName);

  /**
   * Applies the vertical-flip effect to the specified image
   * and display it on GUI.
   * This method checks if the image is loaded or not
   * and then applies the vertical-flip operation on image.
   *
   * @param imageName the name of the image to apply the vertical effect.
   */
  void applyVerticalFlip(String imageName);

  /**
   * Applies the color-correct effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the color-correct effect in the view.
   *
   * @param imageName the name of the image to apply the color-correct effect.
   */
  void applyColorCorrect(String imageName);

  /**
   * Applies the Luma-Component effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the luma effect in the view.
   *
   * @param imageName the name of the image to apply the luma effect.
   */
  void applyLuma(String imageName);

  /**
   * Applies the red-component effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the red-component effect in the view.
   *
   * @param imageName the name of the image to apply the red-component effect.
   */
  void applyRed(String imageName);

  /**
   * Applies the green-component effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the green-component effect in the view.
   *
   * @param imageName the name of the image to apply the green-component effect.
   */
  void applyGreen(String imageName);

  /**
   * Applies the blue-component effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the split preview for
   * the blue-component effect in the view.
   *
   * @param imageName the name of the image to apply the blue-component effect.
   */
  void applyBlue(String imageName);

  /**
   * Applies the levels-adjust effect to the specified image and
   * preview it on GUI.
   * This method checks if the image is loaded or not
   * and then triggers the levels-adjust preview panel for
   * the levels-adjust effect in the view.
   *
   * @param imageName the name of the image to apply the levels-adjust effect.
   */
  void applyLevelAdjust(String imageName);

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
   * @param b          the black point value for the adjustment
   * @param m          the mid-point value for the adjustment
   * @param w          the white point value for the adjustment
   * @param percentage the percentage to apply for the split by default the
   *                   value is 100.
   */
  void getLevelAdjust(String imageName, String b, String m, String w, String percentage);

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
  boolean generateLevelAdjust(String bValue, String mValue, String wValue, String imageName);

  /**
   * Applies compression to the specified image.
   * This method checks if the image is loaded or not
   * and then triggers the image compression pre-view
   * in the view.
   *
   * @param imageName the name of the image to be compressed
   */
  void applyCompress(String imageName);

  /**
   * Generates and applies compression to the specified
   * image based on the given percentage.
   * This method validates the compression percentage,
   * constructs the necessary command,
   * and applies the compression to the image.
   *
   * @param pValue    the percentage for image compression
   * @param imageName the name of the image to be compressed
   * @param checker   a boolean value to check if the preview or apply button is pressed.
   * @return true if the compression was applied successfully, false if the percentage is invalid
   */
  boolean generateCompress(String pValue, String imageName, boolean checker);

  /**
   * Applies brightness to the specified image.
   * This method checks if the image is loaded or not
   * and then triggers the image brightness pre-view
   * in the view.
   *
   * @param imageName the name of the image to be compressed
   */
  void applyBrightness(String imageName);

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
  boolean generateBright(String imageName, String value, boolean checker);

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
  void applySplit(String percentage, String commandName, String imageName);

  /**
   * Applies downscaling to the specified image.
   * This method checks if the image is loaded or not,
   * retrieves the image data, and triggers the downscaling
   * process preview in the view and passed the image's dimensions.
   *
   * @param imageName the name of the image to be downscaled
   */
  void applyDownScale(String imageName);

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
   * @return true if downscaling was applied successfully, false if the parameters are invalid
   */
  boolean generateDown(String imageName, String width, String height, boolean checker);

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
  void commandGenerator(String operation, String imageName);
}
