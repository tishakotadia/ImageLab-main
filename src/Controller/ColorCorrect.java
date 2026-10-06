package controller;

import model.OperationsV3;

/**
 * A class that performs the task of performing the
 * Color Correct on an image.
 * By performing color-correct on an image we
 * tend to make all the peaks in the all channels
 * lie at same intensity-value.
 * If there are two peaks in the channel then we take
 * the peak as the highest intensity value.
 */


class ColorCorrect extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final double percentage;

  /**
   * Construct a ColorCorrect command object.
   * Validate the command length and initializes the image
   * names.
   * As this command supports the split operation also so two check are made for
   * the command length. If split is provided then percentage
   * is set to the value provided by user else the value is set to 100.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  ColorCorrect(String[] cmd, int commandLength) {
    if (this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.percentage = 100;
    } else if (this.validCommandLength(cmd.length, 5)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      if (!(cmd[3].equals("split"))) {
        throw new IllegalArgumentException("Invalid Command");
      }
      try {
        this.percentage = Double.parseDouble(cmd[4]);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("Percentage must be a number.");
      }
      if (this.percentage < 0 || this.percentage > 100) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100.");
      }
    } else {
      throw new IllegalArgumentException("Invalid command length");
    }
  }


  /**
   * Execute the ColorCorrect operation based on the specified
   * handle provided in the command.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   * For the split operation a check if done on the value of the percentage,
   * if the percentage is 100 then directly color-correct is applied.
   * Else first the image is split, after split the operation is done on split
   * half and at end the image is combines with the remaining half.
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
    if (this.percentage == 100.00) {
      return operations.colorCorrection(this.currentImageName, this.newImageName);
    }
    String temp = this.newImageName + this.newImageName.hashCode();
    operations.splitPreview(this.currentImageName, temp, this.percentage);
    boolean t = operations.colorCorrection(temp, temp);
    return operations.regain(this.currentImageName, temp, this.newImageName) & t;
  }
}
