package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This class implements the new methods needed to be implemented by the program.
 * It extends the older class and implements the new OperationsV2 interface.
 * It introduces the following new methods.
 * Compression.
 * Histogram Visualization.
 * Color Correction.
 * Levels Adjustment.
 * Split Preview.
 */

public class ImageOperationsV2 extends ImageOperations implements OperationsV2 {

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
  @Override
  public boolean compressImage(String currentImageName, String newImageName, double percentage) {
    ImageModel imageOld = this.imageMap.get(currentImageName);
    int height = imageOld.getHeight();
    int width = imageOld.getWidth();
    int paddingSize = this.padding(Math.max(height, width));
    double[][] red = new double[paddingSize][paddingSize];
    double[][] green = new double[paddingSize][paddingSize];
    double[][] blue = new double[paddingSize][paddingSize];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red[i][j] = imageOld.getPixelValue(i, j, 0);
        green[i][j] = imageOld.getPixelValue(i, j, 1);
        blue[i][j] = imageOld.getPixelValue(i, j, 2);
      }
    }
    red = this.pipeline(red, paddingSize, percentage);
    green = this.pipeline(green, paddingSize, percentage);
    blue = this.pipeline(blue, paddingSize, percentage);
    this.combineChannel(imageOld, newImageName, red, green, blue);
    return true;
  }

  /**
   * Combines the transformed color channels (red, green, blue)
   * and generates a new compressed image.
   * Sets the pixel values of the new image based on the
   * compressed color channels.
   *
   * @param old          the original ImageModel object containing
   *                     the uncompressed image data.
   * @param newImageName the name of the new compressed image.
   * @param red          2D array representing the compressed red color channel.
   * @param green        2D array representing the compressed green color channel.
   * @param blue         2D array representing the compressed blue color channel.
   */
  private void combineChannel(ImageModel old, String newImageName, double[][] red,
                              double[][] green, double[][] blue) {
    int height = old.getHeight();
    int width = old.getWidth();
    int[][][] arr = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.setPixelValue(i, j, 0, (int) red[i][j], arr);
        this.setPixelValue(i, j, 1, (int) green[i][j], arr);
        this.setPixelValue(i, j, 2, (int) blue[i][j], arr);
      }
    }
    ImageModel imageNew = this.getNewImageModel(old, arr);
    this.imageMap.put(newImageName, imageNew);
  }

  /**
   * Processes a given color channel array by applying the
   * Haar Wavelet Transform to compress it.
   * The compression level is determined by the specified percentage.
   *
   * @param matrix     2D array representing a single color channel of the image.
   * @param size       the padded size of the color channel array.
   * @param percentage the percentage by which the color channel
   *                   data is to be compressed.
   * @return the transformed and compressed color channel array.
   */
  protected double[][] pipeline(double[][] matrix, int size, double percentage) {
    matrix = this.haar(matrix, size);
    matrix = this.compress(matrix, percentage);
    matrix = this.inverseHarr(matrix, size);
    return matrix;
  }

  /**
   * Performs the inverse Haar Wavelet Transform on a given 2D matrix.
   * This method iteratively applies the inverse transform on columns and rows
   * to reconstruct the original data from a compressed state. The transform
   * progresses with increasing resolution until it covers the full matrix size.
   *
   * @param matrix the 2D matrix representing a compressed color channel.
   * @param size   the dimension of the matrix (assumed to be a power of 2).
   * @return the matrix after the inverse Haar transform.
   */
  protected double[][] inverseHarr(double[][] matrix, int size) {
    int c = 2;
    while (c <= size) {
      matrix = this.inverseTransformColumn(matrix, c);
      matrix = this.inverseTransformRow(matrix, c);
      c = c * 2;
    }
    return matrix;
  }

  /**
   * Applies the inverse Haar Wavelet Transform
   * to each row of the specified matrix.
   * This method reconstructs the original row values
   * by separating the average and
   * difference components and computing each element
   * based on the current resolution (c).
   *
   * @param matrix the 2D matrix on which the inverse transform
   *               is to be applied row by row.
   * @param c      the current resolution level.
   * @return the matrix with its rows transformed.
   */
  protected double[][] inverseTransformRow(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c / 2; j++) {
        avg.add((matrix[i][j] + matrix[i][j + (c / 2)]) / Math.sqrt(2.0));
        diff.add((matrix[i][j] - matrix[i][j + (c / 2)]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < c; j = j + 2) {
        matrix[i][j] = avg.get(j / 2);
        matrix[i][j + 1] = diff.get(j / 2);
      }
    }
    return matrix;
  }

  /**
   * Applies the inverse Haar Wavelet Transform
   * to each column of the specified matrix.
   * This method reconstructs the original column values
   * by separating the average and
   * difference components and computing each element
   * based on the current resolution (c).
   *
   * @param matrix the 2D matrix on which the inverse transform is
   *               to be applied column by column.
   * @param c      the current resolution level.
   * @return the matrix with its rows transformed.
   */
  protected double[][] inverseTransformColumn(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c / 2; j++) {
        avg.add((matrix[j][i] + matrix[j + (c / 2)][i]) / Math.sqrt(2.0));
        diff.add((matrix[j][i] - matrix[j + (c / 2)][i]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < c; j = j + 2) {
        matrix[j][i] = avg.get(j / 2);
        matrix[j + 1][i] = diff.get(j / 2);
      }
    }
    return matrix;
  }

  /**
   * Applies the Haar Wavelet Transform to a given matrix,
   * iteratively transforming rows and columns.
   * This method compresses an image channel by breaking down the data
   * into average and difference values, reducing data
   * resolution in each iteration.
   *
   * @param matrix the 2D matrix representing the
   *               color channel of the image.
   * @param size   the size of the matrix.
   * @return the matrix after the Haar Wavelet Transform is applied.
   */
  protected double[][] haar(double[][] matrix, int size) {
    int c = size;
    while (c > 1) {
      matrix = this.transformRow(matrix, c);
      matrix = this.transformColumn(matrix, c);
      c = c / 2;
    }
    return matrix;
  }

  /**
   * Applies the Haar Wavelet Transform
   * to each row of the specified matrix.
   * This method reconstructs the original row values
   * by separating the average and
   * difference components and computing each element
   * based on the current resolution (c).
   *
   * @param matrix the 2D matrix on which the transform
   *               is to be applied row by row.
   * @param c      the current resolution level.
   * @return the matrix with its rows transformed.
   */
  protected double[][] transformRow(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c; j = j + 2) {
        avg.add((matrix[i][j] + matrix[i][j + 1]) / (Math.sqrt(2.0)));
        diff.add((matrix[i][j] - matrix[i][j + 1]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < avg.size(); j++) {
        matrix[i][j] = avg.get(j);
        matrix[i][avg.size() + j] = diff.get(j);
      }
    }
    return matrix;
  }

  /**
   * Applies the inverse Haar Wavelet Transform
   * to each column of the specified matrix.
   * This method reconstructs the original column values
   * by separating the average and
   * difference components and computing each element
   * based on the current resolution (c).
   *
   * @param matrix the 2D matrix on which the transform
   *               is to be applied column by column.
   * @param c      the current resolution level.
   * @return the matrix with its rows transformed.
   */
  protected double[][] transformColumn(double[][] matrix, int c) {
    for (int i = 0; i < matrix.length; i++) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      for (int j = 0; j < c; j = j + 2) {
        avg.add((matrix[j][i] + matrix[j + 1][i]) / (Math.sqrt(2.0)));
        diff.add((matrix[j][i] - matrix[j + 1][i]) / (Math.sqrt(2.0)));
      }
      for (int j = 0; j < avg.size(); j++) {
        matrix[j][i] = ((avg.get(j)));
        matrix[j + avg.size()][i] = (diff.get(j));
      }
    }
    return matrix;
  }

  /**
   * Compresses the matrix by removing values below a
   * calculated threshold based on
   * the specified compression percentage.
   * The Haar coefficients that have less
   * significance are set to zero, achieving compression.
   *
   * @param matrix     the matrix containing color channel data after Haar Transform.
   * @param percentage the percentage of data to remove.
   * @return the matrix after compression is applied.
   */
  protected double[][] compress(double[][] matrix, double percentage) {
    double threshold = Double.MAX_VALUE;
    if (percentage == 100.0) {
      return this.applyThreshold(matrix, threshold);
    }
    List<Double> value = new ArrayList<>();
    for (double[] doubles : matrix) {
      for (int j = 0; j < matrix.length; j++) {
        double num = Math.round(doubles[j] * 1000.0) / 1000.0;
        value.add(Math.abs(num));
      }
    }
    List<Double> distinctPixel = new ArrayList<>(new HashSet<>(value));
    Collections.sort(distinctPixel);
    int index = (int) Math.round((distinctPixel.size() * (percentage / 100.0)));
    threshold = distinctPixel.get(index);
    return this.applyThreshold(matrix, threshold);
  }

  /**
   * Sets matrix values below the specified threshold to zero.
   * This step is part of the compression process.
   *
   * @param matrix    the matrix containing the color channel data.
   * @param threshold the threshold value; values below this are set to zero.
   * @return the matrix after the threshold is applied.
   */
  protected double[][] applyThreshold(double[][] matrix, double threshold) {
    return Arrays.stream(matrix).map(r -> Arrays.stream(r)
                    .map(v -> Math.abs(v) < threshold ? 0.0 : v).toArray())
            .toArray(double[][]::new);
  }

  /**
   * Computes the padding size for the image based
   * on the largest dimension (height or width).
   * Padding ensures that the image dimensions
   * are suitable for the Haar Wavelet Transform.
   *
   * @param value the maximum dimension (height or width)
   *              of the original image.
   * @return the padded size.
   */
  protected int padding(int value) {
    int size = 1;
    while (size < value) {
      size = size * 2;
    }
    return size;
  }

  /**
   * Method to visualize a histogram graph for the provided image.
   * It generates a line graph for all three red, green, and blue channels.
   *
   * @param currentImageName name of the image for which the histogram is to be generated.
   * @param newImageName     name for storing the generated histogram graph.
   * @return true if the operation executed successfully, else false.
   */
  @Override
  public boolean histogram(String currentImageName,
                           String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    int[][][] arr = new int[256][256][3];
    int[][][] matrix = oldImage.getPixelMatrix();
    this.setBackground(arr);

    int[] red = this.getFrequency(matrix, 0);
    int[] green = this.getFrequency(matrix, 1);
    int[] blue = this.getFrequency(matrix, 2);

    int redPeak = Arrays.stream(red).max().getAsInt();
    int greenPeak = Arrays.stream(green).max().getAsInt();
    int bluePeak = Arrays.stream(blue).max().getAsInt();

    int redMin = Arrays.stream(red).min().getAsInt();
    int greenMin = Arrays.stream(green).min().getAsInt();
    int blueMin = Arrays.stream(blue).min().getAsInt();

    int max_value = Math.max(Math.max(redPeak, greenPeak), bluePeak);
    int min_value = Math.min(Math.min(redMin, greenMin), blueMin);

    red = this.applyNormalize(red, max_value, min_value);
    green = this.applyNormalize(green, max_value, min_value);
    blue = this.applyNormalize(blue, max_value, min_value);

    this.connectPeak(arr, red, green, blue);
    ImageModel newImage = new ImageModel(256, 256, arr);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Helper method for `histogram` to generate a
   * background grid for the histogram canvas.
   * Draws grid lines every 17 pixels on the canvas for visual aid.
   *
   * @param arr the pixel matrix for the histogram image.
   */
  private void setBackground(int[][][] arr) {
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        if (i % (17) == 0 || j % (17) == 0) {
          this.setPixelValue(i, j, 0, 200, arr);
          this.setPixelValue(i, j, 1, 200, arr);
          this.setPixelValue(i, j, 2, 200, arr);
        } else {
          this.setPixelValue(i, j, 0, 255, arr);
          this.setPixelValue(i, j, 1, 255, arr);
          this.setPixelValue(i, j, 2, 255, arr);
        }
      }
    }
  }

  /**
   * Calculates the frequency of pixel intensity
   * values for a specific color channel.
   *
   * @param matrix  the pixel matrix of the image.
   * @param channel the color channel.
   * @return an array representing the frequency of each intensity (0-255).
   */
  protected int[] getFrequency(int[][][] matrix, int channel) {
    int[] n = new int[256];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        n[matrix[i][j][channel]] = n[matrix[i][j][channel]] + 1;
      }
    }
    return n;
  }

  /**
   * Normalizes the frequency array for a channel to
   * fit within the display range (0-255).
   *
   * @param arr      the frequency array for a color channel.
   * @param maxValue the maximum frequency value across channels.
   * @param minValue the minimum frequency value across channels.
   * @return a normalized frequency array.
   */
  protected int[] applyNormalize(int[] arr, int maxValue,
                                 int minValue) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = Math.round((float) ((arr[i] - minValue) * 255) / (maxValue - minValue));
    }
    return arr;
  }

  /**
   * Connects the peak points of the histogram for
   * each color channel to form a line graph.
   *
   * @param arr   the pixel matrix for the histogram image.
   * @param red   the normalized frequency array for the red channel.
   * @param green the normalized frequency array for the green channel.
   * @param blue  the normalized frequency array for the blue channel.
   */
  private void connectPeak(int[][][] arr, int[] red,
                           int[] green, int[] blue) {
    int prevRed = 255 - red[0];
    int prevGreen = 255 - green[0];
    int prevBlue = 255 - blue[0];
    for (int j = 1; j < 256; j++) {
      int currentRed = 255 - red[j];
      int currentGreen = 255 - green[j];
      int currentBlue = 255 - blue[j];
      this.drawLine(prevRed, currentRed, arr, j, new int[]{255, 0, 0});
      this.drawLine(prevGreen, currentGreen, arr, j, new int[]{0, 255, 0});
      this.drawLine(prevBlue, currentBlue, arr, j, new int[]{0, 0, 255});
      prevRed = currentRed;
      prevGreen = currentGreen;
      prevBlue = currentBlue;
    }

  }

  /**
   * Draws a vertical line segment between two points
   * on the histogram graph.
   *
   * @param start the starting y-coordinate.
   * @param end   the ending y-coordinate.
   * @param arr   the pixel matrix for the histogram.
   * @param index the x-coordinate for the line.
   * @param rgb   the color of the line (RGB array).
   */
  private void drawLine(int start, int end, int[][][] arr,
                        int index, int[] rgb) {
    if (start < end) {
      for (int i = start; i <= end; i++) {
        this.setPixelValue(i, index, 0, rgb[0], arr);
        this.setPixelValue(i, index, 1, rgb[1], arr);
        this.setPixelValue(i, index, 2, rgb[2], arr);
      }
    } else {
      for (int i = start; i >= end; i--) {
        this.setPixelValue(i, index, 0, rgb[0], arr);
        this.setPixelValue(i, index, 1, rgb[1], arr);
        this.setPixelValue(i, index, 2, rgb[2], arr);
      }
    }
  }

  /**
   * Method to apply color correction on a given image.
   * This method adjusts the color balance of an image by normalizing
   * the RGB channel intensities based on their peak frequencies.
   *
   * @param currentImageName name of the image to be color corrected.
   * @param newImageName     name to be assigned to the color-corrected image.
   * @return true if the operation executed successfully, else false.
   */
  @Override
  public boolean colorCorrection(String currentImageName,
                                 String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    int[][][] arr = new int[oldImage.getHeight()][oldImage.getWidth()][3];
    int[][][] matrix = oldImage.getPixelMatrix();
    int[] red = this.getFrequency(matrix, 0);
    int[] green = this.getFrequency(matrix, 1);
    int[] blue = this.getFrequency(matrix, 2);
    int redPeak = this.getPeak(red);
    int greenPeak = this.getPeak(green);
    int bluePeak = this.getPeak(blue);
    int averagePeak = (redPeak + bluePeak + greenPeak) / 3;
    this.setCorrectedValue(arr, matrix, averagePeak, redPeak, greenPeak, bluePeak);
    ImageModel newImage = this.getNewImageModel(oldImage, arr);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Method to find the peak intensity value of a given color channel.
   * This peak is defined as the intensity level with the highest frequency
   * within the specified range (10 to 245) to reduce the influence of noise.
   *
   * @param arr array representing the frequency distribution of
   *            intensity levels for a color channel.
   * @return the intensity level that has the highest frequency.
   */
  protected int getPeak(int[] arr) {
    int value = 0;
    int index = 0;
    for (int i = 10; i <= 245; i++) {
      if (arr[i] > value) {
        value = arr[i];
        index = i;
      }
    }
    return index;
  }

  /**
   * Method to set the color-corrected values in the new image matrix.
   * Adjusts each pixel's RGB values based on the difference between the
   * channel's peak and the average peak, effectively balancing color intensities.
   *
   * @param arr    the matrix to store the color-corrected pixel values.
   * @param matrix original image matrix containing unadjusted pixel values.
   * @param avg    the calculated average peak intensity across all channels.
   * @param red    peak intensity value for the red channel.
   * @param green  peak intensity value for the green channel.
   * @param blue   peak intensity value for the blue channel.
   */
  private void setCorrectedValue(int[][][] arr, int[][][] matrix, int avg,
                                 int red, int green, int blue) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        int red_value = matrix[i][j][0] + (avg - red);
        int green_value = matrix[i][j][1] + (avg - green);
        int blue_value = matrix[i][j][2] + (avg - blue);
        this.setPixelValue(i, j, 0, red_value, arr);
        this.setPixelValue(i, j, 1, green_value, arr);
        this.setPixelValue(i, j, 2, blue_value, arr);
      }
    }
  }

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
  @Override
  public boolean levelAdjustment(String currentImageName, String newImageName,
                                 int b, int m, int w) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    int[][][] arr = new int[oldImage.getHeight()][oldImage.getWidth()][3];
    double[] coeff = this.getCoefficient(b, m, w);
    this.applyAdjustment(arr, oldImage, coeff);
    ImageModel newImage = this.getNewImageModel(oldImage, arr);
    this.imageMap.put(newImageName, newImage);
    return true;
  }

  /**
   * Helper method to calculate the coefficients
   * required for level adjustment.
   * These coefficients, based on the black,
   * mid-tone, and white values,
   * are used to apply a quadratic transformation
   * to the pixel intensities.
   *
   * @param b black point intensity value.
   * @param m mid-tone intensity value.
   * @param w white point intensity value.
   * @return array storing the computed coefficients [Aa, Ab, Ac].
   */
  protected double[] getCoefficient(int b, int m, int w) {
    int bSquare = b * b;
    int mSquare = m * m;
    int wSquare = w * w;
    int a = bSquare * (m - w) - b * (mSquare - wSquare) + w * m * (m - w);
    double aA = -b * (128 - 255) + 128 * w - 255 * m;
    double bA = bSquare * (128 - 255) + 255 * mSquare - 128 * wSquare;
    double cA = bSquare * (255 * m - 128 * w) - b * (255 * mSquare - 128 * wSquare);
    return new double[]{aA / a, bA / a, cA / a};
  }

  /**
   * Helper method to apply the adjusted pixel
   * values to a new image matrix.
   * This method computes the new pixel values for the
   * image based on the
   * calculated coefficients and updates the pixel
   * matrix accordingly.
   *
   * @param arr   the matrix to store the adjusted pixel
   *              values of the image.
   * @param o     the existing ImageModel whose pixel
   *              values are to be adjusted.
   * @param coeff array of coefficients used for adjusting pixel values.
   */
  private void applyAdjustment(int[][][] arr, ImageModel o, double[] coeff) {
    for (int i = 0; i < o.getHeight(); i++) {
      for (int j = 0; j < o.getWidth(); j++) {
        for (int k = 0; k <= 2; k++) {
          int x = o.getPixelValue(i, j, k);
          int y = (int) (coeff[0] * x * x + coeff[1] * x + coeff[2]);
          this.setPixelValue(i, j, k, y, arr);
        }
      }
    }
  }

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
  @Override
  public void splitPreview(String currentImageName, String newImageName, double percentage) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    int new_height = oldImage.getHeight();
    int new_width = (int) (oldImage.getWidth() * (percentage / 100.0));
    int[][][] arr = new int[new_height][new_width][3];
    for (int i = 0; i < new_height; i++) {
      for (int j = 0; j < new_width; j++) {
        for (int k = 0; k <= 2; k++) {
          this.setPixelValue(i, j, k, oldImage.getPixelValue(i, j, k), arr);
        }
      }
    }
    ImageModel newImage = new ImageModel(new_height, new_width, arr);
    this.imageMap.put(newImageName, newImage);
  }

  /**
   * Helper method to merge an operated image and its original part.
   * This method combines a temporary image (the operated one) with
   * the original image to create a new image that retains parts
   * of both. The width of the new image is determined by the
   * width of the temporary image.
   *
   * @param currentImageName   name of the current image to be merged.
   * @param temporaryImageName name of the operated image to be merged.
   * @param newImageName       name to be assigned to the resulting merged image.
   * @return true if the merging operation executed successfully, else false.
   */
  public boolean regain(String currentImageName, String temporaryImageName, String newImageName) {
    ImageModel oldImage = this.imageMap.get(currentImageName);
    ImageModel newImage = this.imageMap.get(temporaryImageName);
    int width = newImage.getWidth();
    int[][][] arr = new int[oldImage.getHeight()][oldImage.getWidth()][3];
    for (int i = 0; i < oldImage.getHeight(); i++) {
      for (int j = 0; j < oldImage.getWidth(); j++) {
        for (int k = 0; k <= 2; k++) {
          if (j < width) {
            this.setPixelValue(i, j, k, newImage.getPixelValue(i, j, k), arr);
          } else {
            this.setPixelValue(i, j, k, oldImage.getPixelValue(i, j, k), arr);
          }
        }
      }
    }
    ImageModel finalImage = this.getNewImageModel(oldImage, arr);
    this.imageMap.put(newImageName, finalImage);
    this.imageMap.remove(temporaryImageName);
    return true;
  }

}
