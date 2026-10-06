package controller;

import org.junit.Before;

import java.io.Reader;
import java.io.StringReader;

import model.OperationsV3;
import view.ViewHandler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * This is the class to perform the mock testing
 * of the controller using the mock class of the
 * Model which is ImageOperationsV2Mock.
 */

public class ControllerMockTest {
  StringBuffer out;
  Reader in;
  OperationsV3 mock;
  StringBuilder log;
  ImageAppController controller;
  ViewHandler view;

  /**
   * Basic setup before to carry out the test.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    mock = new ImageOperationsMock(log);
    out = new StringBuffer();
    view = new ViewHandler(out);
  }

  /**
   * Test Case to check the Brighten Function is
   * called correctly by the controller.
   */
  @Test
  public void checkBright() {
    in = new StringReader("brighten 50 man man-bright");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nBrighten man to man-bright 50";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Brighten Function is
   * called correctly by the controller for darkening
   * the image.
   */
  @Test
  public void checkDark() {
    in = new StringReader("brighten -50 man man-dark");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nBrighten man to man-dark -50";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Compress Function is
   * called correctly by the controller.
   */
  @Test
  public void checkCompress() {
    in = new StringReader("compress 50 man mancomp");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nCompress man to mancomp";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Histogram function is
   * called correctly by the controller.
   */
  @Test
  public void checkHistogram() {
    in = new StringReader("histogram man manhist");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nHistogram man to manhist";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Color-Correct Function is
   * called correctly by the controller.
   */
  @Test
  public void checkColorCorrection() {
    in = new StringReader("color-correct man mancolor-correct");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nColorCorrection man to mancolor-correct";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Levels-Adjust is
   * called correctly by the controller.
   */
  @Test
  public void checkLevelAdjust() {
    in = new StringReader("levels-adjust 10 20 233 man man-level");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nLevelAdjustment man to man-level with 10 20 233";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Load Function is
   * called correctly by the controller.
   */
  @Test
  public void checkLoadImage() {
    in = new StringReader("load images/manhattan-small.png man");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "Load image man";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the Save Function is
   * called correctly by the controller.
   */
  @Test
  public void checkSaveImage() {
    in = new StringReader("save images/manhattan.png man");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSave image man";
    assertEquals(expected, log.toString());
  }


  /**
   * Test Case to check the red-component function is
   * called correctly by the controller.
   */
  @Test
  public void checkRedComponent() {
    in = new StringReader("red-component man manred");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetColorComponent man to manred 0";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the green-component function is
   * called correctly by the controller.
   */
  @Test
  public void checkGreenComponent() {
    in = new StringReader("green-component man mangreen");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetColorComponent man to mangreen 1";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the blue-component function is
   * called correctly by the controller.
   */
  @Test
  public void checkBlueComponent() {
    in = new StringReader("blue-component man manblue");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetColorComponent man to manblue 2";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the value-component function is
   * called correctly by the controller.
   */
  @Test
  public void checkValueComponent() {
    in = new StringReader("value-component man manvalue");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetBrightnessComponent man to manvalue value-component";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the intensity-component function is
   * called correctly by the controller.
   */
  @Test
  public void checkIntensityComponent() {
    in = new StringReader("intensity-component man manin");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetBrightnessComponent man to manin intensity-component";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the luma-component function is
   * called correctly by the controller.
   */
  @Test
  public void checkLumaComponent() {
    in = new StringReader("luma-component man manluma");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetBrightnessComponent man to manluma luma-component";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the horizontal-flip function is
   * called correctly by the controller.
   */
  @Test
  public void checkHorizontalFlip() {
    in = new StringReader("horizontal-flip man manhf");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nHorizontalFlip man to manhf";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the vertical-flip function is
   * called correctly by the controller.
   */
  @Test
  public void checkVerticalFlip() {
    in = new StringReader("vertical-flip man manvf");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nVerticalFlip man to manvf";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the split-RGB function is
   * called correctly by the controller.
   */
  @Test
  public void checkSplitRGB() {
    in = new StringReader("rgb-split man manr mang manb");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplit RBG man to manr mang manb";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the rgb-combine function is
   * called correctly by the controller.
   */
  @Test
  public void checkCombineRGB() {
    in = new StringReader("rgb-combine man manr mang manb");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nCombineRGB manr mang manb to man";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the blur function is
   * called correctly by the controller.
   */
  @Test
  public void checkBlur() {
    in = new StringReader("blur man manblur");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nBlur man to manblur";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the sharpen function is
   * called correctly by the controller.
   */
  @Test
  public void checkSharpen() {
    in = new StringReader("sharpen man mansharp");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSharpen man to mansharp";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check the sepia function is
   * called correctly by the controller.
   */
  @Test
  public void checkSepia() {
    in = new StringReader("sepia man mansepia");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSepia man to mansepia";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the blur image is called correctly by
   * controller.
   */
  @Test
  public void checkBlurSplit() {
    in = new StringReader("blur man man-blur split 30");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 30.0" +
            "\nBlur man-blur75101434 to man-blur75101434" +
            "\nRegain man to man-blur";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the sharp image is called correctly by
   * controller.
   */
  @Test
  public void checkSharpSplit() {
    in = new StringReader("sharpen man man-sharp split 50.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 50.98" +
            "\nSharpen man-sharp-1951261257 to man-sharp-1951261257" +
            "\nRegain man to man-sharp";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the sepia image is called correctly by
   * controller.
   */
  @Test
  public void checkSepiaSplit() {
    in = new StringReader("sepia man man-sepia split 0.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 0.98" +
            "\nSepia man-sepia-1951336509 to man-sepia-1951336509" +
            "\nRegain man to man-sepia";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the red-component image is called correctly by
   * controller.
   */
  @Test
  public void checkRedComponentSplit() {
    in = new StringReader("red-component man man-red split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nGetColorComponent man-red833721758 to man-red833721758 0" +
            "\nRegain man to man-red";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the green-component image is called correctly by
   * controller.
   */
  @Test
  public void checkGreenComponentSplit() {
    in = new StringReader("green-component man man-green split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nGetColorComponent man-green-1962042160 to man-green-1962042160 1" +
            "\nRegain man to man-green";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the blue-component image is called correctly by
   * controller.
   */
  @Test
  public void checkBlueComponentSplit() {
    in = new StringReader("blue-component man man-blue split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nGetColorComponent man-blue75101421 to man-blue75101421 2" +
            "\nRegain man to man-blue";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the value-component image is called correctly by
   * controller.
   */
  @Test
  public void checkValueComponentSplit() {
    in = new StringReader("value-component man man-value split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nGetBrightnessComponent man-value-1948688578 to man-value-1948688578" +
            " value-component" +
            "\nRegain man to man-value";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the intensity-component image is called correctly by
   * controller.
   */
  @Test
  public void checkIntensityComponentSplit() {
    in = new StringReader("intensity-component man man-in split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nGetBrightnessComponent man-in-1081484680 to man-in-1081484680 " +
            "intensity-component\n" +
            "Regain man to man-in";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the luma-component image is called correctly by
   * controller.
   */
  @Test
  public void checkLumaComponentSplit() {
    in = new StringReader("luma-component man man-luma split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98\n" +
            "GetBrightnessComponent man-luma75407728 to man-luma75407728" +
            " luma-component\n" +
            "Regain man to man-luma";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the levels-adjust operation
   * on the luma-component image is called correctly by
   * controller.
   */
  @Test
  public void checkLevelsAdjustSplit() {
    in = new StringReader("levels-adjust 10 200 255 man man-luma split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nLevelAdjustment man-luma75407728 to man-luma75407728 with 10 200 255\n" +
            "Regain man to man-luma";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the Split operation
   * on the color-correct image is called correctly by
   * controller.
   */
  @Test
  public void checkColorCorrectSplit() {
    in = new StringReader("color-correct man man-luma split 19.98");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSplitPreview on man with 19.98" +
            "\nColorCorrection man-luma75407728 to man-luma75407728\n" +
            "Regain man to man-luma";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the blur image is called correctly by
   * controller.
   */
  @Test
  public void checkBlurMask() {
    in = new StringReader("blur man mask man-mask-blur");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nBlur man to man-mask-blur1567088117\n" +
            "Mask on man to get man-mask-blur";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the sharpen image is called correctly by
   * controller.
   */
  @Test
  public void checkSharpenMask() {
    in = new StringReader("sharpen man mask man-mask-sharpen");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSharpen man to man-mask-sharpen897370565\n" +
            "Mask on man to get man-mask-sharpen";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the sepia image is called correctly by
   * controller.
   */
  @Test
  public void checkSepiaMask() {
    in = new StringReader("sepia man mask man-mask-sepia");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nSepia man to man-mask-sepia1350577704\n" +
            "Mask on man to get man-mask-sepia";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the red-component image is called correctly by
   * controller.
   */
  @Test
  public void checkRedComponentMask() {
    in = new StringReader("red-component man mask man-mask-red");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetColorComponent man to man-mask-red-919264957 0\n" +
            "Mask on man to get man-mask-red";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the green-component image is called correctly by
   * controller.
   */
  @Test
  public void checkGreenComponentMask() {
    in = new StringReader("green-component man mask man-mask-green");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetColorComponent man to man-mask-green1339872053 1\n" +
            "Mask on man to get man-mask-green";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the blue-component image is called correctly by
   * controller.
   */
  @Test
  public void checkBlueComponentMask() {
    in = new StringReader("blue-component man mask man-mask-blue");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetColorComponent man to man-mask-blue1567088104 2\n" +
            "Mask on man to get man-mask-blue";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the value-component image is called correctly by
   * controller.
   */
  @Test
  public void checkValueComponentMask() {
    in = new StringReader("value-component man mask man-mask-v");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetBrightnessComponent man to man-mask-v-537268824" +
            " value-component\n" +
            "Mask on man to get man-mask-v";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the luma-component image is called correctly by
   * controller.
   */
  @Test
  public void checkLumaComponentMask() {
    in = new StringReader("luma-component man mask man-mask-luma");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetBrightnessComponent man to man-mask-luma1567394411 " +
            "luma-component\n" +
            "Mask on man to get man-mask-luma";
    assertEquals(expected, log.toString());
  }

  /**
   * Test Case to check if the mask operation
   * on the intensity-component image is called correctly by
   * controller.
   */
  @Test
  public void checkIntensityComponentMask() {
    in = new StringReader("intensity-component man mask man-mask-intensity");
    controller = new CommandReader(mock, in, view);
    controller.startApplication();
    String expected = "\nGetBrightnessComponent man to man-mask-intensity1155380069 " +
            "intensity-component" +
            "\nMask on man to get man-mask-intensity";
    assertEquals(expected, log.toString());
  }

}
