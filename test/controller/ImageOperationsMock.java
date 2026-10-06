package controller;

import model.OperationsV3;

/**
 * This is a mock class of the ImageOperationsV3 class.
 * The purpose of this class is to provide mock implementations
 * of the various methods defined in the ImageOperationV3 class.
 * This class implements the OperationsV3 interface.
 */
public class ImageOperationsMock implements OperationsV3 {
  private final StringBuilder log;

  /**
   * Constructor for the mock class, initialises a log.
   *
   * @param log string builder.
   */

  public ImageOperationsMock(StringBuilder log) {
    this.log = log;
  }

  /**
   * Mock operation for compress method.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @param percentage       percentage of compression to be done.
   * @return true.
   */
  @Override
  public boolean compressImage(String currentImageName,
                               String newImageName, double percentage) {
    log.append("\nCompress ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  /**
   * Mock operation for histogram method.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @return true.
   */
  @Override
  public boolean histogram(String currentImageName, String newImageName) {
    log.append("\nHistogram ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  /**
   * Mock operation for colorCorrection method.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @return true.
   */
  @Override
  public boolean colorCorrection(String currentImageName, String newImageName) {
    log.append("\nColorCorrection ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  /**
   * Mock operation for levelAdjustment method.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @param b                b value.
   * @param m                m value.
   * @param w                w value.
   * @return true.
   */
  @Override
  public boolean levelAdjustment(String currentImageName, String newImageName,
                                 int b, int m, int w) {
    log.append("\nLevelAdjustment ").append(currentImageName).append(" to ")
            .append(newImageName).append(" with ").append(b).append(" ")
            .append(m).append(" ").append(w);
    return true;
  }

  /**
   * Mock operation for splitPreview method.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @param percentage       percentage of image to be compressed.
   */
  @Override
  public void splitPreview(String currentImageName, String newImageName,
                           double percentage) {
    log.append("\nSplitPreview on ").append(currentImageName).append(" with ").append(percentage);
  }

  /**
   * Mock operation for regain method.
   *
   * @param currentImageName name of the image to be compressed.
   * @param newImageName     name of the new compressed image.
   * @return true.
   */
  @Override
  public boolean regain(String currentImageName, String temp, String newImageName) {
    log.append("\nRegain ").append(currentImageName).append(" to ").append(newImageName);
    return true;
  }

  /**
   * Mock operation for loadImage method.
   *
   * @param matrix image matrix.
   * @param name   image name.
   * @return true.
   */
  @Override
  public boolean loadImage(int[][][] matrix, String name) {
    log.append("Load image ").append(name);
    return true;
  }

  /**
   * Mock operation for saveImage method.
   *
   * @param name Image name.
   * @return 3d matrix.
   */
  @Override
  public int[][][] saveImage(String name) {
    log.append("\nSave image ").append(name);
    return new int[3][3][3];
  }

  /**
   * Mock operation for checkImage method.
   *
   * @param name the name of the image to check for
   *             in the image map.
   * @return true.
   */
  @Override
  public boolean checkImage(String name) {
    return true;
  }

  /**
   * Mock operation for getColorComponent method.
   *
   * @param currentImage the name of the original image from which color
   *                     component is to be obtained.
   * @param newImage     the name of the new image created from the specified
   *                     color component.
   * @param channel      the color component which is to be extracted.
   *                     0 is for Red; 1 is for Green; 2 is for Blue.
   * @return true.
   */
  @Override
  public boolean getColorComponent(String currentImage, String newImage, int channel) {

    log.append("\nGetColorComponent ").append(currentImage).append(" to ")
            .append(newImage).append(" ").append(channel);
    return true;
  }

  /**
   * Mock operation for getBrightnessComponent method.
   *
   * @param currentImage the name of the original image  from which
   *                     specified brightness component is to be
   *                     obtained.
   * @param newImage     the name of the new image created from specified
   *                     brightness component.
   * @param handle       specifies which brightness component to use.
   *                     Can be value-component; luma-component;
   *                     intensity-component.
   * @return true.
   */
  @Override
  public boolean getBrightnessComponent(String currentImage, String newImage, String handle) {
    log.append("\nGetBrightnessComponent ").append(currentImage).append(" to ")
            .append(newImage).append(" ").append(handle);
    return true;
  }

  /**
   * Mock operation for horizontalFlip method.
   *
   * @param currentImage the name of the original image which is to be
   *                     horizontally flipped.
   * @param newImage     the name of the new image created by horizontally
   *                     flipping the original image.
   * @return true.
   */
  @Override
  public boolean horizontalFlip(String currentImage, String newImage) {
    log.append("\nHorizontalFlip ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  /**
   * Mock operation for verticalFlip method.
   *
   * @param currentImage the name of the original image which is to be
   *                     vertically flipped.
   * @param newImage     the name of the new image created by vertically
   *                     flipping the original image.
   * @return true.
   */
  @Override
  public boolean verticalFlip(String currentImage, String newImage) {
    log.append("\nVerticalFlip ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  /**
   * Mock operation for brighten method.
   *
   * @param currentImage the name of the original image whose pixels
   *                     value is to be changed.
   * @param newImage     the name of the new image obtained by changing
   *                     the pixel value in the original image with the
   *                     intensity value provided.
   * @param intensity    the value which is to be added to each pixel. Can
   *                     be positive or negative.
   * @return true.
   */
  @Override
  public boolean brighten(String currentImage, String newImage, int intensity) {
    log.append("\nBrighten ").append(currentImage).append(" to ").
            append(newImage).append(" ").append(intensity);
    return true;
  }

  /**
   * Mock operation for splitRGB method.
   *
   * @param currentImage  the name of the original image which is to be split
   *                      into the constituent color component channels.
   * @param newRedImage   the new image obtained from the red-channel of the
   *                      original image.
   * @param newGreenImage the new image obtained from the green-channel of the
   *                      original image.
   * @param newBlueImage  the new image obtained from the blue-channel of the
   *                      original image.
   * @return true.
   */
  @Override
  public boolean splitRGB(String currentImage, String newRedImage,
                          String newGreenImage, String newBlueImage) {
    log.append("\nSplit RBG ").append(currentImage).append(" to ").append(newRedImage)
            .append(" ").append(newGreenImage).append(" ").append(newBlueImage);
    return true;
  }

  /**
   * Mock operation for combineRGB method.
   *
   * @param redImage   the name of the image which makes red channel.
   * @param greenImage the name of the image which makes green channel.
   * @param blueImage  the name of the image which makes blue channel.
   * @param newImage   the name of the new image formed from combination
   *                   of the three images.
   * @return true.
   * @throws IllegalArgumentException when illegal values are passed.
   */
  @Override
  public boolean combineRGB(String redImage, String greenImage,
                            String blueImage, String newImage) throws IllegalArgumentException {
    log.append("\nCombineRGB ").append(redImage).append(" ").append(greenImage).append(" ")
            .append(blueImage).append(" to ").append(newImage);
    return true;
  }

  /**
   * Mock operation for blur method.
   *
   * @param currentImage the name of the original image which is to be
   *                     blurred.
   * @param newImage     the name of the new image obtained after blurring
   *                     the original image.
   * @return true
   */
  @Override
  public boolean blur(String currentImage, String newImage) {
    log.append("\nBlur ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  /**
   * Mock operation for sharpen method.
   *
   * @param currentImage the name of the original image which is to be
   *                     Sharpen.
   * @param newImage     the name of the new image obtained after sharpening
   *                     the original image.
   * @return true
   */
  @Override
  public boolean sharpen(String currentImage, String newImage) {
    log.append("\nSharpen ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  /**
   * Mock operation for sepia method.
   *
   * @param currentImage the name of the original image on which the sepia
   *                     color transformation is to be applied.
   * @param newImage     the name of the new image obtained after applying
   *                     sepia transformation on th original image.
   * @return true.
   */
  @Override
  public boolean sepia(String currentImage, String newImage) {
    log.append("\nSepia ").append(currentImage).append(" to ").append(newImage);
    return true;
  }

  /**
   * Mock Operation for the mask operation.
   *
   * @param currentImageName the name of original image on which mask
   *                         is to be applied.
   * @param temp             the name of the temporary image
   *                         name on which operation is applied.
   * @param maskImageName    the name of the mask Image Name.
   * @param newImageName     the name of the new image after the masking is applied.
   * @return true.
   */
  @Override
  public boolean mask(String currentImageName, String temp,
                      String maskImageName, String newImageName) {
    log.append("\nMask on ").append(currentImageName).append(" to get ").append(newImageName);
    return false;
  }

  /**
   * Mock operation for the Downscaling operation.
   *
   * @param currentImageName the name of original image on which mask
   *                         is to be applied.
   * @param targetHeight     the height of resultant image.
   * @param targetWidth      the width of the resultant image.
   * @param newImageName     the name of the new image obtained after down scaling
   *                         operations is applied.
   */
  @Override
  public void downScale(String currentImageName, int targetHeight,
                        int targetWidth, String newImageName) {
    log.append("\nDownScale ").append(currentImageName).append(" to ").append(targetHeight);
  }
}
