/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.impl;

import de.calltopower.jgol.api.FieldDimension;
import lombok.Getter;
import lombok.Setter;

/**
 * FieldDimension implementation
 */
@Getter
@Setter
public class FieldDimensionImpl implements FieldDimension {

    private int width;
    private int height;

    /**
     * Constructor
     * 
     * @param width  The width
     * @param height The height
     */
    public FieldDimensionImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("FieldDimension[width=%d, height=%d]", width, height);
    }

}
