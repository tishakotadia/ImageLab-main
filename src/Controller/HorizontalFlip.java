package controller;

import model.OperationsV3;

/**
 * A class that performs the task of performing
 * Horizontal Flip on an image. By performing
 * Horizontal Flip on the image we get a mirror
 * reflection of the original image.
 */

class HorizontalFlip extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;

  /**
   * Construct a HorizontalFlip command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  HorizontalFlip(String[] cmd, int commandLength) {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.currentImageName = cmd[1];
    this.newImageName = cmd[2];
  }

  /**
   * Execute the flip operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
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
    return operations.horizontalFlip(this.currentImageName, this.newImageName);
  }
}
