/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.api;

import de.calltopower.jgol.enums.FieldValue;

/**
 * Interface for game field entities
 */
public interface GameField extends Printable, Drawable {

    /**
     * Returns the whole field
     * 
     * @return the whole field
     */
    Field[][] getField();

    /**
     * Returns the field on row/column
     * @param x Row
     * @param y Column
     * @return The field on row/column
     */
    Field get(int x, int y);

    /**
     * Highlights the field on cordinate
     * 
     * @param xCoord x-coordinate
     * @param yCoord y-coordinate
     */
    void highlightField(int xCoord, int yCoord);

    /**
     * Toggles a value of field on coordinate
     * 
     * @param xCoord x-coordinate
     * @param yCoord y-coordinate
     */
    void toggleField(int xCoord, int yCoord);

    /**
     * Sets a value to field on row/column
     * 
     * @param x Row
     * @param y Column
     * @param value The value
     * @return true if set, false else
     */
    boolean set(int x, int y, FieldValue value);

    /**
     * Sets a (random) seed
     */
    void seed();

    /**
     * Sets all field values
     * 
     * @param values The field values
     * @return true if successfully set, false else
     */
    boolean setValues(FieldValue[][] values);

    /**
     * Sets the seed
     * 
     * @param seed The seed
     */
    void seed(long seed);

    /**
     * Generates a new generation
     */
    void generateNewGeneration();

    /**
     * Returns the number of alive cells
     * 
     * @return The number of alive cells
     */
    int getNrOfAliveCells();

}
