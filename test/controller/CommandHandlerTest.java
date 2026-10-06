package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.ImageOperationsV3;
import model.OperationsV3;
import view.ViewHandler;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to perform all the testing related
 * to the Controller package.
 */

public class CommandHandlerTest {
  ImageAppController controller;
  OperationsV3 opr;
  String cmd = "Enter the Command:\n";
  String cmdload = "Enter the Command:\n" + "load executed successfully\n";
  StringBuffer out;
  ViewHandler v;

  @Before
  public void setUp() {
    opr = new ImageOperationsV3();
    out = new StringBuffer();
    v = new ViewHandler(out);
  }

  /**
   * Test Case to check if the load method is
   * working correctly.
   */
  @Test
  public void testLoad() {
    String command = "load images/manhattan-small.png man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the PPM is load correctly.
   */
  @Test
  public void testLoadPPM() {
    String command = "load images/koala.ppm man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the jpg is load correctly.
   */
  @Test
  public void testLoadJPG() {
    String command = "load images/bird.jpg man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the load throws
   * error if command is given incorrectly.
   */
  @Test
  public void testLoadIncorrectFormat() {
    String command = "load images/manhattan-small.png ";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the load method
   * throws error if extension is not
   * supported.
   */
  @Test
  public void testLoadWithIncorrectExtension() {
    String command = "load images/manhattan-small.oip man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Extension of the image is not supported.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the load method
   * throws error if filepath is not valid.
   */
  @Test
  public void testLoadWithIncorrectFilePath() {
    String command = "load img/manhattan-small.ppm man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Filepath provided is incorrect.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the brighten command is
   * called successfully.
   */
  @Test
  public void testBrighten() {
    String command = "load images/manhattan-small.png man\n " +
            "brighten 100 man man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "brighten executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check the darken command is
   * executed successfully.
   */
  @Test
  public void testDarken() {
    String command = "load images/manhattan-small.png man" +
            "\n brighten -100 man man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "brighten executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brighten command
   * throws error when increment value is not
   * an integer (double).
   */
  @Test
  public void testBrightenIfNoIntegerGiven() {
    String command = "load images/manhattan-small.png man" +
            "\n brighten 100.56 man man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Increment must be an integer.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brighten command
   * throws error when increment value is not
   * an integer (string).
   */
  @Test
  public void testBrightenIfStringGiven() {
    String command = "load images/manhattan-small.png man\n " +
            "brighten hundred man man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Increment must be an integer.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brighten command
   * gives error when given incorrectFormat.
   */
  @Test
  public void testBrightenIncorrectFormat() {
    String command = "load images/manhattan-small.png man" +
            "\n brighten 100 man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check brighten throws error when image to be
   * brighten is not present in application.
   */
  @Test
  public void testBrightenWithoutImage() {
    String command = "brighten 100 man man-brighten";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Horizontal flip
   * is called correctly.
   */
  @Test
  public void testHorizontalFlip() {
    String command = "load images/manhattan-small.png man" +
            "\n horizontal-flip man man-horizontal-flip";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "horizontal-flip executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Horizontal flip
   * throw error if invalid command format.
   */
  @Test
  public void testHorizontalFlipInvalidCommand() {
    String command = "load images/manhattan-small.png man" +
            "\n horizontal-flip man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check horizontal-flip throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testHorizontalFlipImageNotFound() {
    String command = "load images/manhattan-small.png man\n" +
            " horizontal-flip man-br man-horizontal";
    Reader in = new StringReader(command);


    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be " +
            "processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if vertical-flip is called
   * correctly.
   */
  @Test
  public void testVerticalFlip() {
    String command = "load images/manhattan-small.png man" +
            "\n vertical-flip  man man-vflip";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "vertical-flip executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Horizontal flip
   * throw error if invalid command format.
   */
  @Test
  public void testVerticalFlipInvalidCommand() {
    String command = "load images/manhattan-small.png man" +
            "\n vertical-flip man-br";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check horizontal-flip throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testVerticalFlipImageNotFound() {
    String command = "load images/manhattan-small.png man\n " +
            "vertical-flip man-br man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Blur method is called,
   * correctly.
   */
  @Test
  public void testBlur() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-blur";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "blur executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Blur
   * throw error if invalid command format.
   */
  @Test
  public void testBlurInvalidCommand() {
    String command = "load images/manhattan-small.png man" +
            "\n blur man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check blur throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testBlurImageNotFound() {
    String command = "load images/manhattan-small.png man" +
            "\n blur man-br man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with blur works correctly.
   */
  @Test
  public void testBlurWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-sharpen split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "blur executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with blur works correctly.
   */
  @Test
  public void testBlurWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-sharpen split 50.78\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "blur executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * blur.
   */
  @Test
  public void testBlurWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-red split\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * blur.
   */
  @Test
  public void testBlurWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-red split hundred\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testBlurWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-red sp 45\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testBlurSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-level split -23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";

    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testBlurSplitValueMore100() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man man-level split 123\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the sharpen method is
   * called correctly.
   */
  @Test
  public void testSharpen() {
    String command = "load images/manhattan-small.png man" +
            "\n sharpen  man man-sharpen";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sharpen executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Sharpen
   * throw error if invalid command format.
   */
  @Test
  public void testSharpenInvalidCommand() {
    String command = "load images/manhattan-small.png man" +
            "\n sharpen man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Sharpen throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testSharpenImageNotFound() {
    String command = "load images/manhattan-small.png man" +
            "\n sharpen man-br man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with sharpen works correctly.
   */
  @Test
  public void testSharpenWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-sharpen split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sharpen executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with sharpen works correctly.
   */
  @Test
  public void testSharpenWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-sharpen split 50.78\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sharpen executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * sharpen.
   */
  @Test
  public void testSharpWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-sharp split";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * sharpen.
   */
  @Test
  public void testSharpenWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-red split hundred\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testSharpenWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-red sp 45\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testSharpSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-level split -23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testSharpSplitValueMore100() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man man-level split 123\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the sepia method is
   * called correctly.
   */
  @Test
  public void testSepia() {
    String command = "load images/manhattan-small.png man" +
            "\n sepia  man man-sepia";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sepia executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if Sepia
   * throw error if invalid command format.
   */
  @Test
  public void testSepiaInvalidCommand() {
    String command = "load images/manhattan-small.png man" +
            "\n sepia man-br";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Sepia throw error,
   * if image to be loaded is not present.
   */
  @Test
  public void testSepiaImageNotFound() {
    String command = "load images/manhattan-small.png man" +
            "\n sepia man-br man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with sepia works correctly.
   */
  @Test
  public void testSepiaWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-sepia split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sepia executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with sepia works correctly
   * with double.
   */
  @Test
  public void testSepiaWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-sepia split 80.90\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "sepia executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * sepia.
   */
  @Test
  public void testSepiaWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-sepia split\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * sepia.
   */
  @Test
  public void testSepiaWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-sepia split hundred\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testSepiaWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-sepia sp 45\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testSepiaSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-level split -23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";

    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testSepiaSplitValueMore100() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man man-level split 123\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the brightness-component
   * method is called correctly.
   */
  @Test
  public void testBrightnessComponent() {
    String command = "load images/manhattan-small.png man\n " +
            "value-component  man man-value" +
            "\n intensity-component man man-intensity" +
            "\n luma-component man man-luma";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if brightness component
   * throw error if invalid command format.
   */
  @Test
  public void testBrightnessComponentInvalidCommand() {
    String command = "load images/manhattan-small.png man\n" +
            " value-component man" +
            "\n intensity-component man\n" +
            "luma-component man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\nInvalid Command.\n" +
            "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check brightnessComponent
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testBrightnessComponentImageNotFound() {
    String command = "load images/manhattan-small.png man\n " +
            "value-component man-br man-horizontal\n" +
            "intensity-component man-br man-horizontal "
            + "\n luma-component man-br man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with brightness-component works correctly.
   */
  @Test
  public void testBrightnessComponentWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-value split 50\n" +
            "luma-component man man-luma split 50\n" +
            "intensity-component man man-intensity split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "value-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "intensity-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with brightness-component works correctly.
   */
  @Test
  public void testBrightnessComponentWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-value split 50.67\n" +
            "luma-component man man-luma split 50.56\n" +
            "intensity-component man man-intensity split 50.56\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "value-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "intensity-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * brightness-component.
   */
  @Test
  public void testBrightnessComponentWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-value split\n" +
            "luma-component man man-luma split \n" +
            "intensity-component man man-intensity split \n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n"
            + "The image to be processed is not present.\n"
            + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * brightness-component.
   */
  @Test
  public void testBrightnessComponentWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-value split hundred\n" +
            "luma-component man man-luma split hundred\n" +
            "intensity-component man man-intensity split hundred\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n"
            + "Percentage must be a number.\n"
            + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testBrightnessComponentWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-value sp 45\n" +
            "luma-component man man-luma sp 34\n" +
            "intensity-component man man-intensity sp 23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testBrightnessComponentSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-level split -23\n" +
            "intensity-component man man-level split -23\n" +
            "luma-component man man-level split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testBrightnessComponentSplitValueMore100() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man man-level split 123\n" +
            "luma-component man man-level split 123\n" +
            "intensity-component man man-level split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if the color-component
   * method is called correctly.
   */
  @Test
  public void testColorComponent() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component  man man-red" +
            "\ngreen-component man man-green\n " +
            "blue-component man man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if color component
   * throw error if invalid command format.
   */
  @Test
  public void testColorComponentInvalidCommand() {
    String command = "load images/manhattan-small.png man\n " +
            "red-component man" +
            "\n green-component man   \nblue-component man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command.\nInvalid Command.\n" +
            "Invalid Command.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check ColorComponent
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testColorComponentImageNotFound() {
    String command = "load images/manhattan-small.png man\n" +
            " red-component man-br " +
            "man-horizontal\n blue-component " +
            "man-br man-horizontal \n " +
            "green-component man-br man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with color-component works correctly.
   */
  @Test
  public void testColorComponentWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-red split 50\n" +
            "blue-component man man-blue split 50\n" +
            "green-component man man-green split 50\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "red-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "green-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with color-component works correctly
   * with double.
   */
  @Test
  public void testColorComponentWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-red split 50.89\n" +
            "blue-component man man-blue split 60.45\n" +
            "green-component man man-green split 50.23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "red-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "green-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * color-component.
   */
  @Test
  public void testColorComponentWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-red split\n" +
            "blue-component man man-blue split \n" +
            "green-component man man-green split \n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n"
            + "The image to be processed is not present.\n"
            + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * color-component.
   */
  @Test
  public void testColorComponentWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-red split hundred\n" +
            "blue-component man man-blue split hundred\n" +
            "green-component man man-green split hundred\n";

    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n"
            + "Percentage must be a number.\n"
            + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testColorComponentWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-red sp 45\n" +
            "blue-component man man-blue sp 34\n" +
            "green-component man man-green sp 23\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n"
            + "Invalid Command\n"
            + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testColorComponentSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-level split -23\n" +
            "blue-component man man-level split -23\n" +
            "green-component man man-level split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testColorComponentSplitValueMore100() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man man-level split 123\n" +
            "blue-component man man-level split 123\n" +
            "green-component man man-level split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n"
            + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if rgb-split is called
   * correctly.
   */
  @Test
  public void testRGBSplit() {
    String command = "load images/manhattan-small.png " +
            "man\n rgb-split man " +
            "man-red man-green man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if rgb-split component
   * throw error if invalid command format.
   */
  @Test
  public void testRGBSplitInvalidCommand() {
    String command = "load images/manhattan-small.png man" +
            "\n rgb-split man red green";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check rgb-split
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testRGBSplitImageNotFound() {
    String command = "load images/manhattan-small.png man\n " +
            "rgb-split man2 man-red "
            + "man-green man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if rgb-combine is called
   * correctly.
   */
  @Test
  public void testRGBCombine() {
    String command = "load images/manhattan-small.png man\n " +
            "rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-red man-green man-blue";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully\n"
            + "rgb-combine executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test case to check if rgb-split component
   * throw error if invalid command format.
   */
  @Test
  public void testRGBCombineInvalidCommand() {
    String command = "load images/manhattan-small.png man\n " +
            "rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-red";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully\n" +
            "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check rgb-split
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testRGBCombineImageNotFound() {
    String command = "load images/manhattan-small.png man\n " +
            "rgb-split man " +
            "man-red man-green man-blue\nrgb-combine " +
            "man-combine man-r man-g man-b";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "rgb-split executed successfully" +
            "\nThe image to be processed is not present.\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check if save is called
   * correctly.
   */
  @Test
  public void testSave() {
    String command = "load images/manhattan-small.png man\n " +
            "save test/controller/resultTest/man.png man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "save executed successfully\n";
    assertEquals(expected, output);
    assertTrue("Image not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/man.png")));
  }

  /**
   * Test case to check if save component
   * throw error if invalid command format.
   */
  @Test
  public void testSaveInvalidCommand() {
    String command = "load images/manhattan-small.png man\n " +
            "save man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check save
   * throw error, if image to be loaded is not present.
   */
  @Test
  public void testSaveImageNotFound() {
    String command = "load images/manhattan-small.png man\n " +
            "save test/controller/resultTest/man.png man-horizontal";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check save
   * throw error, if file is incorrect.
   */
  @Test
  public void testSaveImageIncorrectFilePath() {
    String command = "load images/manhattan-small.png man\n " +
            "save im/man.png man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Filepath provided is incorrect\n";
    assertEquals(expected, output);
  }


  /**
   * Test Case to check command executed
   * even if there are whitespaces in between.
   */
  @Test
  public void testCommandWithWhiteSpaces() {
    String command = "load images/manhattan-small.png          man\n";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check command still exists
   * even when user gives invalid command.
   */
  @Test
  public void testCommandStillWorksAfterInvalidCommand() {
    String command = "load images/manhattan-small.png\n load images" +
            "/manhattan-small.png man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid command length\nload executed " +
            "successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check save throw
   * error, if file-extension is incorrect.
   */
  @Test
  public void testSaveImageExtensionNot() {
    String command = "load images/manhattan-small.png man\n " +
            "save images/man.ajd man";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "This extension is not Supported\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the invalid syntax.
   */
  @Test
  public void testRun() {
    String command = "run";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Command is not valid\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the invalid filepath.
   */
  @Test
  public void testRunFileNotFound() {
    String command = "run test/controller/ScriptFiles/src.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "File not found\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run command throw error
   * if the filepath is not txt.
   */
  @Test
  public void testRunFileWithNoTXT() {
    String command = "run test/controller/ScriptFiles/src.pdf";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Provide txt File only\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run script file works
   * correct when correct operations are given
   * on png image.
   */
  @Test
  public void testRunFilePng() {
    String command = "run test/controller/ScriptFiles/filepng.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd +
            "load executed successfully\n" +
            "load executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "compress executed successfully\n" +
            "save executed successfully\n" +
            "histogram executed successfully\n" +
            "save executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "save executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "color-correct executed successfully\n" +
            "save executed successfully\n" +
            "color-correct executed successfully\n";

    assertEquals(expected, output);
    assertTrue("bird-histogram not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-hist.png")));
    assertTrue("bird-color-correct not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-correct.png")));
    assertTrue("bird-blur-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-blur-mask.jpg")));
    assertTrue("bird-sharpen-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-sharpen-mask.jpg")));
    assertTrue("bird-sepia-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-sepia-mask.jpg")));


  }

  /**
   * Test Case to check if run script file works
   * correct when correct operations are given
   * on Jpg image.
   */
  @Test
  public void testRunFileJpg() {
    String command = "run test/controller/ScriptFiles/filejpg.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "compress executed successfully\n" +
            "save executed successfully\n" +
            "histogram executed successfully\n" +
            "save executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "save executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "color-correct executed successfully\n" +
            "save executed successfully\n" +
            "color-correct executed successfully\n";
    assertEquals(expected, output);
    assertTrue("bird-horizontal not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-hor.jpg")));
    assertTrue("bird-bright not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-dark-100.jpg")));
    assertTrue("bird-blur not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/" +
                    "bird-split.jpg")));
    assertTrue("bird-compress not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-comp.jpg")));
    assertTrue("bird-levels-adjust not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/" +
                    "bird-level.png")));
    assertTrue("bird-blur-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-blur-mask.jpg")));
    assertTrue("bird-sharpen-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-sharpen-mask.jpg")));
    assertTrue("bird-sepia-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-sepia-mask.jpg")));


  }

  /**
   * Test Case to check if run script file works
   * correct when correct operations are given
   * on PPM image.
   */
  @Test
  public void testRunFilePPM() {
    String command = "run test/controller/ScriptFiles/fileppm.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "compress executed successfully\n" +
            "save executed successfully\n" +
            "histogram executed successfully\n" +
            "save executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "save executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "color-correct executed successfully\n" +
            "save executed successfully\n" +
            "color-correct executed successfully\n";
    assertEquals(expected, output);
    assertTrue("bird-vertical not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-ver.ppm")));
    assertTrue("RGB-Split not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-redsplit.ppm")));
    assertTrue("RGB-Split not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-greensplit.ppm")));
    assertTrue("RGB-Split not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-bluesplit.ppm")));
    assertTrue("RGB-combine not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-combine.ppm")));
    assertTrue("bird-blur not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-blur.ppm")));
    assertTrue("bird-sharp not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-sharpen.ppm")));
    assertTrue("bird-sharp-split not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-split.png")));
    assertTrue("bird-sepia-split not save successfully",
            Files.exists(Paths.get("test/controller/resultTest/bird-sepia.jpg")));
    assertTrue("bird-blur-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-blur-mask.jpg")));
    assertTrue("bird-sharpen-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-sharpen-mask.jpg")));
    assertTrue("bird-sepia-mask not save successfully",
            Files.exists(Paths.get("test/controller/" +
                    "resultTest/bird-sepia-mask.jpg")));
  }

  /**
   * Test Case to check if Run Script support multiple load.
   */
  @Test
  public void testRunLoadMultiple() {
    String command = "run test/controller/ScriptFiles/fileMultipleLoad.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "load executed successfully\n" +
            "load executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "red-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "value-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "intensity-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "luma-component executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "horizontal-flip executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-split executed successfully\n" +
            "rgb-split executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "rgb-combine executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "sharpen executed successfully\n" +
            "sharpen executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "blur executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "sepia executed successfully\n" +
            "sepia executed successfully\n" +
            "sepia executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "sharpen executed successfully\n" +
            "save executed successfully\n" +
            "compress executed successfully\n" +
            "compress executed successfully\n" +
            "histogram executed successfully\n" +
            "histogram executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "levels-adjust executed successfully\n" +
            "color-correct executed successfully\n" +
            "color-correct executed successfully\n" +
            "color-correct executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if run script file throws
   * error when the command in it is invalid.
   */

  @Test
  public void testRunFileError() {
    String command = "run test/controller/ScriptFiles/fileerror.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Invalid command length\n" +
            "Filepath provided is incorrect.\n" +
            "Extension of the image is not supported.\n" +
            "load executed successfully\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Percentage must be a number.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Increment must be an integer.\n" +
            "The image to be processed is not present.\n" +
            "Increment must be an integer.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid command length\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Percentage must be between 0 and 100.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command.\n" +
            "The image to be processed is not present.\n" +
            "Percentage must be between 0 and 100.\n" +
            "Invalid command length\n" +
            "Filepath provided is incorrect\n" +
            "This extension is not Supported\n" +
            "Unknown command: flipvertical\n" +
            "Unknown command: sharp\n" +
            "Percentage must be between 0 and 100\n" +
            "Percentage must be a numeric value\n" +
            "The image to be processed is not present.\n" +
            "B,M and W value must be in ascending order\n" +
            "B, M and W value should be Integral values\n" +
            "B,M and W value must be between 0 and 255\n" +
            "B,M and W value must be in ascending order\n" +
            "Unknown command: colorcorrect\n" +
            "Percentage must be between 0 and 100.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "load executed successfully\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n" +
            "The Dimensions of mask and the image to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check that the interconversion of the
   * files is supported.
   */
  @Test
  public void testInterConversion() {
    String command = "run test/controller/ScriptFiles/fileconversion.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "load executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n" +
            "save executed successfully\n";
    assertEquals(expected, output);

  }

  /**
   * Test Case to check with script file have
   * both incorrect and correct command.
   */
  @Test
  public void testRunWithCorrectIncorrect() {
    String command = "run test/controller/ScriptFiles/filecorrectIncorrect.txt";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "load executed successfully\n" +
            "load executed successfully\n" +
            "Invalid Command.\n" +
            "green-component executed successfully\n" +
            "green-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "blue-component executed successfully\n" +
            "The image to be processed is not present.\n" +
            "Invalid Command\n" +
            "The image to be processed is not present.\n" +
            "horizontal-flip executed successfully\n" +
            "Invalid command length\n" +
            "vertical-flip executed successfully\n" +
            "save executed successfully\n" +
            "brighten executed successfully\n" +
            "save executed successfully\n" +
            "Increment must be an integer.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "The image to be processed is not present.\n" +
            "compress executed successfully\n" +
            "Invalid command length\n" +
            "levels-adjust executed successfully\n" +
            "Invalid Command\n" +
            "color-correct executed successfully\n" +
            "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if controller gives error when a command
   * do defined is given.
   */
  @Test
  public void testUnknownCommand() {
    String command = "enhance new Img";
    Reader in = new StringReader(command);

    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmd + "Unknown command: enhance\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the compression is
   * called correctly in controller.
   */
  @Test
  public void testCompress() {
    String command = "load images/manhattan-small.png man\n" +
            "compress 90 man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "compress executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the compression is
   * called correctly in controller.
   */
  @Test
  public void testCompressWithDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "compress 90.45 man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "compress executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if no value is given in compress
   * command.
   */
  @Test
  public void testCompressNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "compress percent man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a numeric value\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if string value is given in compress
   * command.
   */
  @Test
  public void testCompressStringValue() {
    String command = "load images/manhattan-small.png man\n" +
            "compress twenty man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a numeric value\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if negative percentage is given in compress
   * command.
   */
  @Test
  public void testCompressNegativeValue() {
    String command = "load images/manhattan-small.png man\n" +
            "compress -120 man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if invalid percentage is given in compress
   * command.
   */
  @Test
  public void testCompressInvalidValue() {
    String command = "load images/manhattan-small.png man\n" +
            "compress 120 man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if compress command has
   * incorrect format.
   */
  @Test
  public void testCompressInvalidFormat() {
    String command = "load images/manhattan-small.png man\n" +
            "compress man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check compress throws error when image to be
   * compressed is not present.
   */
  @Test
  public void testCompressWithoutImage() {
    String command = "load images/manhattan-small.png man\n" +
            "compress 90 man3 man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the histogram is
   * called correctly in controller.
   */
  @Test
  public void testHistogram() {
    String command = "load images/manhattan-small.png man\n" +
            "histogram man man-hist";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "histogram executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if histogram command has
   * incorrect format.
   */
  @Test
  public void testHistogramInvalidFormat() {
    String command = "load images/manhattan-small.png man\n" +
            "histogram man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Histogram throws error when image to be
   * compressed is not present.
   */
  @Test
  public void testHistogramWithoutImage() {
    String command = "load images/manhattan-small.png man\n" +
            "histogram man3 man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the color-correct is
   * called correctly in controller.
   */
  @Test
  public void testColorCorrect() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-correct";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "color-correct executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if Color Correct command has
   * incorrect format.
   */
  @Test
  public void testColorCorrectInvalidFormat() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check Color-correct throws error when image to be
   * compressed is not present.
   */
  @Test
  public void testColorCorrectWithoutImage() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man3 man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with color-correct works correctly.
   */
  @Test
  public void testColorCorrectWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level split 50";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "color-correct executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with color-correct works correctly
   * with double value.
   */
  @Test
  public void testColorCorrectWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level split 50.78";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "color-correct executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * color-correct.
   */
  @Test
  public void testColorCorrectWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level split";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * color-correct.
   */
  @Test
  public void testColorCorrectWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level split hundred";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testColorCorrectWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level st 23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testColorCorrectSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testColorCorrectSplitValueMore100() {
    String command = "load images/manhattan-small.png man\n" +
            "color-correct man man-level split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the levels-adjust is
   * called correctly in controller.
   */
  @Test
  public void testLevelsAdjust() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "levels-adjust executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if no value is given in levels adjust.
   */
  @Test
  public void testLevelsAdjustNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 fifty man man-level\n" +
            "levels-adjust twenty 100 200 man man-level \n" +
            "levels-adjust 20 hundred 200 man man-level\n" +
            "levels-adjust twenty fifty hundred man man-level ";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B, M and W value should be Integral values\n" +
            "B, M and W value should be Integral values\n" +
            "B, M and W value should be Integral values\n" +
            "B, M and W value should be Integral values\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if negative values are given in Levels-adjust
   * command.
   */
  @Test
  public void testLevelsAdjustNegativeValue() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust -20 100 255 man man-level \n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B,M and W value must be between 0 and 255\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if invalid values are given in Levels-adjust
   * command.
   */
  @Test
  public void testLevelsAdjustInvalidValue() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 2000 10000 2550000 man man-level\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B,M and W value must be between 0 and 255\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if values are not in
   * ascending order.
   */
  @Test
  public void testLevelsAdjustValueNotAscending() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 100 20 255 man man-level\n";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "B,M and W value must be in ascending order\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if levels-adjust command has
   * incorrect format.
   */
  @Test
  public void testLevelsAdjustInvalidFormat() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 man man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check levels-adjust throws error when image to be
   * processed is not present.
   */
  @Test
  public void testLevelsAdjustWithoutImage() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man3 man-compress";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with levels-adjust works correctly.
   */
  @Test
  public void testLevelWithSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level split 50";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "levels-adjust executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with levels-adjust works correctly
   * with double percentage.
   */
  @Test
  public void testLevelWithSplitDouble() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level split 50.89";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "levels-adjust executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with no value
   * levels-adjust.
   */
  @Test
  public void testLevelWithSplitNoValue() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level split";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid command length.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if split with not numeric value
   * levels-adjust.
   */
  @Test
  public void testLevelWithSplitNoNumValue() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level split hundred";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be a number.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if error thrown is word is not split.
   */
  @Test
  public void testLevelWithNoSplit() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level st 23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Invalid Command\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is less than zero.
   */
  @Test
  public void testLevelSplitInvalid() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level split -23";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check if the percentage is grater than a hundred.
   */
  @Test
  public void testLevelSplitInvalidValue() {
    String command = "load images/manhattan-small.png man\n" +
            "levels-adjust 20 100 255 man man-level split 123";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "Percentage must be between 0 and 100.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with blur.
   */
  @Test
  public void testBlurMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "blur man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "blur executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with blur
   * when mask image is not present.
   */
  @Test
  public void testBlurMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "blur man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with blur
   * when mask image is smaller.
   */
  @Test
  public void testBlurMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "blur man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with blur
   * when mask image is bigger.
   */
  @Test
  public void testBlurMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "blur man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sharpen.
   */
  @Test
  public void testSharpenMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "sharpen man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "sharpen executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sharpen
   * when mask image is not present.
   */
  @Test
  public void testSharpMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "sharpen man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sharpen
   * when mask image is smaller.
   */
  @Test
  public void testSharpenMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "sharpen man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sharpen
   * when mask image is bigger.
   */
  @Test
  public void testSharpenMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "sharpen man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with Sepia.
   */
  @Test
  public void testSepiaMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "sepia man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "sepia executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sepia
   * when mask image is not present.
   */
  @Test
  public void testSepiaMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "sepia man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sepia
   * when mask image is smaller.
   */
  @Test
  public void testSepiaMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "sepia man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with sepia
   * when mask image is bigger.
   */
  @Test
  public void testSepiaMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "blur man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * red-component.
   */
  @Test
  public void testRedComponentMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "red-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "red-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with red-component
   * when mask image is not present.
   */
  @Test
  public void testRedCompMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "red-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with red-
   * -component when mask image is smaller.
   */
  @Test
  public void testRedCompMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "red-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * red-component when mask image is bigger.
   */
  @Test
  public void testRedCompMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "red-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * green-component.
   */
  @Test
  public void testGreenComponentMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "green-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "green-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with green-component
   * when mask image is not present.
   */
  @Test
  public void testGreenCompMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "green-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with green-
   * -component when mask image is smaller.
   */
  @Test
  public void testGreenCompMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "green-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * green-component when mask image is bigger.
   */
  @Test
  public void testGreenCompMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "green-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * blue-component.
   */
  @Test
  public void testBlueComponentMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "blue-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "blue-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with blue-component
   * when mask image is not present.
   */
  @Test
  public void testBlueCompMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "blue-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with blue-
   * -component when mask image is smaller.
   */
  @Test
  public void testBlueCompMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "blue-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * blue-component when mask image is bigger.
   */
  @Test
  public void testBlueCompMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "blue-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * luma-component.
   */
  @Test
  public void testLumaComponentMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "luma-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "luma-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with luma-component
   * when mask image is not present.
   */
  @Test
  public void testLumaCompMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "luma-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with luma-
   * -component when mask image is smaller.
   */
  @Test
  public void testLumaCompMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "luma-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * luma-component when mask image is bigger.
   */
  @Test
  public void testLumaCompMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "luma-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * value-component.
   */
  @Test
  public void testValueComponentMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "value-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "value-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with value-component
   * when mask image is not present.
   */
  @Test
  public void testValueCompMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "value-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with value-
   * -component when mask image is smaller.
   */
  @Test
  public void testValueCompMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "value-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * value-component when mask image is bigger.
   */
  @Test
  public void testValueCompMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "value-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * intensity-component.
   */
  @Test
  public void testIntensityComponentMask() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/mask.jpg mask\n" +
            "intensity-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "intensity-component executed successfully\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with intensity-component
   * when mask image is not present.
   */
  @Test
  public void testIntensityCompMaskImageNotPresent() {
    String command = "load images/manhattan-small.png man\n" +
            "intensity-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "The image to be processed" +
            " is not present.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with intensity-
   * -component when mask image is smaller.
   */
  @Test
  public void testIntensityCompMaskImageSmall() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskSmall.jpeg mask\n" +
            "intensity-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

  /**
   * Test Case to check masking of the images with
   * intensity-component when mask image is bigger.
   */
  @Test
  public void testIntensityCompMaskImageBig() {
    String command = "load images/manhattan-small.png man\n" +
            "load images/maskBig.jpg mask\n" +
            "intensity-component man mask man-blur";
    Reader in = new StringReader(command);
    controller = new CommandReader(opr, in, v);
    controller.startApplication();
    String output = out.toString();
    String expected = cmdload + "load executed successfully\n" +
            "The Dimensions of mask and the image " +
            "to be processed are not same.\n";
    assertEquals(expected, output);
  }

}