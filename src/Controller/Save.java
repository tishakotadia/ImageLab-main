package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.imageio.ImageIO;

import model.OperationsV3;

/**
 * A class that performs the Save operation on an
 * image. Image needs to be saves in a specified file
 * path with the suitable extension.
 */

class Save extends AbstractCommandExecuter {
  private final String filePath;
  private final String currentImageName;
  private final String extension;
  private final Map<String, Function<OperationsV3, Boolean>> savingImage;

  /**
   * Construct a Save command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  Save(String[] cmd, int commandLength) {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.filePath = cmd[1];
    this.extension = this.filePath.substring(this.filePath.lastIndexOf(".") + 1).toLowerCase();
    this.currentImageName = cmd[2];
    this.savingImage = new HashMap<>();
    this.savingImage.put("png", this::save);
    this.savingImage.put("jpeg", this::save);
    this.savingImage.put("jpg", this::save);
    this.savingImage.put("ppm", this::savePPM);
  }

  /**
   * Execute the save operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   * On the basis of the suitable extension in which the image
   * is to be stored a suitable private method is called.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    this.imageCheck(operations, this.currentImageName);
    Function<OperationsV3, Boolean> cmd = this.savingImage.get(this.extension);
    if (cmd == null) {
      throw new IllegalArgumentException("This extension is not Supported");
    }
    try {
      File file = new File(this.filePath);
      File parent = file.getParentFile();
      if (!parent.exists()) {
        throw new IllegalArgumentException("Filepath provided is incorrect");
      }
      return cmd.apply(operations);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Helper method used to save image in the PNG, JPG
   * or JPEG format.
   * Convert the 3-d image pixel matrix into a BufferedImage
   * and then write it at the suitable path.
   * Method throws IOException if the filepath is incorrect.
   *
   * @param operations operations interface instance to save the
   *                   image.
   * @return true if operation done successfully, else false.
   */

  private boolean save(OperationsV3 operations) {
    int[][][] arr = operations.saveImage(this.currentImageName);
    File file = new File(this.filePath);
    int width = arr[0].length;
    int height = arr.length;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    try {
      for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length; j++) {
          int rgb = arr[i][j][0] << 16 | arr[i][j][1] << 8 |
                  arr[i][j][2];
          img.setRGB(j, i, rgb);
        }
      }
      ImageIO.write(img, extension, file);
      return true;
    } catch (IOException e) {
      throw new RuntimeException("Cannot Save the Image, incorrect filepath");
    }
  }

  /**
   * Helper method used to save image in the PPM
   * format.
   * Writes the image data to a PPM file in ASCII
   * format (P3 format), including the image's
   * dimensions and pixel data.
   * Method throws IOException if the filepath is incorrect.
   *
   * @param operations operation interface instance used to
   *                   save the image.
   */

  private boolean savePPM(OperationsV3 operations) {
    int[][][] arr = operations.saveImage(this.currentImageName);
    try {
      File output = new File(this.filePath);
      BufferedWriter bw = new BufferedWriter(new FileWriter(output));
      bw.write("P3\n");
      bw.write("#" + currentImageName + ".PPM Image\n");
      bw.write(arr[0].length + " " + arr.length + "\n");
      bw.write("255\n");
      for (int[][] ints : arr) {
        for (int[] anInt : ints) {
          bw.write(anInt[0] + " " + anInt[1] + " " + anInt[2] + " ");
        }
        bw.write("\n");
      }
      bw.close();
      return true;
    } catch (IOException e) {
      throw new IllegalArgumentException("Image Cannot be save. Filepath" +
              "is incorrect.");
    }
  }


}
