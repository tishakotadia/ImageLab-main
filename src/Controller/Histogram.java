package controller;

import model.OperationsV3;

/**
 * A class that generates the histogram of an
 * image. The histogram is generated of the image specified
 * in the command and result is saved as a new image.
 */

class Histogram extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;

  /**
   * Construct a Histogram command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  Histogram(String[] cmd, int commandLength) {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
  }

  /**
   * Execute the histogram operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   *
   * @param operations The operation instance which is
   *                   used to call the histogram method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    this.imageCheck(operations, this.currentImageName);
    return operations.histogram(this.currentImageName, this.newImageName);
  }


}
