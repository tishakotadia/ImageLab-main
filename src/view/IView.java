package view;

import java.awt.image.BufferedImage;
import java.io.File;

import controller.Features;

/**
 * The interface is representation for the view component
 * in the image processing application.
 * This interface defines methods for interacting with
 * the user interface, displaying images, showing error
 * messages, and facilitating communication between
 * the view and the controller through GUI.
 */

public interface IView {

  /**
   * Adds action listeners to all the buttons in the GUI,
   * linking each button to its corresponding image processing
   * feature method.
   * These actions allow the user to interact with the application
   * by triggering various image processing operations,
   * such as loading, saving, applying filters,
   * and adjusting properties of the image.
   * Attaches the feature controller to the view,
   * allowing the controller to handle
   * user actions from the view.
   *
   * @param features the controller interface that
   *                 provides the core functionality
   *                 of the application.
   */
  void addFeature(Features features);

  /**
   * Opens a file chooser dialog to allow the
   * user to select an image file.
   * The dialog filters for files with extensions
   * `.jpg`, `.jpeg`, `.png`, or `.ppm`.
   * If the user selects a file,
   * its absolute path is retrieved and returned as a `File` object.
   *
   * @return a `File` object representing the selected image file, or null if no file is selected.
   */
  File getFilePath();

  /**
   * Opens a file chooser dialog to allow the user
   * to select a location and format for saving an image.
   * The user can choose from formats such as
   * PNG, JPEG, JPG, or PPM.
   * If the selected file does not
   * have the correct file extension based on the
   * selected format, the extension is automatically added.
   *
   * @return a `File` object representing the selected location,or null.
   */
  File getSaveFilePath();

  /**
   * Checks if the user wants to overwrite the current image.
   * If an image is loaded, this method shows a
   * confirmation dialog asking whether
   * the user is sure they want to overwrite the current image.
   *
   * @return true if the user chooses "Yes" to overwrite the image, false otherwise
   */
  boolean checkImage();

  /**
   * Displays the given image in the image panel of the GUI.
   * This method updates the `imagePanel` with a new
   * `JLabel` containing the image.
   * The image is displayed inside a scroll
   * pane with a preferred size of 300x300 pixels.
   *
   * @param name  the name of the image to be displayed
   * @param image the `BufferedImage` object
   *              representing the image to be shown
   */
  void showImage(String name, BufferedImage image);

  /**
   * Displays the histogram of the given image in
   * the histogram panel of the GUI.
   * This method updates the `histogramPanel`
   * with a new `JLabel` containing the histogram image.
   * The histogram is displayed inside a scroll
   * pane with a preferred size of 270x270 pixels.
   *
   * @param image the `BufferedImage` object
   *              representing the image whose histogram is to be shown
   */
  void showHistogram(BufferedImage image);

  /**
   * Initializes and configures the split frame for
   * previewing a specific image operation.
   * It checks if another frame is open, and
   * if not, sets up the input panel, buttons
   * for previewing, applying, and canceling
   * the operation.
   * The frame is then displayed with the necessary
   * components for the user to interact with.
   *
   * @param commandName the name of the operation
   *                    that is being previewed
   */
  boolean splitPreview(String commandName, Features features);

  /**
   * Displays a split image in the split image panel.
   * This method updates the `splitImagePanel`
   * with a new `JLabel` containing the image.
   * The image is displayed inside a scroll pane
   * with a preferred size of 500x500 pixels.
   *
   * @param image the `BufferedImage` object representing the split image to be displayed
   */
  void showSplitImage(BufferedImage image);

  /**
   * Displays a success message after an image is
   * successfully saved.
   * This method shows a dialog box informing the
   * user that the image has been saved successfully.
   */
  void showSaveSuccess();

  /**
   * Initializes and configures the split frame for
   * performing level adjustment on the image.
   * The method sets up input fields for the
   * user to enter the black, mid, and white values
   * and adds buttons for previewing, applying,
   * and canceling the adjustment.
   * The frame is then displayed, allowing the user to preview
   * the effect of the adjustment and apply it if desired.
   */
  void levelAdjust(Features features);

  /**
   * Displays an error message when no image is
   * loaded in the application.
   * This method shows a dialog box informing
   * the user that they need to load an image
   * before performing any operations.
   */
  void showImageNotPresent();

  /**
   * Initializes and configures the split frame
   * for previewing image compression.
   * The method checks if another frame is
   * open, and if not, sets up the necessary components
   * such as buttons for previewing, applying,
   * and canceling the compression operation.
   * The frame is then displayed, allowing the
   * user to interact with it and preview the
   * compression effect before applying it.
   */
  void compressImage(Features features);

  /**
   * Initializes and configures the split frame to allow
   * the user to adjust the brightness or darkness of an image.
   * The method sets up the input panel with a text field for entering
   * the brightness value, and adds buttons for
   * previewing and applying the adjustment.
   * It also handles the display of the image preview
   * and ensures that the frame is properly
   * set up for the brightness operation.
   */
  void brightness(Features features);

  /**
   * Displays an error message in a modal dialog box.
   * This method is used to inform the user about an error condition
   * by showing a dialog box with the specified error message.
   * The dialog is parented to the `splitFrame` component, ensuring
   * that it stays associated with the application's split frame
   * and appears in a contextually appropriate position.
   *
   * @param error the error message to display.
   */
  void showError(String error);

  /**
   * Initializes and configures the split frame
   * for previewing down scaling of image.
   * The method checks if another frame is
   * open, and if not, sets up the necessary components
   * such as buttons for previewing, applying,
   * and canceling the compression operation.
   * The frame is then displayed, allowing the
   * user to interact with it and preview the
   * downscale effect before applying it.
   */
  void downScale(int height, int width, Features features);

}
