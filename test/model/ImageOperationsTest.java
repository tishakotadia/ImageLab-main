package model;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This is the Class to perform testing on the
 * model of the application.
 */

public class ImageOperationsTest {
  OperationsV3 operations;

  /**
   * The setup needed to carry out the test.
   */
  @Before
  public void setUp() {
    this.operations = new ImageOperationsV3();
    String file = "images/manhattan-small.png";
    String f = "images/galaxy.png";
    String mask_path = "images/mask.jpg";
    int[][][] org = this.getMatrix(file);
    int[][][] org1 = this.getMatrix(f);
    int[][][] maskMatrix = this.getMatrix(mask_path);
    this.operations.loadImage(maskMatrix, "mask");
    this.operations.loadImage(org, "man");
    this.operations.loadImage(org1, "galaxy");
    int[][][] temp = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}}, {{4, 5, 6}, {4, 5, 6}, {4, 5, 6}},
            {{7, 8, 9}, {7, 8, 9}, {7, 8, 9}}, {{10, 11, 12}, {10, 11, 12}, {10, 11, 12}}
    };
    this.operations.loadImage(temp, "matrix");

  }

  /**
   * Method to get the matrix representation of an input
   * image.
   *
   * @param file the file path where image is present.
   * @return a 3-d matrix representing the image.
   */
  private int[][][] getMatrix(String file) {
    File f = new File(file);
    int[][][] arr = null;
    try {
      BufferedImage img = ImageIO.read(f);
      int height = img.getHeight();
      int width = img.getWidth();
      arr = new int[height][width][3];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = img.getRGB(j, i);
          arr[i][j][0] = (rgb >> 16) & 0xff;
          arr[i][j][1] = (rgb >> 8) & 0xff;
          arr[i][j][2] = (rgb) & 0xff;
        }
      }
    } catch (IOException e) {
      System.out.println("File Not Found");

    }
    return arr;
  }

  /**
   * Test Case to check if image gets brighten
   * with a suitable value.
   */
  @Test
  public void checkBrightness() {
    this.operations.brighten("man", "man-bright-50", 50);
    int[][][] manNew = this.operations.saveImage("man-bright-50");
    String file = "images" +
            "/manhattan-small-brighter-by-50.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image gets brighten
   * with a zero.
   */
  @Test
  public void checkBrightnessZero() {
    this.operations.brighten("man", "man-bright-50", 0);
    int[][][] manNew = this.operations.saveImage("man-bright-50");
    int[][][] compare = this.operations.saveImage("man");
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image get darken when a
   * suitable negative value is given to the brightness
   * method.
   */
  @Test
  public void checkDarkeness() {
    this.operations.brighten("man", "man-darken-50", -50);
    int[][][] manNew = this.operations.saveImage("man-darken-50");
    String file2 = "images" +
            "/manhattan-small-darker-by-50.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image gets vertically
   * flipped correctly or not.
   */
  @Test
  public void checkVerticalFlip() {
    this.operations.verticalFlip("man", "man-vflip");
    int[][][] manNew = this.operations.saveImage("man-vflip");
    String file2 = "images" +
            "/manhattan-small-vertical.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image gets horizontally
   * flipped correctly or not.
   */
  @Test
  public void checkHorizontalFlip() {
    this.operations.horizontalFlip("man", "man-hflip");
    int[][][] manNew = this.operations.saveImage("man-hflip");
    String file2 = "images" +
            "/manhattan-small-horizontal.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image get vertical flip
   * of a horizontal flipped image.
   */
  @Test
  public void checkHorizontalVerticalFlip() {
    this.operations.horizontalFlip("man", "man-hflip");
    this.operations.verticalFlip("man-hflip", "man-hv");
    int[][][] manNew = this.operations.saveImage("man-hv");
    String file2 = "images" +
            "/manhattan-small-horizontal-vertical.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if image get horizontal flip
   * of a vertically flipped image.
   */
  @Test
  public void checkVerticalHorizontalFlip() {
    this.operations.verticalFlip("man", "man-vflip");
    this.operations.horizontalFlip("man-vflip", "man-vh");
    int[][][] manNew = this.operations.saveImage("man-vh");
    String file = "images" +
            "/manhattan-small-vertical-horizontal.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check sepia transformation of
   * image.
   */
  @Test
  public void checkSepia() {
    this.operations.sepia("man", "man-sepia");
    int[][][] manNew = this.operations.saveImage("man-sepia");
    String file = "images" +
            "/manhattan-small-sepia.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check blur operation applied
   * on the image.
   */
  @Test
  public void checkBlur() {
    this.operations.blur("man", "man-blur");
    int[][][] manNew = this.operations.saveImage("man-blur");
    String file = "images" +
            "/manhattan-small-blur.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if double blur is
   * applied on the image.
   */
  @Test
  public void checkDoubleBlur() {
    this.operations.blur("man", "man-blur");
    this.operations.blur("man-blur", "man-blur");
    int[][][] manNew = this.operations.saveImage("man-blur");
    String file = "images" +
            "/manhattan-small-blur-2.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check Sharpen operation applied
   * on the image.
   */
  @Test
  public void checkSharpen() {
    this.operations.sharpen("man", "man-sharp");
    int[][][] manNew = this.operations.saveImage("man-sharp");
    String file = "images" +
            "/manhattan-small-sharper.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if double sharp is
   * applied correctly.
   */
  @Test
  public void checkDoubleSharpen() {
    this.operations.sharpen("man", "man-sharp");
    this.operations.sharpen("man-sharp", "man-sharp");
    int[][][] manNew = this.operations.saveImage("man-sharp");
    String file = "images" +
            "/manhattan-small-sharpen-2.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if value component
   * of image is generated correctly.
   */
  @Test
  public void checkValueComponent() {
    this.operations.getBrightnessComponent("man",
            "man-value", "value-component");
    int[][][] manNew = this.operations.saveImage("man-value");
    String file = "images" +
            "/manhattan-small-value-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if luma component
   * of image is generated correctly.
   */
  @Test
  public void checkLumaComponent() {
    this.operations.getBrightnessComponent("man",
            "man-luma", "luma-component");
    int[][][] manNew = this.operations.saveImage("man-luma");
    String file = "images" +
            "/manhattan-small-luma-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check the intensity function
   * is working correctly or not.
   */

  @Test
  public void checkIntensityComponent() {
    this.operations.getBrightnessComponent("man",
            "man-intensity", "intensity-component");
    int[][][] manNew = this.operations.saveImage("man-intensity");
    String file = "images" +
            "/manhattan-small-intensity-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = (compare[i][j][k] - manNew[i][j][k]);
          if (diff == -1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to get the red component from the image.
   */
  @Test
  public void checkRedComponent() {
    this.operations.getColorComponent("man",
            "man-red", 0);
    int[][][] manNew = this.operations.saveImage("man-red");
    String file = "images" +
            "/manhattan-small-red.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to get the blue component from the image.
   */
  @Test
  public void checkBlueComponent() {
    this.operations.getColorComponent("man",
            "man-blue", 2);
    int[][][] manNew = this.operations.saveImage("man-blue");
    String file = "images" +
            "/manhattan-small-blue.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to get the green component from the image.
   */
  @Test
  public void checkGreenComponent() {
    this.operations.getColorComponent("man",
            "man-green", 1);
    int[][][] manNew = this.operations.saveImage("man-green");
    String file = "images" +
            "/manhattan-small-green.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the rgb split
   * of image takes place correctly.
   */
  @Test
  public void checkRGBSplit() {
    this.operations.splitRGB("man",
            "man-red", "man-green", "man-blue");
    int[][][] manNew = this.operations.saveImage("man-red");
    String file = "images" +
            "/manhattan-small-red.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
    manNew = this.operations.saveImage("man-green");
    file = "images" +
            "/manhattan-small-green.png";
    compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }

    manNew = this.operations.saveImage("man-blue");
    file = "images" +
            "/manhattan-small-blue.png";
    compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the RGB Combine
   * works correctly.
   */
  @Test
  public void checkRGBCombine() {
    this.operations.splitRGB("man",
            "man-red", "man-green", "man-blue");
    this.operations.combineRGB("man-red", "man-green",
            "man-blue", "manNew");
    int[][][] manNew = this.operations.saveImage("manNew");
    String file = "images" +
            "/manhattan-small.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the RGB Combine
   * throws error if image are of different size.
   */
  @Test(expected = IllegalArgumentException.class)
  public void checkRGBCombineWithDifferentSize() {
    this.operations.splitRGB("man",
            "man-red", "man-green", "man-blue");
    String file1 = "images" +
            "/galaxy.png";
    int[][][] newImg = this.getMatrix(file1);
    this.operations.loadImage(newImg, "bird");
    this.operations.splitRGB("bird", "bird-red",
            "bird-green", "bird-blue");
    this.operations.combineRGB("bird-blue", "man-green",
            "man-blue", "new-Image");
  }

  /**
   * Test Case to check if the image override
   * when loaded with same name.
   */
  @Test
  public void checkOverridingOfImage() {
    String file1 = "images" +
            "/galaxy.png";
    int[][][] newImg = this.getMatrix(file1);
    this.operations.loadImage(newImg, "man");
    int[][][] reference = this.getMatrix(file1);
    int[][][] manNew = this.operations.saveImage("man");
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(reference[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the color-correction
   * of the image is carried out correctly.
   */
  @Test
  public void checkColorCorrect() {
    this.operations.colorCorrection("galaxy",
            "galaxy-correct");
    int[][][] newImg = this.operations.saveImage("galaxy-correct");
    String file2 = "images//galaxy-corrected.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < newImg.length; i++) {
      for (int j = 0; j < newImg[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], newImg[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the level adjust
   * is carried out correctly.
   */
  @Test
  public void checkLevelAdjust() {
    this.operations.levelAdjustment("galaxy",
            "galaxy-level", 20, 100, 255);
    int[][][] newImg = this.operations.saveImage("galaxy-level");
    String file2 = "images/galaxy-adjusted.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < newImg.length; i++) {
      for (int j = 0; j < newImg[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = Math.abs(compare[i][j][k] - newImg[i][j][k]);
          if (diff <= 1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to check the color correction
   * on the level adjust.
   */
  @Test
  public void checkColorCorrectLevelAdjust() {
    this.operations.levelAdjustment("galaxy", "level", 20,
            100, 255);
    this.operations.colorCorrection("level", "correct");
    int[][][] newImg = this.operations.saveImage("correct");
    String file2 = "images/galaxy-adjusted-color-corrected.png";
    int[][][] compare = this.getMatrix(file2);
    for (int i = 0; i < newImg.length; i++) {
      for (int j = 0; j < newImg[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = Math.abs(compare[i][j][k] - newImg[i][j][k]);
          if (diff <= 1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to check if the compression works correct
   * if the compression ratio is 0.
   */
  @Test
  public void compressionWithZero() {
    this.operations.compressImage("man", "man-comp", 0);
    int[][][] newImg = this.operations.saveImage("man-comp");
    int[][][] compare = this.operations.saveImage("man");
    for (int i = 0; i < newImg.length; i++) {
      for (int j = 0; j < newImg[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = Math.abs(compare[i][j][k] - newImg[i][j][k]);
          if (diff <= 1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to check if the compression works correct
   * with a suitable compression value.
   */
  @Test
  public void checkCompression() {
    int[][][] m = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}},
            {{4, 5, 6}, {4, 5, 6}, {4, 5, 6}},
            {{7, 8, 9}, {7, 8, 9}, {7, 8, 9}}
    };
    this.operations.loadImage(m, "temp");
    this.operations.compressImage("temp",
            "temp-compress", 50);
    int[][][] actual = this.operations.saveImage("temp-compress");
    int[][][] expected = new int[][][]{
            {{1, 3, 4}, {1, 3, 4}, {2, 3, 4}},
            {{3, 3, 4}, {3, 3, 4}, {4, 3, 4}},
            {{4, 6, 7}, {4, 6, 7}, {8, 8, 10}}
    };

    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(expected[i][j][k], actual[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the compression works correct
   * with a suitable compression value.
   */
  @Test
  public void checkCompressionWithDouble() {
    int[][][] m = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}},
            {{4, 5, 6}, {4, 5, 6}, {4, 5, 6}},
            {{7, 8, 9}, {7, 8, 9}, {7, 8, 9}}
    };
    this.operations.loadImage(m, "temp");
    this.operations.compressImage("temp",
            "temp-compress", 70.89);
    int[][][] actual = this.operations.saveImage("temp-compress");
    int[][][] expected = new int[][][]{
            {{2, 2, 3}, {2, 2, 3}, {2, 4, 3}},
            {{2, 2, 3}, {2, 2, 3}, {2, 4, 3}},
            {{4, 5, 3}, {4, 5, 3}, {4, 7, 3}}
    };

    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(expected[i][j][k], actual[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the compression works correct
   * with a 100 compression value.
   */
  @Test
  public void checkCompressionHundred() {
    int[][][] m = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}},
            {{4, 5, 6}, {4, 5, 6}, {4, 5, 6}},
            {{7, 8, 9}, {7, 8, 9}, {7, 8, 9}}
    };
    this.operations.loadImage(m, "temp");
    this.operations.compressImage("temp", "temp-compress", 100);
    int[][][] actual = this.operations.saveImage("temp-compress");
    int[][][] expected = new int[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    assertArrayEquals(expected, actual);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for blur.
   */
  @Test
  public void checkBlurSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.blur(temp, temp);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for sepia.
   */
  @Test
  public void checkSepiaSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.sepia(temp, temp);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for sharp.
   */
  @Test
  public void checkSharpSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.sharpen(temp, temp);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for red-component.
   */
  @Test
  public void checkRedComponentSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.getColorComponent(temp, temp, 0);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for green-component.
   */
  @Test
  public void checkGreenComponentSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.getColorComponent(temp, temp, 1);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for blue-component.
   */
  @Test
  public void checkBlueComponentSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.getColorComponent(temp, temp, 2);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for value-component.
   */
  @Test
  public void checkValueComponentSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.getBrightnessComponent(temp, temp, "value-component");
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for luma-component.
   */
  @Test
  public void checkLumaComponentSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.getBrightnessComponent(temp, temp, "luma-component");
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for intensity-component.
   */
  @Test
  public void checkIntensityComponentSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.getBrightnessComponent(temp, temp, "intensity-component");
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for levels-adjust.
   */
  @Test
  public void checkLevelsAdjustSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.levelAdjustment(temp, temp, 20, 30, 50);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for color-correct.
   */
  @Test
  public void checkColorCorrectSplitZero() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 0);
    this.operations.colorCorrection(temp, temp);
    this.operations.regain("man", name, "man-new");
    int[][][] actual = this.operations.saveImage("man-new");
    int[][][] expected = this.operations.saveImage("man");
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on blur.
   */
  @Test
  public void checkBlurSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.blur(name, name);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-blur.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on Sepia.
   */
  @Test
  public void checkSepiaSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.sepia(name, name);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-sepia.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on sharpen.
   */
  @Test
  public void checkSharpSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.sharpen(name, name);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-sharper.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on red-component.
   */
  @Test
  public void checkRedComponentSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.getColorComponent(name, name, 0);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-red.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on green-component.
   */
  @Test
  public void checkGreenComponentSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.getColorComponent(name, name, 1);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-green.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on blue-component.
   */
  @Test
  public void checkBlueComponentSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.getColorComponent(name, name, 2);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-blue.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on value-component.
   */
  @Test
  public void checkValueComponentSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.getBrightnessComponent(name, name, "value-component");
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-value-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on luma-component.
   */
  @Test
  public void checkLumaComponentSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.getBrightnessComponent(name, name, "luma-component");
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-luma-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(compare[i][j][k], manNew[i][j][k]);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on intensity-component.
   */
  @Test
  public void checkIntensityComponentSplit100() {
    String temp = "man";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("man", name, 100);
    this.operations.getBrightnessComponent(name, name, "intensity-component");
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/manhattan-small-intensity-greyscale.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = Math.abs(compare[i][j][k] - manNew[i][j][k]);
          if (diff <= 1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on levels-adjust.
   */
  @Test
  public void checkLevelsAdjustSplit100() {
    String temp = "galaxy";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("galaxy", name, 100);
    this.operations.levelAdjustment(name, name, 20, 100, 255);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/galaxy-adjusted.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = Math.abs(compare[i][j][k] - manNew[i][j][k]);
          if (diff <= 1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct with 100%
   * on color-correct.
   */
  @Test
  public void checkColorCorrectSplit100() {
    String temp = "galaxy";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("galaxy", name, 100);
    this.operations.colorCorrection(name, name);
    this.operations.regain("man", name, "man-new");
    int[][][] manNew = this.operations.saveImage("man-new");
    String file = "images" +
            "/galaxy-corrected.png";
    int[][][] compare = this.getMatrix(file);
    for (int i = 0; i < manNew.length; i++) {
      for (int j = 0; j < manNew[i].length; j++) {
        for (int k = 0; k <= 2; k++) {
          int diff = Math.abs(compare[i][j][k] - manNew[i][j][k]);
          if (diff <= 1) {
            diff = 0;
          }
          assertEquals(0, diff);
        }
      }
    }
  }

  /**
   * Test Case to check if the split works correct
   * for blur.
   */
  @Test
  public void checkBlurSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 20);
    this.operations.blur(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 1, 2}, {1, 2, 3}, {1, 1, 2}}, {{3, 3, 4}, {4, 5, 6}, {3, 3, 4}},
            {{5, 6, 6}, {7, 8, 9}, {5, 6, 6}}, {{5, 5, 6}, {6, 7, 8}, {5, 5, 6}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for blur with double.
   */
  @Test
  public void checkBlurSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 45.8);
    this.operations.blur(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {1, 1, 2}}, {{4, 5, 6}, {4, 5, 6}, {3, 3, 4}},
            {{7, 8, 9}, {7, 8, 9}, {5, 6, 6}}, {{10, 11, 12}, {6, 7, 8}, {5, 5, 6}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for sepia.
   */
  @Test
  public void checkSepiaSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 50);
    this.operations.sepia(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {2, 2, 1}, {2, 2, 1}}, {{4, 5, 6}, {6, 5, 4}, {6, 5, 4}},
            {{7, 8, 9}, {10, 9, 7}, {10, 9, 7}}, {{10, 11, 12}, {14, 13, 10}, {14, 13, 10}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for sepia.
   */
  @Test
  public void checkSepiaSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 50.56);
    this.operations.sepia(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {2, 2, 1}, {2, 2, 1}}, {{4, 5, 6}, {6, 5, 4}, {6, 5, 4}},
            {{7, 8, 9}, {10, 9, 7}, {10, 9, 7}}, {{10, 11, 12}, {14, 13, 10}, {14, 13, 10}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for sharp.
   */
  @Test
  public void checkSharpSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 70);
    this.operations.sharpen(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {0, 1, 2}}, {{4, 5, 6}, {4, 5, 6}, {3, 5, 6}},
            {{7, 8, 9}, {7, 8, 9}, {12, 14, 15}}, {{10, 11, 12}, {10, 11, 12}, {12, 13, 14}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with zero
   * for sharp.
   */
  @Test
  public void checkSharpSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 23.89);
    this.operations.sharpen(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{0, 1, 2}, {1, 3, 5}, {0, 1, 2}}, {{3, 5, 6}, {8, 10, 13}, {3, 5, 6}},
            {{12, 14, 15}, {20, 23, 25}, {12, 14, 15}}, {{12, 13, 14}, {18, 20, 22}, {12, 13, 14}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for red-component.
   */
  @Test
  public void checkRedComponentSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 59);
    this.operations.getColorComponent(temp, temp, 0);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 1, 1}, {1, 1, 1}}, {{4, 5, 6}, {4, 4, 4}, {4, 4, 4}},
            {{7, 8, 9}, {7, 7, 7}, {7, 7, 7}}, {{10, 11, 12}, {10, 10, 10}, {10, 10, 10}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for red-component.
   */
  @Test
  public void checkRedComponentSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 59.7);
    this.operations.getColorComponent(temp, temp, 0);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 1, 1}, {1, 1, 1}}, {{4, 5, 6}, {4, 4, 4}, {4, 4, 4}},
            {{7, 8, 9}, {7, 7, 7}, {7, 7, 7}}, {{10, 11, 12}, {10, 10, 10}, {10, 10, 10}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for green-component.
   */
  @Test
  public void checkGreenComponentSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 90);
    this.operations.getColorComponent(temp, temp, 1);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {2, 2, 2}}, {{4, 5, 6}, {4, 5, 6}, {5, 5, 5}},
            {{7, 8, 9}, {7, 8, 9}, {8, 8, 8}}, {{10, 11, 12}, {10, 11, 12}, {11, 11, 11}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for green-component.
   */
  @Test
  public void checkGreenComponentSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 90.2);
    this.operations.getColorComponent(temp, temp, 1);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {2, 2, 2}}, {{4, 5, 6}, {4, 5, 6}, {5, 5, 5}},
            {{7, 8, 9}, {7, 8, 9}, {8, 8, 8}}, {{10, 11, 12}, {10, 11, 12}, {11, 11, 11}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for blue-component.
   */
  @Test
  public void checkBlueComponentSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 10);
    this.operations.getColorComponent(temp, temp, 2);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{3, 3, 3}, {3, 3, 3}, {3, 3, 3}}, {{6, 6, 6}, {6, 6, 6}, {6, 6, 6}},
            {{9, 9, 9}, {9, 9, 9}, {9, 9, 9}}, {{12, 12, 12}, {12, 12, 12}, {12, 12, 12}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for blue-component with double.
   */
  @Test
  public void checkBlueComponentSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 67.98);
    this.operations.getColorComponent(temp, temp, 2);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {3, 3, 3}}, {{4, 5, 6}, {4, 5, 6}, {6, 6, 6}},
            {{7, 8, 9}, {7, 8, 9}, {9, 9, 9}}, {{10, 11, 12}, {10, 11, 12}, {12, 12, 12}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for value-component.
   */
  @Test
  public void checkValueComponentSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 90);
    this.operations.getBrightnessComponent(temp, temp, "value-component");
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {3, 3, 3}}, {{4, 5, 6}, {4, 5, 6}, {6, 6, 6}},
            {{7, 8, 9}, {7, 8, 9}, {9, 9, 9}}, {{10, 11, 12}, {10, 11, 12}, {12, 12, 12}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for value-component with double.
   */
  @Test
  public void checkValueComponentSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 45.78);
    this.operations.getBrightnessComponent(temp, temp, "value-component");
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {3, 3, 3}, {3, 3, 3}}, {{4, 5, 6}, {6, 6, 6}, {6, 6, 6}},
            {{7, 8, 9}, {9, 9, 9}, {9, 9, 9}}, {{10, 11, 12}, {12, 12, 12}, {12, 12, 12}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for luma-component.
   */
  @Test
  public void checkLumaComponentSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 96);
    this.operations.getBrightnessComponent(temp, temp, "luma-component");
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {1, 1, 1}}, {{4, 5, 6}, {4, 5, 6}, {4, 4, 4}},
            {{7, 8, 9}, {7, 8, 9}, {7, 7, 7}}, {{10, 11, 12}, {10, 11, 12}, {10, 10, 10}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for luma-component with double.
   */
  @Test
  public void checkLumaComponentSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 45.78);
    this.operations.getBrightnessComponent(temp, temp, "luma-component");
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 1, 1}, {1, 1, 1}}, {{4, 5, 6}, {4, 4, 4}, {4, 4, 4}},
            {{7, 8, 9}, {7, 7, 7}, {7, 7, 7}}, {{10, 11, 12}, {10, 10, 10}, {10, 10, 10}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with
   * for intensity-component.
   */
  @Test
  public void checkIntensityComponentSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 10);
    this.operations.getBrightnessComponent(temp, temp, "intensity-component");
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}, {{5, 5, 5}, {5, 5, 5}, {5, 5, 5}},
            {{8, 8, 8}, {8, 8, 8}, {8, 8, 8}}, {{11, 11, 11}, {11, 11, 11}, {11, 11, 11}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct with
   * for intensity-component double.
   */
  @Test
  public void checkIntensityComponentSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 50.8);
    this.operations.getBrightnessComponent(temp, temp, "intensity-component");
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {2, 2, 2}, {2, 2, 2}}, {{4, 5, 6}, {5, 5, 5}, {5, 5, 5}},
            {{7, 8, 9}, {8, 8, 8}, {8, 8, 8}}, {{10, 11, 12}, {11, 11, 11}, {11, 11, 11}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for levels-adjust.
   */
  @Test
  public void checkLevelsAdjustSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 90);
    this.operations.levelAdjustment(temp, temp, 20, 100, 200);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}, {0, 0, 0}}, {{4, 5, 6}, {4, 5, 6}, {0, 0, 0}},
            {{7, 8, 9}, {7, 8, 9}, {0, 0, 0}}, {{10, 11, 12}, {10, 11, 12}, {0, 0, 0}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for levels-adjust.
   */
  @Test
  public void checkLevelsAdjustSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 34.7);
    this.operations.levelAdjustment(temp, temp, 20, 100, 200);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {0, 0, 0}, {0, 0, 0}}, {{4, 5, 6}, {0, 0, 0}, {0, 0, 0}},
            {{7, 8, 9}, {0, 0, 0}, {0, 0, 0}}, {{10, 11, 12}, {0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for color-correct.
   */
  @Test
  public void checkColorCorrectSplit() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 23);
    this.operations.colorCorrection(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}}, {{5, 5, 5}, {5, 5, 5}, {5, 5, 5}},
            {{8, 8, 8}, {8, 8, 8}, {8, 8, 8}}, {{11, 11, 11}, {11, 11, 11}, {11, 11, 11}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the split works correct
   * for color-correct with double.
   */
  @Test
  public void checkColorCorrectSplitDouble() {
    String temp = "matrix";
    String name = temp + temp.hashCode();
    this.operations.splitPreview("matrix", name, 57.89);
    this.operations.colorCorrection(temp, temp);
    this.operations.regain("matrix", name, "matrix-new");
    int[][][] actual = this.operations.saveImage("matrix-new");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {2, 2, 2}, {2, 2, 2}}, {{4, 5, 6}, {5, 5, 5}, {5, 5, 5}},
            {{7, 8, 9}, {8, 8, 8}, {8, 8, 8}}, {{10, 11, 12}, {11, 11, 11}, {11, 11, 11}}
    };
    assertArrayEquals(actual, expected);
  }

  /**
   * Test Case to check if the histogram is generated correctly.
   * according to the property.
   */
  @Test
  public void checkHistogramProperty() {
    this.operations.histogram("man", "man-hist");
    int[][][] temp = this.operations.saveImage("man-hist");
    int height = temp.length;
    int width = temp[0].length;
    assertEquals(256, height);
    assertEquals(256, width);
  }

  /**
   * Test Case to check if the histogram of the image is generated
   * correctly.
   */
  @Test
  public void checkHistogramComponent() {
    int[][][] m = {
            {{150, 200, 170}, {150, 200, 170}, {150, 200, 170}},
            {{150, 200, 170}, {150, 200, 170}, {150, 200, 170}},
            {{150, 200, 170}, {150, 200, 170}, {150, 200, 170}}
    };
    this.operations.loadImage(m, "temp");
    this.operations.histogram("temp", "temp2");
    int[][][] output = this.operations.saveImage("temp2");

    for (int i = 1; i < 255; i++) {
      for (int j = 1; j < 255; j++) {
        if (j == 150 || j == 151) {
          assertEquals(output[i][j][0], 255);
          assertEquals(output[i][j][1], 0);
          assertEquals(output[i][j][2], 0);
        } else if (j == 200 || j == 201) {
          assertEquals(output[i][j][0], 0);
          assertEquals(output[i][j][1], 255);
          assertEquals(output[i][j][2], 0);
        } else if (j == 170 || j == 171) {
          assertEquals(output[i][j][0], 0);
          assertEquals(output[i][j][1], 0);
          assertEquals(output[i][j][2], 255);
        } else {
          if (i % 17 == 0 || j % 17 == 0) {
            assertEquals(output[i][j][0], 200);
            assertEquals(output[i][j][1], 200);
            assertEquals(output[i][j][2], 200);
          } else {
            assertEquals(output[i][j][0], 255);
            assertEquals(output[i][j][1], 255);
            assertEquals(output[i][j][2], 255);
          }
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for blur.
   */
  @Test
  public void checkMaskBlur() {
    this.operations.blur("man", "man-blur");
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-blur");
    this.operations.mask("man", "man-blur",
            "mask", "man-mask-blur");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-blur");
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for sharpen.
   */
  @Test
  public void checkMaskSharpen() {
    this.operations.sharpen("man", "man-sharp");
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-sharp");
    this.operations.mask("man", "man-sharp",
            "mask", "man-mask-sharp");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-sharp");
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for Sepia.
   */
  @Test
  public void checkMaskSepia() {
    this.operations.sepia("man", "man-sepia");
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-sepia");
    this.operations.mask("man", "man-sepia",
            "mask", "man-mask-sepia");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-sepia");
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for red-component.
   */
  @Test
  public void checkMaskRedComponent() {
    this.operations.getColorComponent("man", "man-red", 0);
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-red");
    this.operations.mask("man", "man-red",
            "mask", "man-mask-man-red");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-man-red");

    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for green-component.
   */
  @Test
  public void checkMaskGreenComponent() {
    this.operations.getColorComponent("man", "man-green", 1);
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-green");
    this.operations.mask("man", "man-green",
            "mask", "man-mask-man-green");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-man-green");

    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for blue-component.
   */
  @Test
  public void checkMaskBlueComponent() {
    this.operations.getColorComponent("man", "man-blue", 2);
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-blue");
    this.operations.mask("man", "man-blue",
            "mask", "man-mask-man-blue");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-man-blue");
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for value-component.
   */
  @Test
  public void checkMaskValueComponent() {
    this.operations.getBrightnessComponent("man", "man-value",
            "value-component");
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-value");
    this.operations.mask("man", "man-value",
            "mask", "man-mask-man-value");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-man-value");

    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for intensity-component.
   */
  @Test
  public void checkMaskIntensityComponent() {
    this.operations.getBrightnessComponent("man", "man-intensity",
            "intensity-component");
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-intensity");
    this.operations.mask("man", "man-intensity",
            "mask", "man-mask-man-intensity");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-man-intensity");

    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the mask works correct
   * for luma-component.
   */
  @Test
  public void checkMaskLumaComponent() {
    this.operations.getBrightnessComponent("man", "man-luma",
            "luma-component");
    int[][][] original = this.operations.saveImage("man");
    int[][][] temp = this.operations.saveImage("man-luma");
    this.operations.mask("man", "man-luma",
            "mask", "man-mask-man-luma");
    int[][][] mask = this.operations.saveImage("mask");
    int[][][] output = this.operations.saveImage("man-mask-man-luma");

    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[0].length; j++) {
        if (mask[i][j][0] == 0 && mask[i][j][1] == 0 && mask[i][j][2] == 0) {
          assertEquals(output[i][j][0], temp[i][j][0]);
          assertEquals(output[i][j][1], temp[i][j][1]);
          assertEquals(output[i][j][2], temp[i][j][2]);
        } else {
          assertEquals(output[i][j][0], original[i][j][0]);
          assertEquals(output[i][j][1], original[i][j][1]);
          assertEquals(output[i][j][2], original[i][j][2]);
        }
      }
    }
  }

  /**
   * Test Case to check if the Downscaling operation is
   * working correctly.
   */
  @Test
  public void downScaling() {
    this.operations.downScale("matrix", 2, 2,
            "down-scaled-matrix");
    int[][][] actual = this.operations.saveImage("down-scaled-matrix");
    int[][][] expected = new int[][][]{
            {{1, 2, 3}, {1, 2, 3}},
            {{7, 8, 9}, {7, 8, 9}}
    };
    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[0].length; j++) {
        for (int k = 0; k <= 2; k++) {
          assertEquals(expected[i][j][k], actual[i][j][k]);
        }
      }
    }
  }

}