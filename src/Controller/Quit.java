package controller;

import model.OperationsV3;

/**
 * A class that performs the Quit operation.
 * So when the user provides the quit command
 * the program terminated.
 */

class Quit extends AbstractCommandExecuter {

  /**
   * Construct a Quit command object.
   * Validate the command length and initialize the image
   * names.
   *
   * @param cmd           the command array obtained by splitting
   *                      input using space.
   * @param commandLength the expected length of command array.
   */
  Quit(String[] cmd, int commandLength) {

    if (!this.validCommandLength(cmd.length, commandLength)) {
      throw new IllegalArgumentException("Invalid command length");
    }
  }

  /**
   * Execute the quit operations to terminate the
   * program.
   *
   * @param operations The operation instance which is
   *                   used to call the brighten method
   *                   which is to be executed on the input
   *                   image.
   * @return true if operation done successfully, else false.
   */
  @Override
  public boolean execute(OperationsV3 operations) {
    System.exit(0);
    return true;
  }
}
