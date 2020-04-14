/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.api;

import java.awt.Graphics;

/**
 * Interface for drawable entities
 */
public interface Drawable {

    /**
     * Draws the cell
     * 
     * @param graphics The graphics
     */
    void draw(Graphics graphics);

    /**
     * Redraws the cell
     * 
     * @param graphics The graphics
     */
    void redraw(Graphics graphics);

}
