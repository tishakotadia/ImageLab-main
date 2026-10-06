package controller;


/**
 * The ImageAppController interface defines the contract for
 * controlling the execution flow of an image processing application.
 * Implementing classes are responsible for handling and executing
 * commands input into the system, managing the necessary logic to
 * facilitate the image processing workflow.
 */

public interface ImageAppController {


  /**
   * Starts the application by loading the necessary resources,
   * such as the script path required for the application's execution.
   * This method initiates the process by invoking getCommand or addFeatures
   * according to the mode of execution. If an error
   * occurs during this process (e.g., file not found, permission issues),
   * an IOException will be caught, and an appropriate error message
   * will be printed to the console.
   * Implementations should ensure proper error handling and provide
   * informative error messages to guide users when issues arise.
   */
  void startApplication();


}
