/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.calltopower.jgol.api.Game;
import de.calltopower.jgol.impl.GameImpl;
import de.calltopower.jgol.utils.Constants;
import de.calltopower.jgol.utils.FileData;
import de.calltopower.jgol.utils.FileUtils;

/**
 * The field UI
 */
public class FieldUI extends JFrame {

	private static final long serialVersionUID = -7001188129435057997L;

	private static final Logger LOGGER = LogManager.getLogger(FieldUI.class);

	private JPanel panelMain;
	private GameImpl game;

	/**
	 * Constructor
	 */
	public FieldUI() {
		LOGGER.info("Initializing Field UI");

		setTitle(Constants.APP_NAME);

		initUI();

		panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridLayout layout = new GridLayout(1, 1, -1, -1);
		panelMain.setLayout(layout);

		setUndecorated(true);
		setContentPane(panelMain);

		int size = Constants.DEFAULT_NR_OF_FIELDS * Constants.DEFAULT_FIELD_SIZE;
		Dimension dimension = new Dimension(size, size + Constants.INFO_FIELD_SIZE);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		pack();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

		registerMouseListeners();

		game = new GameImpl(panelMain, Constants.DEFAULT_NR_OF_FIELDS, Constants.DEFAULT_FIELD_SIZE);
	}

	protected Game getGame() {
		return game;
	}

	protected boolean toggleGameLoop() {
		LOGGER.info("Toggle game loop");
		if (!game.isRunning()) {
			LOGGER.info("Starting game loop");
			new JGOLGameLoop().execute();
			return true;
		} else {
			LOGGER.info("Pausing game");
			game.pause();
			return false;
		}

		// return game.isRunning() is not "fast" enough
	}

	protected void generateNextGeneration() {
		LOGGER.info("Generate next generation");
		game.pause();
		game.generateNextGeneration();
	}

	protected void resetGame() {
		LOGGER.info("Reset game");
		game.pause();
		game.reset();
	}

	protected void clearGame() {
		LOGGER.info("Clear game");
		game.pause();
		game.clear();
	}

	protected void updateGenerationTimeout(long timeout) {
		LOGGER.info("Updating generation timeout to " + timeout);
		game.pause();
		if (timeout > 0) {
			game.setGenerationTimeout(timeout);
		} else {
			game.resetGenerationTimeout();
		}
	}

	protected void updateFieldSize(int fieldSize) {
		LOGGER.info("Updating field size to " + fieldSize);
		clearGame();
		long generationTimeout = game.getGenerationTimeout();
		int nrOfFields = game.getNrOfFields();

		int size = nrOfFields * fieldSize;
		Dimension dimension = new Dimension(size, size + Constants.INFO_FIELD_SIZE);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		pack();
		setLocationRelativeTo(null);

		game = new GameImpl(panelMain, nrOfFields, fieldSize);
		game.setGenerationTimeout(generationTimeout);
	}

	protected void updateNrOfFields(int nrOfFields) {
		LOGGER.info("Updating nr of fields to " + nrOfFields);
		clearGame();

		long generationTimeout = game.getGenerationTimeout();
		int fieldSize = game.getFieldSize();

		int size = nrOfFields * fieldSize;
		Dimension dimension = new Dimension(size, size + Constants.INFO_FIELD_SIZE);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		pack();
		setLocationRelativeTo(null);

		game = new GameImpl(panelMain, nrOfFields, fieldSize);
		game.setGenerationTimeout(generationTimeout);
	}

	protected FileData importFile() {
		LOGGER.info("Importing pattern file");
		game.pause();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JGameOfLife files", Constants.JGOL_FILE_SUFFIX);
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getSelectedFile().getAbsolutePath();
			try {
				LOGGER.info("Reading from file " + fileName);
				return FileUtils.getInstance().readFromFile(fileName);
			} catch (IOException e) {
				LOGGER.error("The file " + fileName + " could not be imported", e);
				showErrorDialog("Error importing", String.format(Locale.ENGLISH, "The file could not be imported"));
			}
		} else {
			LOGGER.info("Cancelled dialog");
		}

		return null;
	}

	protected void setFile(FileData fileData) {
		LOGGER.info("Setting file data");
		int size = fileData.getNrOfFields() * fileData.getFieldSize();
		Dimension dimension = new Dimension(size, size + Constants.INFO_FIELD_SIZE);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		pack();
		setLocationRelativeTo(null);

		game.pause();
		game = new GameImpl(panelMain, fileData.getNrOfFields(), fileData.getFieldSize());
		if (!game.setValues(fileData.getValues())) {
			LOGGER.error("The file could not be imported.");
			showErrorDialog("Error importing", "The file could not be imported.");
		}
	}

	protected void exportFile() {
		LOGGER.info("Exporting pattern file");
		game.pause();
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getSelectedFile().getAbsolutePath();
			try {
				LOGGER.info("Exporting to file " + fileName);
				FileUtils.getInstance().writeToFile(fileName, game.getGameField().getField(), game.getNrOfFields(),
						game.getFieldSize());
			} catch (IOException e) {
				LOGGER.error("The file " + fileName + " could not be exported", e);
				showErrorDialog("Error exporting", "The file could not be exported.");
			}
		}
	}

	private void initUI() {
		panelMain = new JPanel();
	}

	private void showErrorDialog(String title, String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, title, JOptionPane.ERROR_MESSAGE);
	}

	private void registerMouseListeners() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				game.highlightField(e.getX(), e.getY());
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				game.toggleField(e.getX(), e.getY());
			}
		});
	}

	private class JGOLGameLoop extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			game.loop();
			return null;
		}
	}

}
