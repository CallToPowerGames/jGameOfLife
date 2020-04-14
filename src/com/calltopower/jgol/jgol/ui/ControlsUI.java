/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.ui;

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

import com.calltopower.jgol.jgol.utils.Constants;
import com.calltopower.jgol.jgol.utils.FileData;

/**
 * The controls UI
 */
public class ControlsUI extends JFrame {

    private static final long serialVersionUID = 6506041141548166025L;

    private JPanel panelMain;
    private JButton startButton;
    private JButton resetButton;
    private JButton pauseButton;
    private JButton nextGenerationButton;
    private JButton showRoundsButton;
    private JButton quitButton;
    private JButton aboutButton;
    private JButton clearButton;
    private JButton importButton;
    private JButton exportButton;
    private JSlider roundLengthSlider;
    private JLabel labelRoundTimeout;
    private JLabel labelRoundTimeoutVal;
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

        updateGUIOnFieldSizeSliderChange = true;
        updateGUIOnFieldDimensionSliderChange = true;

        setTitle(Constants.APP_NAME);

        initUI();

        setContentPane(panelMain);

        registerButtons();

        Dimension minimumDimension = new Dimension(350, 420);
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

        fieldUI.updateRoundTimeout(roundLengthSlider.getValue());
    }

    private void showAboutDialog(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void initUI() {
        panelMain = new JPanel();
        panelMain.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        startButton = new JButton("Start");
        resetButton = new JButton("Reset");
        pauseButton = new JButton("Pause");
        nextGenerationButton = new JButton("Next generation");
        showRoundsButton = new JButton("Show Rounds");
        quitButton = new JButton("Quit");
        aboutButton = new JButton("About");
        clearButton = new JButton("Clear");
        importButton = new JButton("Import");
        exportButton = new JButton("Export");
        roundLengthSlider = new JSlider(1, Constants.GAME_MAX_ROUND_LENGTH);
        roundLengthSlider.setValue((int) Constants.DEFAULT_ROUND_TIMEOUT);
        labelRoundTimeout = new JLabel("Round Length:");
        labelRoundTimeoutVal = new JLabel(String.valueOf(roundLengthSlider.getValue()));
        nrOfFieldsSlider = new JSlider(Constants.GAME_MIN_NR_OF_FIELDS, Constants.GAME_MAX_NR_OF_FIELDS);
        nrOfFieldsSlider.setValue(Constants.DEFAULT_NR_OF_FIELDS);
        labelNrOfFields = new JLabel("Nr Of Fields:");
        labelNrOfFieldsVal = new JLabel(String.valueOf(nrOfFieldsSlider.getValue()));
        fieldSizeSlider = new JSlider(Constants.GAME_MIN_FIELD_SIZE, Constants.GAME_MAX_FIELD_SIZE);
        fieldSizeSlider.setValue(Constants.DEFAULT_FIELD_SIZE);
        labelFieldSize = new JLabel("Field Size:");
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
        panelMain.add(startButton, c);

        c.weightx = 1.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = currY++;
        panelMain.add(pauseButton, c);

        c.weightx = 1.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = currY++;
        panelMain.add(showRoundsButton, c);

        c.weightx = 1.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = currY++;
        panelMain.add(nextGenerationButton, c);

        c.weightx = 0.1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = currY;
        panelMain.add(labelRoundTimeout, c);

        c.weightx = 0.7;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = currY;
        panelMain.add(roundLengthSlider, c);

        c.weightx = 0.2;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = currY++;
        panelMain.add(labelRoundTimeoutVal, c);

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

    private void registerButtons() {
        startButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.startGameLoop();
        }));

        pauseButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.pauseGameLoop();
        }));

        nextGenerationButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.generateNextGeneration();
        }));

        resetButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.resetGame();
        }));

        clearButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.clearGame();
        }));

        roundLengthSlider.addChangeListener(e -> SwingUtilities.invokeLater(() -> {
            fieldUI.updateRoundTimeout(roundLengthSlider.getValue());
            labelRoundTimeoutVal.setText(String.valueOf(roundLengthSlider.getValue()));
        }));

        fieldSizeSlider.addChangeListener(e -> SwingUtilities.invokeLater(() -> {
            if (updateGUIOnFieldSizeSliderChange) {
                fieldUI.setVisible(false);
                fieldUI.updateFieldSize(fieldSizeSlider.getValue());
                labelFieldSizeVal.setText(String.valueOf(fieldSizeSlider.getValue()));
                fieldUI.setVisible(true);
            } else {
                updateGUIOnFieldSizeSliderChange = true;
            }
        }));

        nrOfFieldsSlider.addChangeListener(e -> SwingUtilities.invokeLater(() -> {
            if (updateGUIOnFieldDimensionSliderChange) {
                fieldUI.setVisible(false);
                fieldUI.updateNrOfFields(nrOfFieldsSlider.getValue());
                labelNrOfFieldsVal.setText(String.valueOf(nrOfFieldsSlider.getValue()));
                fieldUI.setVisible(true);
            } else {
                updateGUIOnFieldDimensionSliderChange = true;
            }
        }));

        showRoundsButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.toggleShowRounds();
        }));

        importButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            FileData fileData = fieldUI.importFile();
            if (fileData != null) {
                fieldUI.setVisible(false);
                updateGUIOnFieldSizeSliderChange = false;
                updateGUIOnFieldDimensionSliderChange = false;
                fieldSizeSlider.setValue(fileData.getFieldSize());
                labelFieldSizeVal.setText(String.valueOf(fieldSizeSlider.getValue()));
                fieldUI.setFile(fileData);
                fieldUI.setVisible(true);
            }
        }));

        exportButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            fieldUI.exportFile();
        }));

        aboutButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.APP_NAME).append("\n\n");
            sb.append(String.format("Version: %s", Constants.APP_VERSION)).append("\n");
            sb.append(Constants.APP_COPYRIGHT);
            showAboutDialog(String.format("About %s", Constants.APP_NAME), sb.toString());
        }));

        quitButton.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            System.exit(0);
        }));
    }

}
