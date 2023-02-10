/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.impl;

import java.awt.Color;
import java.awt.Graphics;

import de.calltopower.jgol.api.Field;
import de.calltopower.jgol.enums.FieldValue;

/**
 * Field implementation
 */
public class FieldImpl implements Field {

    private Color colorActive = Color.white;
    private Color colorInactive = Color.black;
    private Color colorHighlightedActive = Color.blue;
    private Color colorHighlightedInactive = Color.yellow;

    private int x;
    private int y;
    private int size;
    private FieldValue value;
    private boolean highlighted;

    /**
     * Constructor
     * 
     * @param x Row
     * @param y Column
     * @param size The size
     * @param value The value
     */
    public FieldImpl(int x, int y, int size, FieldValue value) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.value = value;
        highlighted = false;
    }

    @Override
    public FieldValue getValue() {
        return value;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isHighlighted() {
        return highlighted;
    }

    @Override
    public boolean isInside(int xCoord, int yCoord) {
        return xCoord > (x * size) && xCoord < (x * size + size) && yCoord > (y * size) && yCoord < (y * size + size);
    }

    @Override
    public void setValue(FieldValue value) {
        this.value = value;
    }

    @Override
    public void toggleValue() {
        setValue(value == FieldValue.ACTIVE ? FieldValue.INACTIVE : FieldValue.ACTIVE);
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    @Override
    public void draw(Graphics graphics) {
        boolean active = value == FieldValue.ACTIVE;
        if (active) {
            graphics.setColor(highlighted ? colorHighlightedActive : colorActive);
        } else {
            graphics.setColor(highlighted ? colorHighlightedInactive : colorInactive);
        }
        graphics.fillRect(y * size, x * size, size, size);
        // graphics.setColor(active ? colorInactive : colorActive);
        // graphics.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        // graphics.drawString(String.valueOf(x).concat("-").concat(String.valueOf(y)),
        // y * size, x * size + size / 2);
    }

    @Override
    public void redraw(Graphics graphics) {
        draw(graphics);
    }

}
