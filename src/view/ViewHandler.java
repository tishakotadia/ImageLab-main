package view;

import java.io.IOException;

/**
 * View class which implements the ProgramView interface.
 * It has an Appendable object to store the output to be displayed to the user.
 */
public class ViewHandler implements ProgramView {
  Appendable out;

  /**
   * Constructor to initialize the Appendable object.
   *
   * @param out appendable.
   */
  public ViewHandler(Appendable out) {
    this.out = out;
  }

  /**
   * Method to set the output displayed to the user.
   *
   * @param output String.
   */
  @Override
  public void setOutput(String output) {
    try {
      this.out.append(output);
    } catch (IOException e) {
      throw new IllegalArgumentException("Something went wrong");
    }
  }
}