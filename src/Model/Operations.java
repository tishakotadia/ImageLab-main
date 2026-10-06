package model;

/**
 * This interface represents the contract which
 * defines the operations that can be performed on images,
 * regardless of their specific types. The available methods include:
 * retrieving components, flipping, brightening, splitting,
 * combining, blurring, sharpening, and applying sepia filtering.
 */

interface Operations {

  /**
   * Loads an image from a 3D integer array representing pixel values
   * and stores it in new object of Image model.
   * In the 3-D Matrix :
   * The first dimension is the height of the image.
   * The second dimension is the width of the image.
   * The third dimension is the channel associated with the
   * image.
   *
   * @param matrix a 3D integer array containing pixel
   *               values for image.
   * @param name   the name under which the image will be
   *               stored in the image map.
   * @return true if operation done successfully, else false.
   */

  boolean loadImage(int[][][] matrix, String name);

  /**
   * Obtain the pixel matrix of the image whose name is
   * provided in the function parameter.
   *
   * @param name the name of the image whose pixel matrix
   *             if to be obtained.
   * @return a 3-D array representing the pixel values of image.
   */
  int[][][] saveImage(String name);

  /**
   * Checks whether an image with the specified name exists
   * in the image map.
   *
   * @param name the name of the image to check for
   *             in the image map.
   * @return true if the image exists; false otherwise.
   */
  boolean checkImage(String name);

  /**
   * Obtain the specific color component from the specified image.
   * The color component can be either Red, Green or Blue.
   * In the resulting image, all three channels for each pixel will have the
   * same value, which corresponds to the value of the selected color component
   * from the original image.
   *
   * @param currentImage the name of the original image from which color
   *                     component is to be obtained.
   * @param newImage     the name of the new image created from the specified
   *                     color component.
   * @param channel      the color component which is to be extracted.
   *                     0 is for Red; 1 is for Green; 2 is for Blue.
   * @return true if operation done successfully, else false.
   */
  boolean getColorComponent(String currentImage, String newImage, int channel);

  /**
   * Obtain the specific brightness component from the specified image.
   * The brightness component can be value, luma or intensity.
   * Value is the max value of the three component for each pixel.
   * Intensity is the average of three component for each pixel.
   * Luma is weighted sum of three component for each pixel.
   *
   * @param currentImage the name of the original image  from which
   *                     specified brightness component is to be
   *                     obtained.
   * @param newImage     the name of the new image created from specified
   *                     brightness component.
   * @param handle       specifies which brightness component to use.
   *                     Can be value-component; luma-component;
   *                     intensity-component.
   * @return true if operation done successfully, else false.
   */
  boolean getBrightnessComponent(String currentImage, String newImage, String handle);

  /**
   * Perform horizontal flip of the specified image and create a new image
   * with flipped pixel values.
   * Horizontal flip is basically the mirror image of the original image.
   *
   * @param currentImage the name of the original image which is to be
   *                     horizontally flipped.
   * @param newImage     the name of the new image created by horizontally
   *                     flipping the original image.
   * @return true if operation done successfully, else false.
   */
  boolean horizontalFlip(String currentImage, String newImage);

  /**
   * Perform vertical flip of the specified image and create a new image
   * with flipped pixel values.
   * Vertical flip is basically making an image up-side down.
   *
   * @param currentImage the name of the original image which is to be
   *                     vertically flipped.
   * @param newImage     the name of the new image created by vertically
   *                     flipping the original image.
   * @return true if operation done successfully, else false.
   */
  boolean verticalFlip(String currentImage, String newImage);

  /**
   * Brightens the specified image by adding the pixel's color value
   * to a specified value.
   * The value can be positive for brightening and can be negative
   * for darkening the image.
   *
   * @param currentImage the name of the original image whose pixels
   *                     value is to be changed.
   * @param newImage     the name of the new image obtained by changing
   *                     the pixel value in the original image with the
   *                     intensity value provided.
   * @param intensity    the value which is to be added to each pixel. Can
   *                     be positive or negative.
   * @return true if operation done successfully, else false.
   */
  boolean brighten(String currentImage, String newImage, int intensity);

  /**
   * Split the specified image into its constituent color component images.
   * So extracting a red, green and blue channel images from the specified
   * image.
   *
   * @param currentImage  the name of the original image which is to be split
   *                      into the constituent color component channels.
   * @param newRedImage   the new image obtained from the red-channel of the
   *                      original image.
   * @param newGreenImage the new image obtained from the green-channel of the
   *                      original image.
   * @param newBlueImage  the new image obtained from the blue-channel of the
   *                      original image.
   * @return true if operation done successfully, else false.
   */
  boolean splitRGB(String currentImage, String newRedImage, String newGreenImage,
                   String newBlueImage);

  /**
   * Combine the red, green and blue color component from three specified images
   * to obtain a new image with the combined RGB value.
   * So in the new image the red channel has all pixel value from any channel in the
   * red image. The green channel has all pixel value from any channel of the green image.
   * The blue channel has all pixel value from any channel of the blue image.
   *
   * @param redImage   the name of the image which makes red channel.
   * @param greenImage the name of the image which makes green channel.
   * @param blueImage  the name of the image which makes blue channel.
   * @param newImage   the name of the new image formed from combination
   *                   of the three images.
   * @return true if operation done successfully, else false.
   * @throws IllegalArgumentException if the provided images do not have same dimensions.
   */

  boolean combineRGB(String redImage, String greenImage, String blueImage,
                     String newImage) throws IllegalArgumentException;

  /**
   * Blur the specified image by applying a suitable kernel over the
   * entire image. Gaussian blue filer is used, after applying the filer
   * a new blurred image is obtained.
   *
   * @param currentImage the name of the original image which is to be
   *                     blurred.
   * @param newImage     the name of the new image obtained after blurring
   *                     the original image.
   * @return true if operation done successfully, else false.
   */
  boolean blur(String currentImage, String newImage);

  /**
   * Sharpen the specified image by applying a suitable kernel over the
   * entire image. Sharpens the edges of the image.
   *
   * @param currentImage the name of the original image which is to be
   *                     Sharpen.
   * @param newImage     the name of the new image obtained after sharpening
   *                     the original image.
   * @return true if operation done successfully, else false.
   */
  boolean sharpen(String currentImage, String newImage);

  /**
   * Applying a Sepia color transformation on the image to give it a
   * characteristic reddish brown tone. By applying this the image tends
   * to appear as the photograph taken in the 19th and early 20th century.
   *
   * @param currentImage the name of the original image on which the sepia
   *                     color transformation is to be applied.
   * @param newImage     the name of the new image obtained after applying
   *                     sepia transformation on th original image.
   * @return true if operation done successfully, else false.
   */
  boolean sepia(String currentImage, String newImage);

}
