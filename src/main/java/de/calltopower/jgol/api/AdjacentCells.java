/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.api;

/**
 * Interface for adjacent cell entities
 */
public interface AdjacentCells extends Printable {

    /**
     * Returns the number of active neighbors
     * 
     * @return the number of active neighbors
     */
    int getNumberOfActiveNeighbors();

}
