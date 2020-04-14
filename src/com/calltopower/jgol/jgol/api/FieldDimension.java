/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.api;

/**
 * Interface for field dimension entities
 */
public interface FieldDimension extends Printable {

    /**
     * Returns the width of the field dimension
     * 
     * @return the width of the field dimension
     */
    int getWidth();

    /**
     * Returns the height of the field dimension
     * 
     * @return the height of the field dimension
     */
    int getHeight();

    /**
     * Sets the width of the field dimension
     * 
     * @param width The width of the field dimension
     */
    void setWidth(int width);

    /**
     * Sets the height of the field dimension
     * 
     * @param height The height of the field dimension
     */
    void setHeight(int height);

}
