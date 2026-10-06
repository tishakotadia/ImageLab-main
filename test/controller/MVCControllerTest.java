package controller;

import org.junit.Before;
import org.junit.Test;


import model.OperationsV3;
import view.IView;

import static org.junit.Assert.assertEquals;

/**
 * Class to perform all the testing related
 * to the MVC controller package.
 */

public class MVCControllerTest {
  IView view;
  OperationsV3 model;
  StringBuilder log;
  ImageAppController controller;
  Features f;

  /**
   * The Setup method called everytime a test is run.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    model = new ImageOperationsMock(log);
    view = new GuiViewMock(log);
    controller = new MVCController(model, view);
    f = new ImageApplicationFeatures(model, view);
  }

  /**
   * Test case to check addFeatures method is called correctly.
   */
  @Test
  public void testAddFeaturesTesting() {
    controller.startApplication();
    String expected = "Features object added to the action listeners of the buttons";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check load method is called correctly
   * from the ImageApplicationFeature to the GUIBased view method.
   */
  @Test
  public void testLoadImage() {
    f.loadImage();
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check the save method from the controller passes
   * correct info to GUI view method.
   */
  @Test
  public void testSaveCorrectCall() {
    f.loadImage();
    f.saveImage("manhattan-smallhist");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "getSaveFilePath called successfully.\n" +
            "Save image manhattan-smallhist\n" +
            "Success Message Displayed.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * save is called when no image is loaded.
   */
  @Test
  public void showErrorSave() {
    f.saveImage(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyBlur method passes correct info to method
   * in GUI and blur-pop gets open.
   */
  @Test
  public void testApplyBlurCorrectCall() {
    f.loadImage();
    f.applyBlur("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "blur preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "Blur manhattan-small_blur_Split-1921825657 to " +
            "manhattan-small_blur_Split-1921825657\n" +
            "Regain manhattan-small to manhattan-small_blur_Split\n" +
            "Save image manhattan-small_blur_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * blur is called when no image is loaded.
   */
  @Test
  public void testShowErrorBlur() {
    f.applyBlur(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for Blur.
   */
  @Test
  public void testBlurPreview() {
    f.loadImage();
    f.applySplit("50", "blur", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "Blur manhattan_blur_Split8433089 to manhattan_blur_Split8433089\n" +
            "Regain manhattan to manhattan_blur_Split\n" +
            "Save image manhattan_blur_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Blur.
   */
  @Test
  public void negativePercentageBlur() {
    f.loadImage();
    f.applySplit("-60", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater than 100 and
   * preview button is pressed for Blur.
   */
  @Test
  public void invalidPercentageBlur() {
    f.loadImage();
    f.applySplit("160", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Blur.
   */
  @Test
  public void stringPercentageForBlur() {
    f.loadImage();
    f.applySplit("adjbf", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the blurred image is displayed on
   * main screen.
   */
  @Test
  public void blurFinalApply() {
    f.loadImage();
    f.commandGenerator("blur", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Blur manhattan to manhattan_blur\n" +
            "Save image manhattan_blur\n" +
            "Image manhattan_blur displayed correctly.\n" +
            "Histogram manhattan_blur to manhattan_blurhist\n" +
            "Save image manhattan_blurhist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applySepia method passes correct info to method
   * in GUI and sepia-pop gets open.
   */
  @Test
  public void testApplySepiaCorrectCall() {
    f.loadImage();
    f.applySepia("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "sepia preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "Sepia manhattan-small_sepia_Split1183982764 to " +
            "manhattan-small_sepia_Split1183982764\n" +
            "Regain manhattan-small to manhattan-small_sepia_Split\n" +
            "Save image manhattan-small_sepia_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * sepia is called when no image is loaded.
   */
  @Test
  public void testShowErrorSepia() {
    f.applySepia(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for Sepia.
   */
  @Test
  public void testSepiaPreview() {
    f.loadImage();
    f.applySplit("50", "sepia", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "Sepia manhattan_sepia_Split892461746 to manhattan_sepia_Split892461746\n" +
            "Regain manhattan to manhattan_sepia_Split\n" +
            "Save image manhattan_sepia_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Sepia.
   */
  @Test
  public void negativePercentageSepia() {
    f.loadImage();
    f.applySplit("-60", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater than 100 and
   * preview button is pressed for Sepia.
   */
  @Test
  public void invalidPercentageSepia() {
    f.loadImage();
    f.applySplit("160", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Sepia.
   */
  @Test
  public void stringPercentageForSepia() {
    f.loadImage();
    f.applySplit("adjbf", "blur", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the sepia image is displayed on
   * main screen.
   */
  @Test
  public void sepiaFinalApply() {
    f.loadImage();
    f.commandGenerator("sepia", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Sepia manhattan to manhattan_sepia\n" +
            "Save image manhattan_sepia\n" +
            "Image manhattan_sepia displayed correctly.\n" +
            "Histogram manhattan_sepia to manhattan_sepiahist\n" +
            "Save image manhattan_sepiahist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applySharpen method passes correct info to method
   * in GUI and sharpen-pop gets open.
   */
  @Test
  public void testApplySharpenCorrectCall() {
    f.loadImage();
    f.applySharp("manhattan-smallBlur");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "sharpen preview called successfully.\n" +
            "SplitPreview on manhattan-smallBlur with 50.0\n" +
            "Sharpen manhattan-smallBlur_sharpen_Split799684496 to manhattan-small" +
            "Blur_sharpen_Split799684496\n" +
            "Regain manhattan-smallBlur to manhattan-smallBlur_sharpen_Split\n" +
            "Save image manhattan-smallBlur_sharpen_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * sharpen is called when no image is loaded.
   */
  @Test
  public void testShowErrorSharp() {
    f.applySharp(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for Sharpen.
   */
  @Test
  public void testSharpPreview() {
    f.loadImage();
    f.applySplit("50", "sharpen", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "Sharpen manhattan_sharpen_Split968469711 to manhattan_sharpen_Split968469711\n" +
            "Regain manhattan to manhattan_sharpen_Split\n" +
            "Save image manhattan_sharpen_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Sharpen.
   */
  @Test
  public void negativePercentageSharp() {
    f.loadImage();
    f.applySplit("-60", "sharpen", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater than 100 and
   * preview button is pressed for Sharpen.
   */
  @Test
  public void invalidPercentageSharp() {
    f.loadImage();
    f.applySplit("160", "sharpen", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Sharpen.
   */
  @Test
  public void stringPercentageForSharp() {
    f.loadImage();
    f.applySplit("adjbf", "sharpen", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the sharpen image is displayed on
   * main screen.
   */
  @Test
  public void sharpFinalApply() {
    f.loadImage();
    f.commandGenerator("sharpen", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Sharpen manhattan to manhattan_sharpen\n" +
            "Save image manhattan_sharpen\n" +
            "Image manhattan_sharpen displayed correctly.\n" +
            "Histogram manhattan_sharpen to manhattan_sharpenhist\n" +
            "Save image manhattan_sharpenhist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyHorizontalFlip method passes correct info to method
   * in GUI and the flipped image gets display on the screen.
   */
  @Test
  public void applyHorizontalFlipCorrectCall() {
    f.loadImage();
    f.applyHorizontalFlip("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "HorizontalFlip manhattan-small to manhattan-small_horizontal-flip\n" +
            "Save image manhattan-small_horizontal-flip\n" +
            "Image manhattan-small_horizontal-flip displayed correctly.\n" +
            "Histogram manhattan-small_horizontal-flip to manhattan-small_horizontal-fliphist\n" +
            "Save image manhattan-small_horizontal-fliphist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * horizontal-flip is called when no image is loaded.
   */
  @Test
  public void testShowErrorHorizontalFlip() {
    f.applyHorizontalFlip(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyVerticalFlip method passes correct info to method
   * in GUI and the flipped image gets display on the screen.
   */
  @Test
  public void applyVerticalFlipCorrectCall() {
    f.loadImage();
    f.applyHorizontalFlip("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "HorizontalFlip manhattan-small to manhattan-small_horizontal-flip\n" +
            "Save image manhattan-small_horizontal-flip\n" +
            "Image manhattan-small_horizontal-flip displayed correctly.\n" +
            "Histogram manhattan-small_horizontal-flip to manhattan-small_horizontal-fliphist\n" +
            "Save image manhattan-small_horizontal-fliphist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * vertical-flip is called when no image is loaded.
   */
  @Test
  public void testShowErrorVerticalFlip() {
    f.applyVerticalFlip(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyLuma method passes correct info to method
   * in GUI and luma-pop gets open.
   */
  @Test
  public void applyLumaCorrectCall() {
    f.loadImage();
    f.applyLuma("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "luma-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetBrightnessComponent manhattan-small_luma-component_Split1191784813" +
            " to manhattan-small_luma-component_Split1191784813 luma-component\n" +
            "Regain manhattan-small to manhattan-small_luma-component_Split\n" +
            "Save image manhattan-small_luma-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * Luma is called when no image is loaded.
   */
  @Test
  public void testShowErrorLuma() {
    f.applyLuma(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for Luma.
   */
  @Test
  public void testLumaPreview() {
    f.loadImage();
    f.applySplit("50", "luma-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetBrightnessComponent manhattan_luma-component_Split-1762141657 to" +
            " manhattan_luma-component_Split-1762141657 luma-component\n" +
            "Regain manhattan to manhattan_luma-component_Split\n" +
            "Save image manhattan_luma-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Luma.
   */
  @Test
  public void negativePercentageLuma() {
    f.loadImage();
    f.applySplit("-60", "luma-component", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for Luma.
   */
  @Test
  public void invalidPercentageLuma() {
    f.loadImage();
    f.applySplit("160", "luma-component", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Luma.
   */
  @Test
  public void stringPercentageForLuma() {
    f.loadImage();
    f.applySplit("adjbf", "luma-component", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the luma image is displayed on
   * main screen.
   */
  @Test
  public void lumaFinalApply() {
    f.loadImage();
    f.commandGenerator("luma-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "GetBrightnessComponent manhattan to manhattan_luma-component luma-component\n" +
            "Save image manhattan_luma-component\n" +
            "Image manhattan_luma-component displayed correctly.\n" +
            "Histogram manhattan_luma-component to manhattan_luma-componenthist\n" +
            "Save image manhattan_luma-componenthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyColorCorrect method passes correct info to method
   * in GUI and color-correct-pop gets open.
   */
  @Test
  public void applyColorCorrectCorrectCall() {
    f.loadImage();
    f.applyColorCorrect("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "color-correct preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "ColorCorrection manhattan-small_color-correct_Split-1779365834 to " +
            "manhattan-small_color-correct_Split-1779365834\n" +
            "Regain manhattan-small to manhattan-small_color-correct_Split\n" +
            "Save image manhattan-small_color-correct_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * color-correct is called when no image is loaded.
   */
  @Test
  public void testShowErrorColorCorrect() {
    f.applyColorCorrect(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for
   * Color-Correct.
   */
  @Test
  public void testColorCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "color-correct", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "ColorCorrection manhattan_color-correct_Split-1181917124 to " +
            "manhattan_color-correct_Split-1181917124\n" +
            "Regain manhattan to manhattan_color-correct_Split\n" +
            "Save image manhattan_color-correct_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Color-Correct.
   */
  @Test
  public void negativePercentageColorCorrect() {
    f.loadImage();
    f.applySplit("-60", "color-correct",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for Color-correct.
   */
  @Test
  public void invalidPercentageColorCorrect() {
    f.loadImage();
    f.applySplit("160", "color-correct",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Color-Correct.
   */
  @Test
  public void stringPercentageForColorCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "color-correct",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the color-correct
   * image is displayed on main screen.
   */
  @Test
  public void colorCorrectFinalApply() {
    f.loadImage();
    f.commandGenerator("color-correct", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "ColorCorrection manhattan to manhattan_color-correct\n" +
            "Save image manhattan_color-correct\n" +
            "Image manhattan_color-correct displayed correctly.\n" +
            "Histogram manhattan_color-correct to manhattan_color-correcthist\n" +
            "Save image manhattan_color-correcthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyRed method passes correct info to method
   * in GUI and red-component-pop gets open.
   */
  @Test
  public void applyRedCorrectCall() {
    f.loadImage();
    f.applyRed("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "red-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetColorComponent manhattan-small_red-component_Split2135654967 " +
            "to manhattan-small_red-component_Split2135654967 0\n" +
            "Regain manhattan-small to manhattan-small_red-component_Split\n" +
            "Save image manhattan-small_red-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * red-component is called when no image is loaded.
   */
  @Test
  public void testShowErrorRed() {
    f.applyRed(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for
   * red-component.
   */
  @Test
  public void testRedCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "red-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetColorComponent manhattan_red-component_Split-1561863619" +
            " to manhattan_red-component_Split-1561863619 0\n" +
            "Regain manhattan to manhattan_red-component_Split\n" +
            "Save image manhattan_red-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for red-component.
   */
  @Test
  public void negativePercentageRedCorrect() {
    f.loadImage();
    f.applySplit("-60", "red-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for red-component.
   */
  @Test
  public void invalidPercentageRedCorrect() {
    f.loadImage();
    f.applySplit("160", "red-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for red-component.
   */
  @Test
  public void stringPercentageForRedCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "red-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the red-component image is displayed on
   * main screen.
   */
  @Test
  public void redComponentFinalApply() {
    f.loadImage();
    f.commandGenerator("color-correct", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "ColorCorrection manhattan to manhattan_color-correct\n" +
            "Save image manhattan_color-correct\n" +
            "Image manhattan_color-correct displayed correctly.\n" +
            "Histogram manhattan_color-correct to manhattan_color-correcthist\n" +
            "Save image manhattan_color-correcthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyBlue method passes correct info to method
   * in GUI and blue-component-pop gets open.
   */
  @Test
  public void applyBlueCorrectCall() {
    f.loadImage();
    f.applyBlue("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "blue-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetColorComponent manhattan-small_blue-component_Split1726999274 " +
            "to manhattan-small_blue-component_Split1726999274 2\n" +
            "Regain manhattan-small to manhattan-small_blue-component_Split\n" +
            "Save image manhattan-small_blue-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * blue-component is called when no image is loaded.
   */
  @Test
  public void testShowErrorBlue() {
    f.applyBlue(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for
   * blue-component.
   */
  @Test
  public void testBlueCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "blue-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetColorComponent manhattan_blue-component_Split-1226927196 to " +
            "manhattan_blue-component_Split-1226927196 2\n" +
            "Regain manhattan to manhattan_blue-component_Split\n" +
            "Save image manhattan_blue-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for blue-component.
   */
  @Test
  public void negativePercentageBlueCorrect() {
    f.loadImage();
    f.applySplit("-60", "blue-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for blue-component.
   */
  @Test
  public void invalidPercentageBlueCorrect() {
    f.loadImage();
    f.applySplit("160", "blue-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for blue-component.
   */
  @Test
  public void stringPercentageForBlueCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "blue-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the blue-component image is displayed on
   * main screen.
   */
  @Test
  public void blueComponentFinalApply() {
    f.loadImage();
    f.commandGenerator("blue-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "GetColorComponent manhattan to manhattan_blue-component 2\n" +
            "Save image manhattan_blue-component\n" +
            "Image manhattan_blue-component displayed correctly.\n" +
            "Histogram manhattan_blue-component to manhattan_blue-componenthist\n" +
            "Save image manhattan_blue-componenthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyGreen method passes correct info to method
   * in GUI and green-component-pop gets open.
   */
  @Test
  public void applyGreenCorrectCall() {
    f.loadImage();
    f.applyGreen("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "green-component preview called successfully.\n" +
            "SplitPreview on manhattan-small with 50.0\n" +
            "GetColorComponent manhattan-small_green-component_Split-390606359" +
            " to manhattan-small_green-component_Split-390606359 1\n" +
            "Regain manhattan-small to manhattan-small_green-component_Split\n" +
            "Save image manhattan-small_green-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * green-component is called when no image is loaded.
   */
  @Test
  public void testShowErrorGreen() {
    f.applyBlue(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method passes correct info to method
   * in GUI when the preview button in GUI is pressed for
   * green-component.
   */
  @Test
  public void testGreenCorrectPreview() {
    f.loadImage();
    f.applySplit("50", "green-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 50.0\n" +
            "GetColorComponent manhattan_green-component_Split-1768013713" +
            " to manhattan_green-component_Split-1768013713 1\n" +
            "Regain manhattan to manhattan_green-component_Split\n" +
            "Save image manhattan_green-component_Split\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for green-component.
   */
  @Test
  public void negativePercentageGreenCorrect() {
    f.loadImage();
    f.applySplit("-60", "green-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for green-component.
   */
  @Test
  public void invalidPercentageGreenCorrect() {
    f.loadImage();
    f.applySplit("160", "green-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check split method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for green-component.
   */
  @Test
  public void stringPercentageForGreenCorrect() {
    f.loadImage();
    f.applySplit("adjbf", "green-component",
            "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in pop-up method the apply
   * button is pressed and then the green-component image is displayed on
   * main screen.
   */
  @Test
  public void greenComponentFinalApply() {
    f.loadImage();
    f.commandGenerator("green-component", "manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "GetColorComponent manhattan to manhattan_green-component 1\n" +
            "Save image manhattan_green-component\n" +
            "Image manhattan_green-component displayed correctly.\n" +
            "Histogram manhattan_green-component to manhattan_green-componenthist\n" +
            "Save image manhattan_green-componenthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyLevelAdjust method passes correct info to method
   * in GUI and levels-adjust-pop-up gets open.
   */
  @Test
  public void applyLevelAdjustCorrectCall() {
    f.loadImage();
    f.applyLevelAdjust("manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Level-Adjustment split frame called successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyLevelAdjust throws error and display in view when
   * levels-adjust is called when no image is loaded.
   */
  @Test
  public void testShowErrorLevelAdjust() {
    f.applyLevelAdjust(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check getLevelAdjust throws error and display in view when
   * in level-adjust no B,M and W values are not provided.
   */
  @Test
  public void testLevelAdjustValueNotGiven() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", " ", " ", " ", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be provided and all must be whole value.";
    assertEquals(temp, log.toString());
  }

  /**
   * Test case to check getLevelAdjust throws error and display in view when
   * in level-adjust no B,M and W values are not integers.
   */
  @Test
  public void testLevelAdjustValueNotInteger() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "afbf", "skfba", "ajfbsf",
            "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be provided and all must be whole value.";
    assertEquals(temp, log.toString());
  }

  /**
   * Test case to check getLevelAdjust throws error and display in view when
   * in level-adjust no B,M and W values are less then 0.
   */
  @Test
  public void testLevelAdjustNotInRangeNegative() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "-123", "-255", "-2", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be between 0 to 255";
    assertEquals(temp, log.toString());
  }

  /**
   * Test case to check getLevelAdjust passes correct info
   * to GUI when preview button in GUI is presses.
   */
  @Test
  public void testLevelAdjust() {
    f.loadImage();
    f.getLevelAdjust("manhattan", "23", "45", "100", "70");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "SplitPreview on manhattan with 70.0\n" +
            "LevelAdjustment manhattan_adjusted-23637811 to" +
            " manhattan_adjusted-23637811 with 23 45 100\n" +
            "Regain manhattan to manhattan_adjusted\n" +
            "Save image manhattan_adjusted\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check getLevelAdjust throws error and display in view when
   * in level-adjust no B,M and W values are more then 255.
   */
  @Test
  public void testLevelAdjustNotInRange() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "1230", "2550", "2000", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "All values (B, M, and W) must be between 0 to 255";
    assertEquals(temp, log.toString());
  }

  /**
   * Test case to check getLevelAdjust method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Levels-adjust.
   */
  @Test
  public void negativePercentageLevelAdjust() {
    f.loadImage();
    f.getLevelAdjust("manhattan", "60", "100", "240", "-123");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check getLevelAdjust method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for Levels-adjust.
   */
  @Test
  public void invalidPercentageLevelAdjustCorrect() {
    f.loadImage();
    f.getLevelAdjust("manhattan", "60", "100", "240", "123");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check getLevelAdjust method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Levels-adjust.
   */
  @Test
  public void stringPercentageForLevelAdjustCorrect() {
    f.loadImage();
    f.getLevelAdjust("manhattan", "60", "100", "240", "afk");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check getLevelAdjust throws error  and display in view when
   * in level-adjust no B,M and W values are not ascending.
   */
  @Test
  public void testLevelAdjustNotInAscending() {
    f.loadImage();
    f.getLevelAdjust("manhattan-small", "123", "25", "20", "50");
    String temp = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Value of B,M and W must be in ascending order (B < M < W)";
    assertEquals(temp, log.toString());
  }

  /**
   * Test case to check when in leveld-adjust-pop-up the apply
   * button is pressed and then the levels-adjust image is displayed on
   * main screen.
   */
  @Test
  public void testLevelAdjustApply() {
    f.loadImage();
    f.generateLevelAdjust("24", "50", "100", "manhattan-small");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "LevelAdjustment manhattan-small to manhattan-small_levels-adjust with 24 50 100\n" +
            "Save image manhattan-small_levels-adjust\n" +
            "Image manhattan-small_levels-adjust displayed correctly.\n" +
            "Histogram manhattan-small_levels-adjust to manhattan-small_levels-adjusthist\n" +
            "Save image manhattan-small_levels-adjusthist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyCompress method passes correct info to method
   * in GUI and compress-pop-up gets open.
   */
  @Test
  public void testApplyCompress() {
    f.loadImage();
    f.applyCompress("manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Compress split frame called successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyComprees throws error and it is display in view when
   * compress is called when no image is loaded.
   */
  @Test
  public void testCompressError() {
    f.applyCompress(null);
    String expected = "\nImage not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method passes correct info to method
   * in GUI when the preview button in GUI is pressed for compress.
   */
  @Test
  public void testGenerateCompress() {
    f.loadImage();
    f.generateCompress("89", "manhattan",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Compress manhattan to manhattan_Compress\n" +
            "Save image manhattan_Compress\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method throws error message to
   * get display in view when percentage value is non-numeric and
   * preview button is pressed for Compress.
   */
  @Test
  public void testCompressInvalidPercentageString() {
    f.loadImage();
    f.generateCompress("ajbf", "manhattan",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method throws error message to
   * get display in view when percentage value is negative and
   * preview button is pressed for Compress.
   */
  @Test
  public void testCompressInvalidPercentageNegative() {
    f.loadImage();
    f.generateCompress("-100", "manhattan",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method throws error message to
   * get display in view when percentage value is greater then 100 and
   * preview button is pressed for Compress.
   */
  @Test
  public void testCompressInvalidPercentagePositive() {
    f.loadImage();
    f.generateCompress("900", "manhattan",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method throws error message to
   * get display in view when percentage value is greater then 100.
   */
  @Test
  public void testCompressInvalidPercentagePositiveFinal() {
    f.loadImage();
    f.generateCompress("1000", "manhattan",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method throws error message to
   * get display in view when percentage value is non-numeric.
   */
  @Test
  public void testCompressInvalidPercentageStringFinal() {
    f.loadImage();
    f.generateCompress("ajbf", "manhattan",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateCompress method throws error message to
   * get display in view when percentage value is negative.
   */
  @Test
  public void testCompressInvalidPercentageNegativeFinal() {
    f.loadImage();
    f.generateCompress("-100", "manhattan",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Error message displayed successfully.\n" +
            "Provide valid value of percentage.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in compress-pop-up method the apply
   * button is pressed and then the compress image is displayed on
   * main screen.
   */
  @Test
  public void testCompressApplyFinal() {
    f.loadImage();
    f.generateCompress("50", "manhattan",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Compress manhattan to manhattan_compress\n" +
            "Save image manhattan_compress\n" +
            "Image manhattan_compress displayed correctly.\n" +
            "Histogram manhattan_compress to manhattan_compresshist\n" +
            "Save image manhattan_compresshist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check applyDownScale method passes correct info to method
   * in GUI and down-scale-pop-up gets open.
   */
  @Test
  public void testApplyDownScale() {
    f.loadImage();
    f.applyDownScale("manhattan");
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Downscale split frame called successfully.\n" +
            "with current height and width are 3 3";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check error thrown and display in view when
   * applyDownScale is called when no image is loaded.
   */
  @Test
  public void testDownScaleWithoutImage() {
    f.applyDownScale(null);
    String expected = "\n" +
            "Image not present error displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method passes correct info to method
   * in GUI when the preview button in GUI is pressed for DownScale
   * and the preview image gets displayed.
   */
  @Test
  public void testGenerateDown() {
    f.loadImage();
    f.generateDown("manhattan", "2", "2",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "DownScale manhattan to 2\n" +
            "Save image manhattan_DownScale\n" +
            "Split Image displayed successfully.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height is greater then current image and
   * preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithBiggerHeight() {
    f.loadImage();
    f.generateDown("manhattan", "2", "4",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when width is greater then current image and
   * preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithBiggerWidth() {
    f.loadImage();
    f.generateDown("manhattan", "4", "2",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when width and height is greater
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithBiggerWidthAndHeight() {
    f.loadImage();
    f.generateDown("manhattan", "4", "4",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height is negative
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithNegativeHeight() {
    f.loadImage();
    f.generateDown("manhattan", "2", "-4",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when widht is negative
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithNegativeWidth() {
    f.loadImage();
    f.generateDown("manhattan", "-4", "2",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height and width is negative
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithNegativeWidthAndHeight() {
    f.loadImage();
    f.generateDown("manhattan", "-4", "-4",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height is zero
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithZeroHeight() {
    f.loadImage();
    f.generateDown("manhattan", "2", "0",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when width is negative
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithZeroWidth() {
    f.loadImage();
    f.generateDown("manhattan", "0", "2",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height and width is negative
   * then current image and preview button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithZeroWidthAndHeight() {
    f.loadImage();
    f.generateDown("manhattan", "0", "0",true);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check when in down-scale-pop-up the apply
   * button is pressed and then the downscaled image is displayed on
   * main screen.
   */
  @Test
  public void generateDownScaleApplyButton() {
    f.loadImage();
    f.generateDown("manhattan", "2", "2",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "DownScale manhattan to 2\n" +
            "Save image manhattan_DownScale\n" +
            "Image manhattan_DownScale displayed correctly.\n" +
            "Histogram manhattan_DownScale to manhattan_DownScalehist\n" +
            "Save image manhattan_DownScalehist\n" +
            "Histogram of image showed correctly";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height is greater then current image and
   * apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithBiggerHeightApplyButton() {
    f.loadImage();
    f.generateDown("manhattan", "2", "4",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when width is greater then current image and
   * apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithBiggerWidthApply() {
    f.loadImage();
    f.generateDown("manhattan", "4", "2",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when width and height is greater
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithBiggerWidthAndHeightApply() {
    f.loadImage();
    f.generateDown("manhattan", "4", "4",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height is negative
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithNegativeHeightApply() {
    f.loadImage();
    f.generateDown("manhattan", "2", "-4",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when widht is negative
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithNegativeWidthApply() {
    f.loadImage();
    f.generateDown("manhattan", "-4", "2",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height and width is negative
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithNegativeWidthAndHeightApply() {
    f.loadImage();
    f.generateDown("manhattan", "-4", "-4",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height is zero
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithZeroHeightApply() {
    f.loadImage();
    f.generateDown("manhattan", "2", "0",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when width is negative
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithZeroWidthApply() {
    f.loadImage();
    f.generateDown("manhattan", "0", "2",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }

  /**
   * Test case to check generateDown method throws error message to
   * get display in view when height and width is negative
   * then current image and apply button is pressed for Sharpen.
   */
  @Test
  public void testGenerateDownWithZeroWidthAndHeightApply() {
    f.loadImage();
    f.generateDown("manhattan", "0", "0",false);
    String expected = "checkImage called successfully.\n" +
            "getFilePath called successfully.Load image manhattan-small\n" +
            "Save image manhattan-small\n" +
            "Image manhattan-small displayed correctly.\n" +
            "Histogram manhattan-small to manhattan-smallhist\n" +
            "Save image manhattan-smallhist\n" +
            "Histogram of image showed correctly\n" +
            "Save image manhattan\n" +
            "Error message displayed successfully.\n" +
            "The height and width must be smaller than the original values and positive integer.";
    assertEquals(expected, log.toString());
  }


}
