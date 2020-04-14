/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol;

import com.calltopower.jgol.jgol.ui.ControlsUI;
import javax.swing.SwingUtilities;

/**
 * Main class, starting point
 */
public class JGameOfLife {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlsUI controls = new ControlsUI();
            controls.setVisible(true);
        });
    }

}
