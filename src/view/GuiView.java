package view;


import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;


/**
 * The GuiView class implements the IView interface and
 * serves as the main graphical user interface
 * for the image processing application.
 * It handles the layout and functionality of the GUI,
 * including buttons for various image processing
 * operations such as blur, sepia, sharpening,
 * brightness adjustment, and more.
 * This class contains the necessary logic for
 * handling user input, displaying results,
 * and interacting with the image processing
 * features of the application.
 */

public class GuiView extends JFrame implements IView {
  private JToggleButton loadButton;
  private JToggleButton saveButton;
  private JButton blurButton;
  private JButton sepiaButton;
  private JButton sharpenButton;
  private JButton colorCorrectionButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton lumaButton;
  private JButton hflipButton;
  private JButton vflipButton;
  private JButton brightButton;
  private JButton compressButton;
  private JButton previewButton;
  private JButton applyButton;
  private JButton cancelButton;
  private JButton adjButton;
  private JButton downScaleButton;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private JLabel messageLabel;
  private String imageName;
  private String commandName;
  private JPanel topPanel;
  private JPanel operationPanel;
  private JPanel imagePanel;
  private JPanel splitImagePanel;
  private JPanel histogramPanel;
  private JPanel inputPanel;
  private JPanel buttonPanel;
  private JPanel top;
  private JFrame splitFrame;
  private JTextField bField;
  private JTextField mField;
  private JTextField wField;
  private JTextField pField;
  private JTextField heightField;
  private JTextField widthField;
  private JTextField brightnessField;
  private Font font;
  private boolean isSplitFrameOpen;

  /**
   * Constructs the main GUI view for the
   * image processing application.
   * Initializes the components,
   * layout, panels, and sets up window behavior.
   *
   * @param caption The title to be displayed on the main window.
   */
  public GuiView(String caption) {
    this.isSplitFrameOpen = false;
    this.setTitle(caption);
    this.setMinimumSize(new Dimension(1100, 700));
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setResizable(true);
    this.initializeComponents();
    this.setLayout(new BorderLayout());

    this.addHeadingLabel();
    this.addSpacePanel();

    this.createTopPanel();
    this.createOperationalPanel();
    this.createImagePanel();
    this.createHistogramPanel();

    this.addLeftPanel();
    this.addSplitPane();
    this.addWindowListener();

    this.pack();
    this.setVisible(true);
  }

  /**
   * Creates and adds the heading label to the top of the window.
   * The label displays the text "Image Processing Application" in
   * a centered, bold SansSerif font.
   * The label's preferred size is set to 800x50 pixels and is
   * added to the NORTH region of the layout.
   */
  private void addHeadingLabel() {
    JLabel headingLabel = new JLabel("Image Processing Application", JLabel.CENTER);
    headingLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    headingLabel.setPreferredSize(new Dimension(800, 50));
    this.add(headingLabel, BorderLayout.NORTH);
  }

  /**
   * Creates and adds a space panel to the center of the window.
   * The space panel is a blank JPanel with a preferred size of 800x20 pixels.
   * It serves as a visual spacer between other components and is added
   * to the CENTER region of the layout.
   */
  private void addSpacePanel() {
    JPanel spacePanel = new JPanel();
    spacePanel.setPreferredSize(new Dimension(800, 20));
    this.add(spacePanel, BorderLayout.CENTER);
  }

  /**
   * Sets up and adds a vertical split pane to the main window.
   * This method creates a right panel that contains the image and
   * histogram panels stacked vertically.
   * A vertical split pane is then created, displaying the image
   * panel and histogram panel, allowing the user to resize the split area.
   * The split pane is added to the center of the main window.
   */
  private void addLeftPanel() {
    JScrollPane operationScrollPane = new JScrollPane(this.operationPanel);
    operationScrollPane.setPreferredSize(new Dimension(180, 500));
    operationScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout());
    leftPanel.add(this.topPanel, BorderLayout.NORTH);
    leftPanel.add(operationScrollPane, BorderLayout.CENTER);
    this.add(leftPanel, BorderLayout.WEST);
  }

  /**
   * Configures and adds a vertical split pane to the GUI, dividing the image panel
   * and histogram panel. The split pane supports adjustable resizing, with an initial
   * divider location at the midpoint and one-touch expandability.
   */
  private void addSplitPane() {
    JPanel rightPanel = new JPanel(new BorderLayout());
    JPanel imageAndHistogramPanel = new JPanel();
    imageAndHistogramPanel.setLayout(new GridLayout(2, 1, 0, 10));
    imageAndHistogramPanel.add(this.imagePanel);
    imageAndHistogramPanel.add(this.histogramPanel);
    imageAndHistogramPanel.setPreferredSize(new Dimension(400, 700));
    rightPanel.add(imageAndHistogramPanel, BorderLayout.CENTER);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, histogramPanel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerLocation(0.5);
    this.add(splitPane, BorderLayout.CENTER);
  }

  /**
   * Adds a window listener to handle the window closing event.
   * When the user attempts to close the application window, a confirmation dialog is shown
   * asking if they are sure they want to exit.
   * If the user confirms, the application window is disposed.
   */
  private void addWindowListener() {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        int choice = JOptionPane.showConfirmDialog(
                GuiView.this,
                "Are you sure you want to exit the application?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
          dispose(); // Close the main application window
        }
      }
    });
  }

  /**
   * Initializes all GUI components for the image processing application.
   * This includes buttons for image manipulation operations such as
   * loading, saving, applying color filters, adjusting brightness,
   * color correction, compression, and flipping the image.
   * Components like labels, Frames and toggle buttons are also initialized
   * to enable various functionality in the GUI.
   */
  private void initializeComponents() {
    this.font = new Font("SansSerif", Font.BOLD, 14);
    this.loadButton = new JToggleButton("Load Image");
    this.saveButton = new JToggleButton("Save Image");
    this.blurButton = new JButton("Blur");
    this.sepiaButton = new JButton("Sepia");
    this.sharpenButton = new JButton("Sharpen");
    this.redButton = new JButton("Red-component");
    this.greenButton = new JButton("Green-component");
    this.blueButton = new JButton("Blue-component");
    this.lumaButton = new JButton("Greyscale-Luma");
    this.hflipButton = new JButton("Horizontal-Flip");
    this.vflipButton = new JButton("Vertical-Flip");
    this.brightButton = new JButton("Brightness-Change");
    this.colorCorrectionButton = new JButton("Color Correction");
    this.downScaleButton = new JButton("Downscale");
    this.compressButton = new JButton("Compression");
    this.adjButton = new JButton("Levels-Adjust");
    this.previewButton = new JButton("Preview");
    this.applyButton = new JButton("Apply");
    this.cancelButton = new JButton("Cancel");
    this.imageLabel = new JLabel();
    this.histogramLabel = new JLabel();
  }

  /**
   * Creates and configures the top panel of the GUI, which contains
   * buttons for loading and saving images.
   * This panel uses a flow layout aligned to the left with specified padding.
   * A titled border with a customized font is added to distinguish the panel's purpose.
   */
  private void createTopPanel() {
    this.topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    this.topPanel.add(loadButton);
    this.topPanel.add(saveButton);
    TitledBorder border = BorderFactory.createTitledBorder("Load and Save");
    border.setTitleFont(this.font);
    border.setTitleJustification(TitledBorder.LEFT);
    this.topPanel.setBorder(border);
  }

  /**
   * Creates and configures the operational panel,
   * which contains buttons for
   * various image processing operations
   * (e.g., blur, sepia, sharpen, color adjustments).
   * The panel uses a GridBagLayout to arrange the buttons
   * in a grid structure with consistent padding between them.
   * Each button is given a standard size, and the
   * panel has a titled border to label it as the "Operations" section.
   */
  private void createOperationalPanel() {
    this.operationPanel = new JPanel();
    this.operationPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    Dimension buttonSize = new Dimension(150, 40);
    JButton[] buttons = new JButton[]{
      this.blurButton, this.sepiaButton, this.sharpenButton, this.colorCorrectionButton,
      this.redButton, this.greenButton, this.blueButton, this.lumaButton,
      this.hflipButton, this.vflipButton, this.brightButton,
      this.compressButton, this.adjButton, this.downScaleButton
    };

    for (JButton button : buttons) {
      setButtonProperties(button, buttonSize);
      addButton(button, gbc);
    }

    TitledBorder b = BorderFactory.createTitledBorder("Operations");
    b.setTitleFont(this.font);
    b.setTitleJustification(TitledBorder.LEFT);
    this.operationPanel.setBorder(b);
  }

  /**
   * Adds a button to the operational panel with
   * specified layout constraints.
   * Each button is added in a new row by
   * incrementing the grid's y-coordinate.
   *
   * @param button the JButton to add to the panel
   * @param gbc    the GridBagConstraints to specify the button's layout
   */
  private void addButton(JButton button, GridBagConstraints gbc) {
    this.operationPanel.add(button, gbc);
    gbc.gridy++;
  }

  /**
   * Creates and configures the image panel,
   * which displays the image in the center.
   * The panel uses a BorderLayout to center the image label.
   * A titled border is added to label the section as "Image".
   * After configuring the panel, the initial image setup
   * method is called to initialize the image display.
   */
  private void createImagePanel() {
    this.imagePanel = new JPanel(new BorderLayout());
    this.imagePanel.add(this.imageLabel, BorderLayout.CENTER);
    TitledBorder b = BorderFactory.createTitledBorder("Image");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    this.imagePanel.setBorder(b);
    this.initialImagePanel();
  }

  /**
   * Creates and configures the histogram panel,
   * which displays the histogram of the image.
   * The panel uses a BorderLayout to center
   * the histogram label.
   * A titled border is added to label the
   * section as "Histogram of Image".
   */
  private void createHistogramPanel() {
    this.histogramPanel = new JPanel(new BorderLayout());
    this.histogramPanel.add(this.histogramLabel, BorderLayout.CENTER);
    TitledBorder b = BorderFactory.createTitledBorder("Histogram of Image");
    b.setTitleFont(font);
    b.setTitleJustification(TitledBorder.LEFT);
    this.histogramPanel.setBorder(b);
  }

  /**
   * Sets uniform properties for a given button,
   * including preferred size and text alignment.
   * This method ensures that the button has a
   * consistent size and that its text is centered
   * both horizontally and vertically.
   *
   * @param button the JButton whose properties are to be set
   * @param size   the preferred size of the button
   */
  private void setButtonProperties(JButton button, Dimension size) {
    button.setPreferredSize(size);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setVerticalAlignment(SwingConstants.CENTER);
  }

  /**
   * Initializes the image panel with a placeholder
   * label and adds a scroll pane to allow scrolling
   * if the image exceeds the panel's dimensions.
   * The label prompts the user to load an image
   * for using the application.
   */
  private void initialImagePanel() {
    JLabel label = new JLabel("Load an Image for using the application.",
            SwingConstants.CENTER);
    JScrollPane scroll = new JScrollPane(label);
    scroll.setPreferredSize(new Dimension(200, 200));
    this.imagePanel.add(scroll);
  }

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
  @Override
  public void addFeature(Features features) {
    this.loadButton.addActionListener(e -> features.loadImage());
    this.saveButton.addActionListener(e -> features.saveImage(this.imageName));
    this.blurButton.addActionListener(e -> features.applyBlur(this.imageName));
    this.sepiaButton.addActionListener(e -> features.applySepia(this.imageName));
    this.sharpenButton.addActionListener(e -> features.applySharp(this.imageName));
    this.redButton.addActionListener(e -> features.applyRed(this.imageName));
    this.greenButton.addActionListener(e -> features.applyGreen(this.imageName));
    this.blueButton.addActionListener(e -> features.applyBlue(this.imageName));
    this.lumaButton.addActionListener(e -> features.applyLuma(this.imageName));
    this.compressButton.addActionListener(e -> features.applyCompress(this.imageName));
    this.hflipButton.addActionListener(e -> features.applyHorizontalFlip(this.imageName));
    this.vflipButton.addActionListener(e -> features.applyVerticalFlip(this.imageName));
    this.downScaleButton.addActionListener(e -> features.applyDownScale(this.imageName));
    this.colorCorrectionButton.addActionListener(e -> features.applyColorCorrect(this.imageName));
    this.adjButton.addActionListener(e -> features.applyLevelAdjust(this.imageName));
    this.brightButton.addActionListener(e -> features.applyBrightness(this.imageName));
    this.cancelButton.addActionListener(e -> {
      this.splitFrame.setVisible(false);
      this.splitFrame.dispose();
      this.isSplitFrameOpen = false;
    });
  }

  /**
   * Adds action listeners to the buttons related to the split operation,
   * which allow the user to apply a split operation to the image.
   * This method links the buttons to the corresponding methods in the `Features` controller
   * and handles showing success messages upon completion of the operation.
   *
   * @param features the controller interface responsible for applying
   *                 the split operation to the image.
   */
  private void addSplitOperationButton(Features features) {
    this.previewButton.addActionListener(e -> features.applySplit(this.pField.getText(),
            this.commandName, this.imageName));
    this.applyButton.addActionListener(e -> features.commandGenerator(this.commandName,
            this.imageName));
    this.applyButton.addActionListener(e ->
            this.previewDisposalForAcceptButton("Filter applied successfully"));
  }

  /**
   * Adds action listeners to the buttons related to image compression operations,
   * enabling the user to apply compression to the image.
   * The method links the buttons to the appropriate methods in the `Features` controller
   * and handles displaying success messages after the compression is applied.
   *
   * @param features the controller interface responsible
   *                 for applying compression to the image.
   */
  private void addCompressionButton(Features features) {
    this.previewButton.addActionListener(e -> features.generateCompress(this.pField.getText(),
            this.imageName, true));
    this.applyButton.addActionListener(e -> {
      boolean t = features.generateCompress(this.pField.getText(), this.imageName, false);
      if (!t) {
        return;
      }
      this.previewDisposalForAcceptButton("Compress applied successfully");
    });
  }

  /**
   * Adds action listeners to the buttons related to level adjustment operations,
   * which allow the user to adjust the levels of the image.
   * The method links the buttons to the corresponding methods in the `Features` controller
   * and ensures that success messages are shown when the operation is applied.
   *
   * @param features the controller interface that handles level
   *                 adjustment operations for the image.
   */
  private void addLevelAdjustButton(Features features) {
    this.previewButton.addActionListener(e -> features.getLevelAdjust(this.imageName,
            this.bField.getText(), this.mField.getText(), this.wField.getText(),
            this.pField.getText()));

    this.applyButton.addActionListener(e -> {
      boolean t = features.generateLevelAdjust(this.bField.getText(), this.mField.getText(),
              this.wField.getText(), this.imageName);
      if (!t) {
        return;
      }
      this.previewDisposalForAcceptButton("Levels-adjust applied successfully");
    });
  }

  /**
   * Adds action listeners to the brightness-related buttons,
   * enabling the user to preview and apply brightness adjustments to the image.
   * The method links the buttons to the appropriate methods in the `Features` controller
   * and displays success messages when the brightness operation is completed.
   *
   * @param features the controller interface
   *                 that handles brightness adjustments for the image.
   */
  private void addBrightButton(Features features) {
    this.previewButton.addActionListener(e -> features.generateBright(this.imageName,
            this.brightnessField.getText(), true));
    this.applyButton.addActionListener(e -> {
      boolean t = features.generateBright(this.imageName, this.brightnessField.getText(),
              false);
      if (!t) {
        return;
      }
      this.previewDisposalForAcceptButton("Brightness-Change applied successfully");
    });

  }

  /**
   * Adds action listeners to the downscale-related buttons,
   * allowing the user to preview and apply downscaling operations to the image.
   * The method links the buttons to the corresponding methods in the Features controller
   * and handles showing success messages once the downscaling operation is complete.
   *
   * @param features the controller interface that
   *                 manages downscaling operations for the image.
   */
  private void addDownScalingButton(Features features) {
    this.previewButton.addActionListener(e -> features.generateDown(this.imageName,
            this.widthField.getText(), this.heightField.getText(), true));
    this.applyButton.addActionListener(e -> {
      boolean t = features.generateDown(this.imageName, this.widthField.getText(),
              this.heightField.getText(), false);
      if (!t) {
        return;
      }
      this.previewDisposalForAcceptButton("Down-Scaling applied successfully");
    });
  }

  /**
   * Private helper method which is used to dispose the preview frame
   * when apply button is clicked.
   *
   * @param message the success message to get displayed when apply is
   *                clicked.
   */
  private void previewDisposalForAcceptButton(String message) {
    this.splitFrame.setVisible(false);
    this.successMessage(message);
    this.splitFrame.dispose();
    this.isSplitFrameOpen = false;
  }

  /**
   * Initializes and configures the split frame for
   * displaying operations such as previewing
   * and applying image transformations.
   * The frame is set up with specific dimensions, and
   * contains panels for user input , buttons, and a placeholder for
   * the image preview.
   * The method ensures the frame is resizable,
   * positioned relative to the main
   * window, and handles the closing event to
   * properly dispose of the frame and update the state.
   *
   * @param frameName the name to be given to the split frame window
   */
  private void initializeFrame(String frameName) {
    this.splitFrame = new JFrame(frameName);
    this.splitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.splitFrame.setResizable(true);
    int height = (int) (this.getHeight() * 0.8);
    int width = (int) (this.getWidth() * 0.9);
    this.splitFrame.setSize(width, height);
    this.splitFrame.setMinimumSize(new Dimension(900, 650));
    this.splitFrame.setLayout(new BorderLayout());
    this.splitFrame.setLocationRelativeTo(this);
    this.top = new JPanel();
    this.top.setLayout(new BoxLayout(this.top, BoxLayout.Y_AXIS));
    this.top.setPreferredSize(new Dimension(width, 120));
    this.inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    JLabel label = new JLabel("Enter a Percentage: ");
    this.pField = new JTextField("50", 5);
    this.messageLabel = new JLabel("");
    messageLabel.setForeground(Color.RED);
    messageLabel.setVisible(false);

    this.pField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("Percentage must be between 0 to 100");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });

    this.inputPanel.add(this.pField, 0);
    this.inputPanel.add(label, 0);

    this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    this.buttonPanel.add(this.cancelButton);

    JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    messagePanel.add(messageLabel);
    this.top.add(inputPanel);
    this.top.add(messagePanel);
    this.top.add(buttonPanel);

    this.splitImagePanel = new JPanel();
    this.splitImagePanel.setLayout(new BorderLayout());
    JLabel placeholderLabel = new JLabel("Enter value and then press preview to " +
            "get the preview of the operation.", JLabel.CENTER);
    this.splitImagePanel.add(placeholderLabel, BorderLayout.CENTER);
    JPanel bottom = new JPanel(new BorderLayout());
    bottom.add(this.splitImagePanel, BorderLayout.CENTER);
    this.splitFrame.add(top, BorderLayout.NORTH);
    this.splitFrame.add(bottom, BorderLayout.CENTER);
    this.isSplitFrameOpen = true;
    this.splitFrame.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        isSplitFrameOpen = false;
        if (splitFrame != null) {
          splitFrame.dispose();
        }
      }
    });
    this.splitFrame.pack();
    this.splitFrame.setVisible(true);
  }

  /**
   * Updates the text displayed on the message label with the provided value.
   * This method allows dynamic updates to the message displayed on the label,
   * enabling the application to provide feedback or guidance to the user
   * based on specific actions or input.
   *
   * @param p the message to be displayed on the label.
   */
  private void setMessageValue(String p) {
    this.messageLabel.setText(p);
  }

  /**
   * Checks if a split frame is already open.
   * If an operation pop-up is currently open,
   * it displays an error message and brings
   * the existing frame to the front.
   * If no frame is open, it returns false,
   * allowing the new operation to proceed.
   *
   * @return true if a split frame is already open, false otherwise
   */
  private boolean checkingFrame() {
    if (isSplitFrameOpen) {
      JOptionPane.showMessageDialog(this.splitFrame,
              "Already a operation pop-up opened.",
              "Error",
              JOptionPane.ERROR_MESSAGE);
      this.splitFrame.toFront();
      return true;
    }
    return false;
  }

  /**
   * Initializes and configures the preview frame to allow
   * the user to adjust the brightness or darkness of an image.
   * The method sets up the input panel with a text field for entering
   * the brightness value, and adds buttons for
   * previewing and applying the adjustment.
   * It also handles the display of the image preview
   * and ensures that the frame is properly
   * set up for the brightness operation.
   *
   * @param features the controller interface responsible for applying
   *                 the split operation to the image.
   */
  @Override
  public void brightness(Features features) {
    if (this.checkingFrame()) {
      return;
    }
    this.initializeFrame("Brightness or Darkness Preview");
    this.inputPanel.removeAll();
    JLabel label = new JLabel("Enter a Value: ");
    this.inputPanel.add(label);
    this.brightnessField = new JTextField(5);
    this.brightnessField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("The value must be an Integer Value.");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });
    this.inputPanel.add(this.brightnessField);
    this.addingButtonToPanel();
    this.addBrightButton(features);
  }

  /**
   * Initializes and configures the preview split frame for
   * previewing a specific image operation like blur, sharpen,
   * sepia, red-component, green-component, blue-component
   * and luma-component.
   * It checks if another frame is open, and
   * if not, sets up the input panel, buttons
   * for previewing, applying, and canceling
   * the operation.
   * The frame is then displayed with the necessary
   * components for the user to interact with.
   *
   * @param commandName the name of the operation
   *                    that is being previewed
   * @param features    the controller interface responsible for applying
   *                    the split operation to the image.
   */
  @Override
  public boolean splitPreview(String commandName, Features features) {
    if (this.checkingFrame()) {
      return false;
    }
    this.commandName = commandName;

    this.initializeFrame("Preview " + commandName);
    this.addingButtonToPanel();
    this.addSplitOperationButton(features);
    return true;
  }

  /**
   * Initializes and configures the preview frame for
   * performing level adjustment on the image.
   * The method sets up input fields for the
   * user to enter the black, mid, and white values
   * and adds buttons for previewing, applying,
   * and canceling the adjustment.
   * The frame is then displayed, allowing the user to preview
   * the effect of the adjustment and apply it if desired.
   *
   * @param features the controller interface responsible for applying
   *                 the preview operation to the image.
   */
  @Override
  public void levelAdjust(Features features) {
    if (this.checkingFrame()) {
      return;
    }
    this.initializeFrame("Level Adjustment Preview");
    this.pField.setText("");
    this.bField = new JTextField(5);
    this.bField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("The value of b must be between 0 and 255, " +
                "and it should be smaller than both m and w.");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });
    this.mField = new JTextField(5);
    this.mField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("The value of m must be between 0 and 255, and it should be" +
                " smaller than w but larger than b.");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });
    this.wField = new JTextField(5);
    this.wField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("The value of w must be between 0 and 255, " +
                "and it should be greater than both b and m.");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });
    this.inputPanel.add(this.wField, 0);
    this.inputPanel.add(new JLabel("Enter White Value : "), 0);
    this.inputPanel.add(this.mField, 0);
    this.inputPanel.add(new JLabel("Enter Mid Value : "), 0);
    this.inputPanel.add(this.bField, 0);
    this.inputPanel.add(new JLabel("Enter Black Value : "), 0);
    this.addingButtonToPanel();
    this.addLevelAdjustButton(features);
  }

  /**
   * Initializes and configures the preview frame
   * for previewing image compression.
   * The method checks if another frame is
   * open, and if not, sets up the necessary components
   * such as buttons for previewing, applying,
   * and canceling the compression operation.
   * The frame is then displayed, allowing the
   * user to interact with it and preview the
   * compression effect before applying it.
   *
   * @param features the controller interface responsible for applying
   *                 the preview operation to the image.
   */
  @Override
  public void compressImage(Features features) {
    if (this.checkingFrame()) {
      return;
    }
    this.initializeFrame("Compression Preview");
    this.pField.setText("");
    this.addingButtonToPanel();
    this.addCompressionButton(features);
  }

  /**
   * Initializes and configures the preview frame
   * for previewing down scaling of image.
   * The method checks if another frame is
   * open, and if not, sets up the necessary components
   * such as buttons for previewing, applying,
   * and canceling the compression operation.
   * The frame is then displayed, allowing the
   * user to interact with it and preview the
   * downscale effect before applying it.
   *
   * @param height   the height of current image.
   * @param width    the width of current image.
   * @param features the controller interface responsible for applying
   *                 the preview operation to the image.
   */
  @Override
  public void downScale(int height, int width, Features features) {
    if (this.checkingFrame()) {
      return;
    }
    this.initializeFrame("Down Scale Preview");
    this.inputPanel.removeAll();
    JPanel information = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel heightLabel = new JLabel("Current Image Height : " + height + " ");
    JLabel widthLabel = new JLabel("Current Image Width : " + width);
    information.add(heightLabel);
    information.add(widthLabel);
    this.top.add(information, 2);
    this.widthField = new JTextField(5);
    this.widthField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("The value of width must be less than current " +
                "Image width and non-negative");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });
    this.heightField = new JTextField(5);
    this.heightField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        setMessageValue("The value of height must be less than current Image height" +
                " and non-negative.");
        messageLabel.setVisible(true);
      }

      @Override
      public void focusLost(FocusEvent e) {
        messageLabel.setVisible(false);
      }
    });
    this.inputPanel.add(this.widthField, 0);
    this.inputPanel.add(new JLabel("Enter Width Value : "), 0);
    this.inputPanel.add(this.heightField, 0);
    this.inputPanel.add(new JLabel("Enter Height Value : "), 0);
    this.addingButtonToPanel();
    this.addDownScalingButton(features);
  }

  /**
   * Helper method to add the buttons to the button panel
   * when any of the preview panels gets opened.
   */
  private void addingButtonToPanel() {
    this.buttonPanel.add(this.applyButton, 0);
    this.buttonPanel.add(this.previewButton, 0);
    this.removeAllListners(this.applyButton);
    this.removeAllListners(this.previewButton);
  }

  /**
   * Helper method to remove the current action listners associated with
   * all button common to all preview panel.
   *
   * @param button the button whose action listners are to be removed.
   */
  private void removeAllListners(JButton button) {
    for (ActionListener listners : button.getActionListeners()) {
      button.removeActionListener(listners);
    }
  }

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
  @Override
  public File getFilePath() {
    File f = null;
    JFileChooser select = new JFileChooser(".");
    select.setAcceptAllFileFilterUsed(false);
    select.setDialogTitle("Select an Image");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "File Extensions", "jpg", "jpeg", "png", "ppm"
    );
    select.setFileFilter(filter);
    int result = select.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File temp = select.getSelectedFile();
      String path = temp.getAbsolutePath();
      f = new File(path);
    }
    return f;
  }

  /**
   * Opens a file chooser dialog to allow the user
   * to select a location and format for saving an image.
   * The user can choose from formats such as
   * PNG, JPEG, JPG, or PPM.
   * If the selected file does not
   * have the correct file extension based on the
   * selected format, the extension is automatically added.
   *
   * @return a `File` object representing the selected location, or null.
   */
  @Override
  public File getSaveFilePath() {
    String[] formats = {"PNG", "JPEG", "JPG", "PPM"};
    JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setDialogTitle("Save Image As");
    fileChooser.setSelectedFile(new File(this.imageName));
    for (String format : formats) {
      fileChooser.addChoosableFileFilter(new FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.isDirectory() || f.getName().toLowerCase().endsWith("." +
                  format.toLowerCase());
        }
        @Override
        public String getDescription() {
          return format + " (*." + format.toLowerCase() + ")";
        }
      });
      if (format.equals("JPEG")) {
        fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[
                fileChooser.getChoosableFileFilters().length - 1]);
      }
    }
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      String selectedFormat = fileChooser.getFileFilter().getDescription().split(" ")[0]
              .toLowerCase();
      if (!file.getName().toLowerCase().endsWith("." + selectedFormat)) {
        file = new File(file.getAbsolutePath() + "." + selectedFormat);
      }
      return file;
    } else {
      return null;
    }
  }

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
  @Override
  public void showImage(String name, BufferedImage image) {
    this.imageName = name;
    JLabel img = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(img);
    scrollPane.setPreferredSize(new Dimension(300, 300));
    this.imagePanel.removeAll();
    this.imagePanel.add(scrollPane);
    this.imagePanel.revalidate();
    this.imagePanel.repaint();
  }

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
  @Override
  public void showHistogram(BufferedImage image) {
    JLabel histogram = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(histogram);
    scrollPane.setPreferredSize(new Dimension(270, 270));
    this.histogramPanel.removeAll();
    this.histogramPanel.add(scrollPane);
    this.histogramPanel.revalidate();
    this.histogramPanel.repaint();
  }

  /**
   * Checks if the user wants to overwrite the current image.
   * If an image is loaded, this method shows a
   * confirmation dialog asking whether
   * the user is sure they want to overwrite the current image.
   *
   * @return true if the user chooses "Yes" to overwrite the image, false otherwise
   */
  @Override
  public boolean checkImage() {
    if (this.imageName == null) {
      return true;
    }
    int value = JOptionPane.showConfirmDialog(
            this, "Are Sure you want to " +
                    "overwrite the current image",
            "Overwriting the loaded Image ",
            JOptionPane.YES_NO_OPTION
    );
    return value == JOptionPane.YES_OPTION;

  }

  /**
   * Displays a split image in the split image panel.
   * This method updates the `splitImagePanel`
   * with a new `JLabel` containing the image.
   * The image is displayed inside a scroll pane
   * with a preferred size of 500x500 pixels.
   *
   * @param image the `BufferedImage` object representing the split image to be displayed
   */
  @Override
  public void showSplitImage(BufferedImage image) {
    JLabel imgLabel = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(imgLabel);
    scrollPane.setPreferredSize(new Dimension(500, 500));
    this.splitImagePanel.removeAll();
    this.splitImagePanel.add(scrollPane, BorderLayout.CENTER);
    this.splitImagePanel.revalidate();
    this.splitImagePanel.repaint();
  }

  /**
   * Displays a success message after an image is
   * successfully saved.
   * This method shows a dialog box informing the
   * user that the image has been saved successfully.
   */
  @Override
  public void showSaveSuccess() {
    JOptionPane.showMessageDialog(
            this,
            "Image saved successful!",
            "Save Success",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  /**
   * Displays a success message after an operations on
   * image is applied correctly.
   * This method shows a dialog box informing the
   * user that the operations has performed successfully.
   */
  private void successMessage(String msg) {
    JOptionPane.showMessageDialog(
            this,
            msg,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

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
  @Override
  public void showError(String error) {
    JOptionPane.showMessageDialog(this.splitFrame,
            error,
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays an error message when no image is
   * loaded in the application.
   * This method shows a dialog box informing
   * the user that they need to load an image
   * before performing any operations.
   */
  @Override
  public void showImageNotPresent() {
    JOptionPane.showMessageDialog(this,
            "No image loaded! Please load an image before applying Operations.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

}

