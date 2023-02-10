/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import de.calltopower.jgol.api.AdjacentCells;
import de.calltopower.jgol.api.Field;
import de.calltopower.jgol.api.FieldDimension;
import de.calltopower.jgol.api.GameField;
import de.calltopower.jgol.enums.FieldValue;

/**
 * GameField implementation
 */
public class GameFieldImpl implements GameField {

    private int nrOfFields;
    private int fieldSize;
    private FieldDimension dimension;
    private FieldDimension currentArraySize;
    private Field[][] field;
    private Field[][] fieldBackbuffer;
    private Color colorLines = Color.red;

    /**
     * Constructor
     * 
     * @param nrOfFields The number of fields
     * @param fieldSize The field size
     */
    public GameFieldImpl(int nrOfFields, int fieldSize) {
        this.nrOfFields = nrOfFields;
        this.fieldSize = fieldSize;

        int val = this.nrOfFields * this.fieldSize;
        this.dimension = new FieldDimensionImpl(val, val);

        currentArraySize = calculateArraySize();
        field = new FieldImpl[currentArraySize.getWidth()][currentArraySize.getHeight()];
        fieldBackbuffer = new FieldImpl[currentArraySize.getWidth()][currentArraySize.getHeight()];
        initFieldsWith(FieldValue.INACTIVE);
    }

    @Override
    public Field[][] getField() {
        return field;
    }

    @Override
    public Field get(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        return this.field[x][y];
    }

    @Override
    public void highlightField(int xCoord, int yCoord) {
        for (Field[] aField : field) {
            for (int j = 0; j < field[0].length; ++j) {
                aField[j].setHighlighted(aField[j].isInside(yCoord, xCoord));
            }
        }
    }

    @Override
    public void toggleField(int xCoord, int yCoord) {
        for (Field[] aField : field) {
            for (int j = 0; j < field[0].length; ++j) {
                if (aField[j].isInside(yCoord, xCoord)) {
                    aField[j].toggleValue();
                    return;
                }
            }
        }
    }

    @Override
    public boolean set(int x, int y, FieldValue value) {
        if (!isValidCoordinate(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        this.field[x][y].setValue(value);
        return true;
    }

    @Override
    public void seed() {
        seed(new Random());
    }

    @Override
    public boolean setValues(FieldValue[][] values) {
        if (!(values.length == field.length) || !(values[0].length == field[0].length)) {
            return false;
        }
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                field[i][j].setValue(values[i][j]);
            }
        }

        return true;
    }

    @Override
    public void seed(long seed) {
        seed(new Random(seed));
    }

    @Override
    public void generateNewGeneration() {
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                Field currF = field[i][j];
                fieldBackbuffer[i][j] = new FieldImpl(currF.getX(), currF.getY(), currF.getSize(), currF.getValue());
                AdjacentCells adjacentCells = new AdjacentCellsImpl(this, currF);
                int nrOfNeighbors = adjacentCells.getNumberOfActiveNeighbors();
                if (fieldBackbuffer[i][j].getValue() == FieldValue.ACTIVE) {
                    // Each cell with one or no neighbors dies
                    // Each cell with four or more neighbors dies
                    // Each cell with two or three neighbors survives.
                    if (nrOfNeighbors < 2 || nrOfNeighbors > 3) {
                        fieldBackbuffer[i][j].setValue(FieldValue.INACTIVE);
                    }
                } else {
                    // Each cell with three neighbors becomes populated.
                    if (nrOfNeighbors == 3) {
                        fieldBackbuffer[i][j].setValue(FieldValue.ACTIVE);
                    }
                }
            }
        }
        for (int i = 0; i < field.length; ++i) {
            // System.arraycopy(fieldBackbuffer[i], 0, field[i], 0, field[0].length);
            for (int j = 0; j < field[0].length; ++j) {
                field[i][j] = fieldBackbuffer[i][j];
            }
        }
    }

    @Override
    public int getNrOfAliveCells() {
        int count = 0;
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                if (field[i][j].getValue().equals(FieldValue.ACTIVE)) {
                    ++count;
                }
            }
        }

        return count;
    }

    @Override
    public void draw(Graphics graphics) {
        drawFields(graphics);
        drawLines(graphics);
    }

    @Override
    public void redraw(Graphics graphics) {
        draw(graphics);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GameField{\n");
        currentArraySize = calculateArraySize();
        for (int i = 0; i < currentArraySize.getWidth(); ++i) {
            for (int j = 0; j < currentArraySize.getHeight(); ++j) {
                sb.append(field[i][j].getValue().getValue()).append(j >= currentArraySize.getHeight() - 1 ? "\n" : " ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private void seed(Random random) {
        for (Field[] aField : field) {
            for (int j = 0; j < field[0].length; ++j) {
                aField[j].setValue(random.nextBoolean() ? FieldValue.ACTIVE : FieldValue.INACTIVE);
            }
        }
    }

    private void drawFields(Graphics graphics) {
        for (Field[] aField : field) {
            for (int j = 0; j < field[0].length; ++j) {
                aField[j].draw(graphics);
            }
        }
    }

    private void drawLines(Graphics graphics) {
        graphics.setColor(colorLines);
        int stepHoriz = dimension.getWidth() / currentArraySize.getWidth();
        for (int i = stepHoriz; i < dimension.getWidth(); i += stepHoriz) {
            graphics.drawLine(i, 0, i, dimension.getWidth());
            graphics.setColor(Color.red);
        }
        int stepVert = dimension.getHeight() / currentArraySize.getWidth();
        for (int i = stepVert; i < dimension.getHeight(); i += stepVert) {
            graphics.drawLine(0, i, dimension.getHeight(), i);
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < dimension.getWidth() && y >= 0 && y < dimension.getHeight();
    }

    private FieldDimensionImpl calculateArraySize() {
        return new FieldDimensionImpl(dimension.getWidth() / fieldSize, dimension.getHeight() / fieldSize);
    }

    private void initFieldsWith(FieldValue initialValue) {
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                field[i][j] = new FieldImpl(i, j, fieldSize, initialValue);
                fieldBackbuffer[i][j] = new FieldImpl(i, j, fieldSize, initialValue);
            }
        }
    }

}
