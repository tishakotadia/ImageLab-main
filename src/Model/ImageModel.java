package model;

/**
 * This class represents an image, so the class
 * contains all the variables and method needed to handle
 * an image. An image is a 3-d matrix of pixels.
 */

class ImageModel {
  private final int height;
  private final int width;
  private final int[][][] pixels;

  /**
   * Constructs an ImageModel object with the
   * specified height and width. Initializes the
   * pixel array store the RGB values.
   *
   * @param height height of the image.
   * @param width  width of the image.
   * @param matrix the matrix of pixel values.
   */
  public ImageModel(int height, int width, int[][][] matrix) {
    this.height = height;
    this.width = width;
    this.pixels = matrix;
  }

  /**
   * Method to obtain the height of the image.
   *
   * @return the height of the image.
   */
  protected int getHeight() {
    return this.height;
  }

  /**
   * Method to obtain the width of the image.
   *
   * @return the width of the image.
   */
  protected int getWidth() {
    return this.width;
  }

  /**
   * Method to obtain the Pixel Value at a specific coordinate
   * of the image.
   *
   * @param x the row of the array.
   * @param y the column of the array.
   * @param z the color channel.
   * @return the value of the specified pixel.
   */
  protected int getPixelValue(int x, int y, int z) {
    return this.pixels[x][y][z];
  }

  /**
   * Method to obtain the Pixel Matrix of the image.
   *
   * @return a 3-d array containing the pixel values of image.
   */
  protected int[][][] getPixelMatrix() {
    int[][][] n = new int[this.height][this.width][3];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        for (int k = 0; k < 3; k++) {
          n[i][j][k] = this.pixels[i][j][k];
        }
      }
    }
    return n;
  }

}
