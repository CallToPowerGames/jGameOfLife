/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.ui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.calltopower.jgol.utils.Constants;
import de.calltopower.jgol.utils.FileData;

/**
 * The controls UI
 */
public class ControlsUI extends JFrame {

	private static final Logger LOGGER = LogManager.getLogger(ControlsUI.class);

	private static final long serialVersionUID = 6506041141548166025L;

	private JPanel panelMain;
	private JButton startPauseButton;
	private JButton resetButton;
	private JButton nextGenerationButton;
	private JButton quitButton;
	private JButton aboutButton;
	private JButton clearButton;
	private JButton importButton;
	private JButton exportButton;
	private JSlider generationLengthSlider;
	private JLabel labelGenerationTimeout;
	private JLabel labelGenerationTimeoutVal;
	private JSlider nrOfFieldsSlider;
	private JLabel labelNrOfFields;
	private JLabel labelNrOfFieldsVal;
	private JSlider fieldSizeSlider;
	private JLabel labelFieldSize;
	private JLabel labelFieldSizeVal;

	private FieldUI fieldUI;

	private GridBagLayout layout;

	private boolean updateGUIOnFieldSizeSliderChange;
	private boolean updateGUIOnFieldDimensionSliderChange;

	/**
	 * Constructor
	 */
	public ControlsUI() {
		super(Constants.APP_NAME);

		LOGGER.info("Initializing Controls UI");

		updateGUIOnFieldSizeSliderChange = true;
		updateGUIOnFieldDimensionSliderChange = true;

		setTitle(Constants.APP_NAME);

		initUI();

		setContentPane(panelMain);

		registerButtons();

		Dimension minimumDimension = new Dimension(300, 330);
		setMinimumSize(minimumDimension);
		setPreferredSize(minimumDimension);
		pack();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = 150;
		int y = (((int) rect.getMaxY() / 2)) - (getHeight() / 2);
		setLocation(x, y);

		fieldUI = new FieldUI();
		fieldUI.setVisible(true);

		fieldUI.updateGenerationTimeout(generationLengthSlider.getValue());
	}

	private void showAboutDialog(String title, String msg) {
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private void initUI() {
		panelMain = new JPanel();
		panelMain.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		startPauseButton = new JButton("Start");
		resetButton = new JButton("New random field");
		nextGenerationButton = new JButton("Next generation");
		quitButton = new JButton("Quit");
		aboutButton = new JButton("About");
		clearButton = new JButton("Clear");
		importButton = new JButton("Import pattern");
		exportButton = new JButton("Export pattern");
		generationLengthSlider = new JSlider(1, Constants.GAME_MAX_GENERATION_LENGTH);
		generationLengthSlider.setValue((int) Constants.DEFAULT_GENERATION_TIMEOUT);
		labelGenerationTimeout = new JLabel("Generation Length (ms)	:");
		labelGenerationTimeoutVal = new JLabel(String.valueOf(generationLengthSlider.getValue()));
		nrOfFieldsSlider = new JSlider(Constants.GAME_MIN_NR_OF_FIELDS, Constants.GAME_MAX_NR_OF_FIELDS);
		nrOfFieldsSlider.setValue(Constants.DEFAULT_NR_OF_FIELDS);
		labelNrOfFields = new JLabel("Nr Of Fields:");
		labelNrOfFieldsVal = new JLabel(String.valueOf(nrOfFieldsSlider.getValue()));
		fieldSizeSlider = new JSlider(Constants.GAME_MIN_FIELD_SIZE, Constants.GAME_MAX_FIELD_SIZE);
		fieldSizeSlider.setValue(Constants.DEFAULT_FIELD_SIZE);
		labelFieldSize = new JLabel("Field Size (px):");
		labelFieldSizeVal = new JLabel(String.valueOf(fieldSizeSlider.getValue()));

		layout = new GridBagLayout();
		panelMain.setLayout(layout);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2, 2, 2, 2);
		int currY = 0;

		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(startPauseButton, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(nextGenerationButton, c);

		c.weightx = 0.1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = currY;
		panelMain.add(labelGenerationTimeout, c);

		c.weightx = 0.7;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = currY;
		panelMain.add(generationLengthSlider, c);

		c.weightx = 0.2;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = currY++;
		panelMain.add(labelGenerationTimeoutVal, c);

		c.weightx = 0.1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = currY;
		panelMain.add(labelNrOfFields, c);

		c.weightx = 0.7;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = currY;
		panelMain.add(nrOfFieldsSlider, c);

		c.weightx = 0.2;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = currY++;
		panelMain.add(labelNrOfFieldsVal, c);

		c.weightx = 0.1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = currY;
		panelMain.add(labelFieldSize, c);

		c.weightx = 0.7;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = currY;
		panelMain.add(fieldSizeSlider, c);

		c.weightx = 0.2;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = currY++;
		panelMain.add(labelFieldSizeVal, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(clearButton, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(resetButton, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(importButton, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(exportButton, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(aboutButton, c);

		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = currY++;
		panelMain.add(quitButton, c);
	}

	private void updateStartPauseButtonText(boolean running) {
		if (running) {
			startPauseButton.setText("Pause");
		} else {
			startPauseButton.setText("Start");
		}
	}

	private void registerButtons() {
		startPauseButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Start/Pause");
			boolean running = fieldUI.toggleGameLoop();
			updateStartPauseButtonText(running);
			startPauseButton.updateUI();
		}));

		nextGenerationButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Next Generation");
			updateStartPauseButtonText(false);
			fieldUI.generateNextGeneration();
		}));

		resetButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Reset");
			updateStartPauseButtonText(false);
			fieldUI.resetGame();
		}));

		clearButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Clear");
			updateStartPauseButtonText(false);
			fieldUI.clearGame();
		}));

		generationLengthSlider.addChangeListener(e -> SwingUtilities.invokeLater(() -> {
			int val = generationLengthSlider.getValue();
			LOGGER.info("Generation slider value changed to " + val);
			updateStartPauseButtonText(false);
			fieldUI.updateGenerationTimeout(val);
			labelGenerationTimeoutVal.setText(String.valueOf(val));
		}));

		fieldSizeSlider.addChangeListener(e -> SwingUtilities.invokeLater(() -> {
			int val = fieldSizeSlider.getValue();
			LOGGER.info("Field size slider value changed to " + val);
			updateStartPauseButtonText(false);
			if (updateGUIOnFieldSizeSliderChange) {
				fieldUI.setVisible(false);
				fieldUI.updateFieldSize(val);
				labelFieldSizeVal.setText(String.valueOf(val));
				fieldUI.setVisible(true);
			} else {
				updateGUIOnFieldSizeSliderChange = true;
			}
		}));

		nrOfFieldsSlider.addChangeListener(e -> SwingUtilities.invokeLater(() -> {
			int val = nrOfFieldsSlider.getValue();
			LOGGER.info("Nr Of Fields size slider value changed to " + val);
			updateStartPauseButtonText(false);
			if (updateGUIOnFieldDimensionSliderChange) {
				fieldUI.setVisible(false);
				fieldUI.updateNrOfFields(val);
				labelNrOfFieldsVal.setText(String.valueOf(val));
				fieldUI.setVisible(true);
			} else {
				updateGUIOnFieldDimensionSliderChange = true;
			}
		}));

		importButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Import");
			updateStartPauseButtonText(false);
			FileData fileData = fieldUI.importFile();
			if (fileData != null) {
				fieldUI.setVisible(false);
				updateGUIOnFieldSizeSliderChange = false;
				updateGUIOnFieldDimensionSliderChange = false;
				fieldSizeSlider.setValue(fileData.getFieldSize());
				labelFieldSizeVal.setText(String.valueOf(fieldSizeSlider.getValue()));
				nrOfFieldsSlider.setValue(fileData.getFieldSize());
				labelNrOfFieldsVal.setText(String.valueOf(fieldSizeSlider.getValue()));
				fieldUI.setFile(fileData);
				fieldUI.setVisible(true);
			}
		}));

		exportButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Export");
			updateStartPauseButtonText(false);
			fieldUI.exportFile();
		}));

		aboutButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked About");
			StringBuilder sb = new StringBuilder();
			sb.append(Constants.APP_NAME).append("\n\n");
			sb.append(String.format("Version: %s", Constants.APP_VERSION)).append("\n");
			sb.append(Constants.APP_COPYRIGHT);
			showAboutDialog(String.format("About %s", Constants.APP_NAME), sb.toString());
		}));

		quitButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
			LOGGER.info("Clicked Quit");
			System.exit(0);
		}));
	}

}
