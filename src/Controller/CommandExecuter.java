package controller;


import model.OperationsV3;

/**
 * This interface defines the necessary method for
 * executing commands.
 * It ensures that the input command is validated
 * and the image on which operation needs to be
 * carried out exists.
 * If the command is valid then the command is
 * executed using the Operation Interface.
 */

interface CommandExecuter {


  /**
   * Method to execute the command provided as input.
   *
   * @param operations The operation instance which is
   *                   used to call the suitable method
   *                   which is to be executed on the input
   *                   image.
   */
  boolean execute(OperationsV3 operations);
}