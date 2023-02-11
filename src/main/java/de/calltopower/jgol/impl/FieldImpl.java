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
import lombok.Getter;
import lombok.Setter;

/**
 * Field implementation
 */
public class FieldImpl implements Field {

    private Color colorActive = Color.white;
    private Color colorInactive = Color.black;
    private Color colorHighlightedActive = Color.blue;
    private Color colorHighlightedInactive = Color.yellow;

    @Getter
    private int x;

    @Getter
    private int y;

    @Getter
    private int size;

    @Getter
    private FieldValue value;

    @Getter
    @Setter
    private boolean highlighted;

    /**
     * Constructor
     * 
     * @param x     Row
     * @param y     Column
     * @param size  The size
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
