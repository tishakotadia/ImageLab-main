package controller;

import model.OperationsV3;


/**
 * A class that performs the Sepia operation on an
 * image. The sepia is applied to the image specified
 * in the command and result is saved as a new image.
 * After performing sepia a reddish tone is obtained on
 * the image.
 */

class Sepia extends AbstractCommandExecuter {
  private final String currentImageName;
  private final String newImageName;
  private final double percentage;
  private final String maskImageName;

  /**
   * Constructs a Sepia command object.
   * Validates the command length and initializes the image names.
   * This constructor handles different command formats based on
   * the provided input.
   * It supports split and masking operations, with specific
   * validation and initialization steps for each case.
   * If the split operation is provided, the percentage is
   * set to the value provided by the user; otherwise,
   * it defaults to 100.
   * If a mask is provided, the mask image name is set,
   * and the masking operation is carried out accordingly.
   * A check is performed to ensure the mask image
   * exists if specified.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */

  Sepia(String[] cmd, int commandLength) {
    if (this.validCommandLength(cmd.length, commandLength)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.percentage = 100;
      this.maskImageName = null;
    } else if (this.validCommandLength(cmd.length, 5)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[2];
      this.maskImageName = null;
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
    } else if (this.validCommandLength(cmd.length, 4)) {
      this.currentImageName = cmd[1];
      this.newImageName = cmd[3];
      this.percentage = 100.00;
      this.maskImageName = cmd[2];
    } else {
      throw new IllegalArgumentException("Invalid Command.");
    }
  }

  /**
   * Executes the sepia operation on the current image.
   * The method first checks if the image on which
   * the operation is to be performed is available in the system.
   * It handles both standard sepia operations and operations
   * involving image splitting and masking:
   * If the percentage is 100 and no mask image is provided,
   * the sepia operation is applied directly.
   * If the percentage is less than 100, the image is split.
   * The sepia operation is applied on one half,
   * and then the image is recombined with the remaining half.
   * If a mask image is provided, first a check is made if mask image
   * is present or not. If present then a temporary image is created
   * which is obtained as a result of sepia operation on original image.
   * Then the masking operation is called to obtain the final resultant
   * image.
   *
   * @param operations the instance that provides the methods to perform the sepia, split,
   *                   and mask operations on the images.
   * @return true if the operation is completed successfully; false otherwise.
   * @throws IllegalArgumentException if the current image or mask
   *                                  image (if provided) is not found in the system.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    this.imageCheck(operations, this.currentImageName);
    if (this.percentage == 100.00 && this.maskImageName == null) {
      return operations.sepia(this.currentImageName, this.newImageName);
    } else {
      String temp = this.newImageName + this.newImageName.hashCode();
      if (this.maskImageName != null) {
        this.imageCheck(operations, this.maskImageName);
        boolean t = operations.sepia(this.currentImageName, temp);
        return operations.mask(this.currentImageName, temp, this.maskImageName,
                this.newImageName) & t;
      }
      operations.splitPreview(this.currentImageName, temp, this.percentage);
      boolean t = operations.sepia(temp, temp);
      return operations.regain(this.currentImageName, temp, this.newImageName) & t;
    }
  }

}
