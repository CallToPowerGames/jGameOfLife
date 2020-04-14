/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.api;

import com.calltopower.jgol.jgol.enums.FieldValue;

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
     * Returns the round timeout
     * 
     * @return the round timeout
     */
    long getRoundTimeout();

    /**
     * Returns the field value at row/column
     * 
     * @param x Row
     * @param y Column
     * @return The field value
     */
    FieldValue getFieldValue(int x, int y);

    /**
     * Whether is showing rounds
     * 
     * @return whether is showing rounds
     */
    boolean isShowingRounds();

    /**
     * Returns whether the game is running
     * 
     * @return whether the game is running
     */
    boolean isGameRunning();

    /**
     * Sets all field values
     * 
     * @param values The field values
     * @return true if successfully set, false else
     */
    boolean setValues(FieldValue[][] values);

    /**
     * Sets a new round timeout
     * 
     * @param timeout new round timeout
     */
    void setRoundTimeout(long timeout);

    /**
     * Toggles the rounds show
     */
    void toggleShowRounds();

    /**
     * Shows/hides the rounds
     * @param showRounds Boolean flag whether to show or hide the rounds
     */
    void setShowRounds(boolean showRounds);

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
     * Resets the round timeout
     */
    void resetRoundTimeout();

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
