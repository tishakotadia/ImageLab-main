package controller;

import java.awt.image.BufferedImage;
import java.io.File;

import view.IView;

/**
 * This is a mock class of the GuiView class.
 * The purpose of this class is to provide mock implementations
 * of the various methods defined in the GuiView class.
 * This class implements the IView interface.
 */
public class GuiViewMock implements IView {

  private final StringBuilder log;

  /**
   * Constructor for the mock class, initialises a log.
   *
   * @param log string builder.
   */
  public GuiViewMock(StringBuilder log) {
    this.log = log;
  }

  /**
   * Adds the given features to the action listeners
   * of the buttons in the application.
   *
   * @param features the Features object
   */
  @Override
  public void addFeature(Features features) {
    log.append("Features object added to the action listeners of the buttons");
  }

  /**
   * Retrieves the file path of the image to be processed.
   *
   * @return a File object representing the image file path
   */
  @Override
  public File getFilePath() {
    log.append("\ngetFilePath called successfully.");
    return new File("images/manhattan-small.png");
  }

  /**
   * Retrieves the file path where the processed image will be saved.
   *
   * @return a File object representing the save file path
   */
  @Override
  public File getSaveFilePath() {
    log.append("\ngetSaveFilePath called successfully.");
    return new File("images/dummyTest.png");
  }

  /**
   * Checks if there is an image available for processing.
   *
   * @return true if an image is present, false otherwise
   */
  @Override
  public boolean checkImage() {
    log.append("checkImage called successfully.");
    return true;
  }

  /**
   * Displays the given image in the application.
   *
   * @param name  the name of the image
   * @param image the  BufferedImage object to be displayed
   */
  @Override
  public void showImage(String name, BufferedImage image) {
    log.append("\nImage ").append(name).append(" displayed correctly.");
  }

  /**
   * Displays the histogram of the given image.
   *
   * @param image the BufferedImage object whose histogram is to be displayed
   */
  @Override
  public void showHistogram(BufferedImage image) {
    log.append("\nHistogram of image showed correctly");

  }

  /**
   * Handles the preview functionality for a given command in split view.
   *
   * @param commandName the name of the command to be previewed
   * @param features    the Features object
   * @return true if the preview was successful, false otherwise
   */
  @Override
  public boolean splitPreview(String commandName, Features features) {
    log.append("\n").append(commandName).append(" preview called successfully.");
    return true;
  }

  /**
   * Displays the split version of the image.
   *
   * @param image the BufferedImage object to be displayed
   */
  @Override
  public void showSplitImage(BufferedImage image) {
    log.append("\nSplit Image displayed successfully.");
  }

  /**
   * Displays a success message when the image is saved.
   */
  @Override
  public void showSaveSuccess() {
    log.append("\nSuccess Message Displayed.");
  }

  /**
   * Handles the functionality to adjust image levels in a split view.
   *
   * @param features the Features object
   */
  @Override
  public void levelAdjust(Features features) {
    log.append("\nLevel-Adjustment split frame called successfully.");
  }


  /**
   * Displays an error message indicating that no image is present for processing.
   */
  @Override
  public void showImageNotPresent() {
    log.append("\nImage not present error displayed successfully.");
  }

  /**
   * Handles the functionality to compress an image in a split view.
   *
   * @param features the Features object
   */
  @Override
  public void compressImage(Features features) {
    log.append("\nCompress split frame called successfully.");
  }

  /**
   * Handles the functionality to adjust the brightness of an image in a split view.
   *
   * @param features the Features object
   */
  @Override
  public void brightness(Features features) {
    log.append("\nBrightness split frame called successfully.");
  }

  /**
   * Displays an error message with the specified details.
   *
   * @param error the error message to be displayed
   */
  @Override
  public void showError(String error) {
    log.append("\nError message displayed successfully.\n")
            .append(error);
  }

  /**
   * Handles the functionality to downscale an image based on the specified dimensions.
   *
   * @param height   the target height for downscaling
   * @param width    the target width for downscaling
   * @param features the Features object
   */
  @Override
  public void downScale(int height, int width, Features features) {
    log.append("\nDownscale split frame called successfully.").append("\n")
            .append("with current height and width are ").append(height).append(" ")
            .append(width);
  }
}
