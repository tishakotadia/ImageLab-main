package model;

/**
 * New version of the operations interface that introduces new methods
 * provided by the ImageProcessingProgram.
 * It extends the older version Operations interface.
 */
public interface OperationsV2 extends Operations {

  /**
   * Compresses a given image by a specified percentage using
   * the Haar Wavelet Transform algorithm.
   * This method extracts each color channel (red, green, blue)
   * from the image, applies the Haar Wavelet Transform to
   * compress each channel, and combines the channels to generate
   * a new compressed image.
   *
   * @param currentImageName the name of the original image to be compressed.
   * @param newImageName     the name to assign to the new compressed image.
   * @param percentage       the percentage by which the image is to be compressed (0-100).
   *                         A higher percentage results in greater compression and loss.
   * @return true if operation done successfully, else false.
   */
  boolean compressImage(String currentImageName, String newImageName, double percentage);

  /**
   * Method to visualize a histogram graph for the provided image.
   * It generates a line graph for all three red, green, and blue channels.
   *
   * @param currentImageName name of the image for which the histogram is to be generated.
   * @param newImageName     name for storing the generated histogram graph.
   * @return true if the operation executed successfully, else false.
   */
  boolean histogram(String currentImageName, String newImageName);

  /**
   * Method to apply color correction on a given image.
   * This method adjusts the color balance of an image by normalizing
   * the RGB channel intensities based on their peak frequencies.
   *
   * @param currentImageName name of the image to be color corrected.
   * @param newImageName     name to be assigned to the color-corrected image.
   * @return true if the operation executed successfully, else false.
   */
  boolean colorCorrection(String currentImageName, String newImageName);

  /**
   * Method to apply levels adjustment operation on the given image.
   * This method modifies pixel intensity levels of the image based on
   * specified parameters to enhance brightness, contrast, and
   * intensity distribution.
   *
   * @param currentImageName name of the image to be level adjusted.
   * @param newImageName     name to be assigned to the level-adjusted image.
   * @param b                black point for intensity adjustment.
   * @param m                mid-tone value for adjusting contrast.
   * @param w                white point for intensity adjustment.
   * @return true if the operation executed successfully, else false.
   */
  boolean levelAdjustment(String currentImageName,
                          String newImageName, int b, int m, int w);

  /**
   * Method to apply a split view of an operation on the given image.
   * This method creates a new image that displays a split preview of the
   * specified operation. The supported operations include:
   * - Blur
   * - Sharpen
   * - Sepia
   * - Red/Green/Blue components
   * - Value/Luma/Intensity components
   * - Color Correction
   * - Levels Adjustment
   *
   * @param currentImageName name of the image to be viewed in split preview.
   * @param newImageName     name to be assigned to the new image in the split preview.
   * @param percentage       percentage of the original image width to be
   *                         included in the split view.
   */
  void splitPreview(String currentImageName, String newImageName, double percentage);

  /**
   * Helper method to merge an operated image and its original part.
   * This method combines a temporary image (the operated one) with
   * the original image to create a new image that retains parts
   * of both. The width of the new image is determined by the
   * width of the temporary image.
   *
   * @param currentImageName name of the current image to be merged.
   * @param temp             name of the operated image to be merged.
   * @param newImageName     name to be assigned to the resulting merged image.
   * @return true if the merging operation executed successfully, else false.
   */
  boolean regain(String currentImageName, String temp, String newImageName);
}