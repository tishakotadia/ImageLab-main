package controller;

import model.OperationsV3;

/**
 * A class that performs the LevelsAdjust operation on an
 * image. The levels-adjust is applied to the image specified
 * in the command and result is saved as a new image.
 */

class LevelsAdjust extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final int b;
  private final int m;
  private final int w;
  private final double percentage;

  /**
   * Construct a Levels-adjust command object.
   * Validate the command length and initialize the image
   * names.
   * As this command supports the split operation also so two check are made for
   * the command length. If split is provided then percentage
   * is set to the value provided by user else the value is set to 100.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   * @throws NumberFormatException if the input b,m or w value in not an integer.
   */

  LevelsAdjust(String[] cmd, int commandLength) throws NumberFormatException {
    if (this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[4];
      this.newImageName = cmd[5];
      this.percentage = 100.00;
    } else if (this.validCommandLength(cmd.length, 8)) {
      this.currentImageName = cmd[4];
      this.newImageName = cmd[5];
      if (!cmd[6].equals("split")) {
        throw new IllegalArgumentException("Invalid Command");
      }
      try {
        this.percentage = Double.parseDouble(cmd[7]);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("Percentage must be a number.");
      }
      if (this.percentage < 0 || this.percentage > 100) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100.");
      }
    } else {
      throw new IllegalArgumentException("Invalid command length.");
    }
    try {
      this.b = Integer.parseInt(cmd[1]);
      this.m = Integer.parseInt(cmd[2]);
      this.w = Integer.parseInt(cmd[3]);
      if (!((b <= m && m <= w))) {
        throw new IllegalArgumentException("B,M and W value must be in ascending order");
      }
      if (!(b >= 0 && w <= 255)) {
        throw new IllegalArgumentException("B,M and W value must be between 0 and 255");
      }
    } catch (NumberFormatException e) {
      throw new NumberFormatException("B, M and W value should be Integral values");
    }
  }

  /**
   * Execute the levels-adjust operation on the current image.
   * The method first check if the image on which operation
   * is to be done in present in the system or not.
   * For the split operation a check if done on the value of the percentage,
   * if the percentage is 100 then directly levels-adjust is applied.
   * Else first the image is split, after split the operation is done on split
   * half and at end the image is combines with the remaining half.
   *
   * @param operations The operation instance which is
   *                   used to call the levels-adjust method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00) {
      return operations.levelAdjustment(this.currentImageName, this.newImageName, this.
              b, this.m, this.w);
    }
    String temp = this.newImageName + this.newImageName.hashCode();
    operations.splitPreview(this.currentImageName, temp, this.percentage);
    boolean t = operations.levelAdjustment(temp, temp, this.b, this.m, this.w);
    return operations.regain(this.currentImageName, temp, this.newImageName) & t;

  }
}
