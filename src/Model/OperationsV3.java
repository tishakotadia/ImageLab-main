package model;

/**
 * The OperationsV3 interface extends OperationsV2 and adds new
 * methods for advanced image processing tasks.
 * These include:
 * 1. Masking an image based on a mask image and a temporary image.
 * 2. Downscaling an image to specified dimensions using interpolation.
 */
public interface OperationsV3 extends OperationsV2 {

  /**
   * Applies a mask to the current image. Pixels of the
   * current image are replaced with
   * pixels from a temporary image wherever the
   * corresponding mask image pixels are black
   * (R=0, G=0, B=0). The result is saved as a new image.
   *
   * @param currentImageName the name of the current image to
   *                         which the mask will be applied
   * @param temp             the name of the temporary image providing
   *                         replacement pixel values
   * @param maskImageName    the name of the mask image used to
   *                         determine which pixels to replace
   * @param newImageName     the name under which the
   *                         resulting image will be saved
   * @return true if the masking operation is successful
   * @throws IllegalArgumentException if the dimensions of
   *                                  the mask and the current image differ
   */
  boolean mask(String currentImageName, String temp, String maskImageName,
               String newImageName);

  /**
   * Downscales the current image to a specified target height
   * and width using bi-linear interpolation.
   * The resulting image is saved under a new name.
   *
   * @param currentImageName the name of the current image to be downscaled
   * @param targetHeight     the target height of the downscaled image
   * @param targetWidth      the target width of the downscaled image
   * @param newImageName     the name under which the downscaled image will be saved
   * @throws IllegalArgumentException if targetHeight or
   *                                  targetWidth is less than or equal to zero
   */
  void downScale(String currentImageName, int targetHeight, int targetWidth,
                 String newImageName);

}
