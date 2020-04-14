/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.api;

import com.calltopower.jgol.jgol.enums.FieldValue;

/**
 * Interface for field entities
 */
public interface Field extends Drawable {

    /**
     * Returns the value of the field
     * 
     * @return the value of the field
     */
    FieldValue getValue();

    /**
     * Returns the x-coordinate of the field
     * 
     * @return the y-coordinate of the field
     */
    int getX();

    /**
     * Returns the height of the field
     * 
     * @return the height of the field
     */
    int getY();

    /**
     * Returns the size of the field
     * 
     * @return the size of the field
     */
    int getSize();

    /**
     * Returns whether the field is highlighted
     * 
     * @return Boolean flag whether the field is highlighted
     */
    boolean isHighlighted();

    /**
     * Returns whether the coordinate is inside the field
     * 
     * @param xCoord The x-coordinate
     * @param yCoord The y-coordinate
     * @return Boolean flag whether the coordinate is inside the field
     */
    boolean isInside(int xCoord, int yCoord);

    /**
     * Sets the value
     * 
     * @param value The value
     */
    void setValue(FieldValue value);

    /**
     * Toggles the value of the field
     */
    void toggleValue();

    /**
     * Sets highlightes flag
     * 
     * @param highlighted Boolean flag whether field is highlighted
     */
    void setHighlighted(boolean highlighted);

}
