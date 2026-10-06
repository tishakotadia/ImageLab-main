package controller;

import model.OperationsV3;


/**
 * A class that performs the rgb-combine operation on an
 * image. By performing the rgb-combine operation the class
 * combines the three images representing the red,green and
 * blue channels into a single image.
 */

class RGBCombine extends AbstractCommandExecuter {
  private final String newImageName;
  private final String redImage;
  private final String greenImage;
  private final String blueImage;

  /**
   * Construct a RGBCombine command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  RGBCombine(String[] cmd, int commandLength) {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.newImageName = cmd[1];
    this.redImage = cmd[2];
    this.greenImage = cmd[3];
    this.blueImage = cmd[4];
  }

  /**
   * Execute the rgb-combine operation on the current set
   * of channel images.
   * The method first check if all image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the combineRGB method
   *                   which is to be executed on the input
   *                   images.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    this.imageCheck(operations, this.redImage);
    this.imageCheck(operations, this.greenImage);
    this.imageCheck(operations, this.blueImage);
    return operations.combineRGB(this.redImage,
            this.greenImage, this.blueImage, this.newImageName);
  }
}
