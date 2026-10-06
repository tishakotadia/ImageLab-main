package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;

import model.OperationsV3;

/**
 * A class that performs the load operation on an
 * image. Class is responsible for loading images
 * from file path. Supports images with JPG,PNG and
 * PPM formats only.
 */

class Load extends AbstractCommandExecuter {
  private final String filePath;
  private final String currentImageName;
  private final String extension;
  private final Map<String, Function<OperationsV3, Boolean>> loadingImage;


  /**
   * Construct a load command object.
   * Validates the command length and initializes the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  Load(String[] cmd, int commandLength) {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.filePath = cmd[1];
    this.extension = this.filePath.substring(this.filePath.lastIndexOf(".") + 1).toLowerCase();
    this.currentImageName = cmd[2];
    this.loadingImage = new HashMap<>();
    this.loadingImage.put("png", this::load);
    this.loadingImage.put("jpg", this::load);
    this.loadingImage.put("jpeg", this::load);
    this.loadingImage.put("ppm", this::loadPpm);
  }

  /**
   * Execute the load operation.
   * The method checks the file extension and loads the
   * image accordingly.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    Function<OperationsV3, Boolean> cmd = this.loadingImage.get(this.extension);
    if (cmd == null) {
      throw new IllegalArgumentException("Extension of the image is not supported.");
    }
    return cmd.apply(operations);
  }

  /**
   * Helper Method which is used to load the PNG or
   * JPG format images by reading the file and converting
   * the pixel data into a 3-d matrix.
   * Method throws IOException if the filepath is incorrect.
   *
   * @param operations operation interface used to load image.
   * @return true if operation done successfully, else false.
   */
  private boolean load(OperationsV3 operations) {
    File file = new File(this.filePath);
    try {
      BufferedImage img = ImageIO.read(file);
      int height = img.getHeight();
      int width = img.getWidth();
      int[][][] arr = new int[height][width][3];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = img.getRGB(j, i);
          arr[i][j][0] = (rgb >> 16) & 0xff;
          arr[i][j][1] = (rgb >> 8) & 0xff;
          arr[i][j][2] = (rgb) & 0xff;
        }
      }
      return operations.loadImage(arr, this.currentImageName);
    } catch (IOException e) {
      throw new RuntimeException("Filepath provided is incorrect.");
    }
  }

  /**
   * Helper method used to load a PPM image by reading the file and
   * converting the pixel data into a 3-d array.
   * PPM format is assumed to be a plain text with "P3" format.
   * Method throws IOException if the filepath is incorrect.
   *
   * @param operations operation interface used to load image.
   * @return true if operation done successfully, else false.
   **/
  private boolean loadPpm(OperationsV3 operations) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (IOException e) {
      throw new RuntimeException("Filepath provided is incorrect.");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: " +
              "plain RAW file should begin with P3.");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    int[][][] arr = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        arr[i][j][0] = sc.nextInt();
        arr[i][j][1] = sc.nextInt();
        arr[i][j][2] = sc.nextInt();
      }
    }
    return operations.loadImage(arr, this.currentImageName);
  }


}
