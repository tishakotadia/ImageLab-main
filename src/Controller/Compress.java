package controller;

import model.OperationsV3;

/**
 * A class that performs the task of obtaining the
 * compress version of an image.
 * Harr method for compression is used. This is a lossy
 * compression method.
 */


class Compress extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final double percentage;

  /**
   * Construct a Compress command object.
   * Validate the command length and initializes the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   * @throws IllegalArgumentException if the command length is invalid or if the
   *                                  percentage is not between 0 and 100.
   * @throws NumberFormatException    if the percentage provided in cmd[1]
   *                                  is not a valid numeric value.
   */

  Compress(String[] cmd, int commandLength) throws NumberFormatException {
    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
    this.currentImageName = cmd[2];
    this.newImageName = cmd[3];
    try {
      this.percentage = Double.parseDouble(cmd[1]);
      if (this.percentage < 0.0 || this.percentage > 100.0) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100");
      }
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Percentage must be a numeric value");
    }
  }

  /**
   * Execute the Compress operation based on the specified
   * handle provided in the command.
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
    return operations.compressImage(this.currentImageName, this.newImageName, this.percentage);
  }
}
