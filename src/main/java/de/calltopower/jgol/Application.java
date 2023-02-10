/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol;

import de.calltopower.jgol.ui.ControlsUI;
import de.calltopower.jgol.utils.Helper;
import de.calltopower.jgol.Application;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class, starting point
 */
public class Application {

	private static final Logger LOGGER = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		Helper.printSystemInformation();

		LOGGER.info("Setting platform look and feel");
		Helper.setPlatformLookAndFeel();

		LOGGER.info("Setting up UI");
		SwingUtilities.invokeLater(() -> {
			ControlsUI controls = new ControlsUI();
			controls.setVisible(true);
		});
	}

}
