package controller;

import model.OperationsV3;

/**
 * An Abstract class that implements the CommandExecuter interface.
 * This class provides the common validation methods for the various commands.
 * This class validates the length of the command and checks if the specified image
 * exists in the provided context. The execute method remains
 * abstract for subclasses to implement specific behavior.
 */

abstract class AbstractCommandExecuter implements CommandExecuter {

  /**
   * Method to validate the length of the input
   * command.
   *
   * @param length1 the actual length of the command.
   * @param length2 the expected length of the
   *                command.
   * @throws IllegalArgumentException if the lengths do not match.
   */
  protected boolean validCommandLength(int length1, int length2) throws IllegalArgumentException {
    return length1 == length2;
  }

  /**
   * Method to check if the image on which operations need to carried
   * out is present in the system or not.
   *
   * @param operations the instance of operation interface.
   * @param imageName  the name of image which is to be
   *                   checked.
   * @throws IllegalArgumentException if the image is not found.
   */
  protected void imageCheck(OperationsV3 operations, String imageName)
          throws IllegalArgumentException {
    if (!(operations.checkImage(imageName))) {
      throw new IllegalArgumentException("The image to be processed is not present.");
    }
  }

  /**
   * Method to execute the command provided as input.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public abstract boolean execute(OperationsV3 operations);


}
