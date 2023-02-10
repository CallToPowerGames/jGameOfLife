/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.api;

import de.calltopower.jgol.enums.FieldValue;

/**
 * Interface for game entities
 */
public interface Game extends Drawable {

    /**
     * Returns the game field
     * 
     * @return the game field
     */
    GameField getGameField();

    /**
     * Returns the number of fields
     * 
     * @return the number of fields
     */
    int getNrOfFields();

    /**
     * Returns the field size
     * 
     * @return the field size
     */
    int getFieldSize();

    /**
     * Returns the generation timeout
     * 
     * @return the generation timeout
     */
    long getGenerationTimeout();

    /**
     * Returns the field value at row/column
     * 
     * @param x Row
     * @param y Column
     * @return The field value
     */
    FieldValue getFieldValue(int x, int y);

    /**
     * Returns whether the game is running
     * 
     * @return whether the game is running
     */
    boolean isRunning();

    /**
     * Sets all field values
     * 
     * @param values The field values
     * @return true if successfully set, false else
     */
    boolean setValues(FieldValue[][] values);

    /**
     * Sets a new generation timeout
     * 
     * @param timeout new generation timeout
     */
    void setGenerationTimeout(long timeout);

    /**
     * Toggles the field on row/column
     * 
     * @param x Row
     * @param y Column
     */
    void toggleField(int x, int y);

    /**
     * Resets the game
     */
    void reset();

    /**
     * Resets the generation timeout
     */
    void resetGenerationTimeout();

    /**
     * Loops the game one time
     */
    void loop();

    /**
     * Pauses the game
     */
    void pause();

    /**
     * Clears the game
     */
    void clear();

    /**
     * Generates a new generation
     */
    void generateNextGeneration();

    /**
     * Highlights the field on row/column
     * 
     * @param x Row
     * @param y Column
     */
    void highlightField(int x, int y);

}
