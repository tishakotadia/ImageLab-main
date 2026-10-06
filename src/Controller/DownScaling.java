package controller;

import model.OperationsV3;

/**
 * The DownScaling class represents a command that performs downscaling
 * of an image to a specified width and height.
 * It verifies that the new dimensions
 * are valid and smaller than the current image's
 * dimensions before performing the operation.
 */

class DownScaling extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final int newImageWidth;
  private final int newImageHeight;

  /**
   * Constructs a DownScaling command object that will perform downscaling
   * of the specified image to the new dimensions.
   *
   * @param currentImageName the name of the image to be downscaled
   * @param newImageHeight   the target height for the downscaled image
   * @param newImageWidth    the target width for the downscaled image
   * @param newImageName     the name of the downscaled image after the operation
   */
  DownScaling(String currentImageName, int newImageHeight, int newImageWidth,
              String newImageName) {
    this.currentImageName = currentImageName;
    this.newImageHeight = newImageHeight;
    this.newImageWidth = newImageWidth;
    this.newImageName = newImageName;
  }


  /**
   * Executes the downscaling operation on the current image.
   * The method first checks if the new image dimensions
   * are valid and smaller than the current image's dimensions.
   * If the dimensions are valid, the image is downscaled to
   * the specified width and height.
   *
   * @param operations the OperationsV3 instance used to
   *                   perform the downscaling operation.
   * @return true if the operation was successfully executed; false otherwise.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    int[][][] currentImage = operations.saveImage(this.currentImageName);
    int height = currentImage.length;
    int width = currentImage[0].length;
    if ((height < newImageHeight || width < newImageWidth) || (newImageHeight <= 0 ||
            newImageWidth <= 0)) {
      return false;
    }
    operations.downScale(this.currentImageName, this.newImageHeight, this.newImageWidth,
            this.newImageName);
    return true;
  }
}
