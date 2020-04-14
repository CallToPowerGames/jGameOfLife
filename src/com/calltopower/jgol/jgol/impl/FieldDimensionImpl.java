/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.impl;

import com.calltopower.jgol.jgol.api.FieldDimension;

/**
 * FieldDimension implementation
 */
public class FieldDimensionImpl implements FieldDimension {

    private int width;
    private int height;

    /**
     * Constructor
     * 
     * @param width The width
     * @param height The height
     */
    public FieldDimensionImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("FieldDimension[width=%d, height=%d]", width, height);
    }

}
