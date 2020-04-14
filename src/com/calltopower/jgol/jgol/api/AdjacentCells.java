/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.api;

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
