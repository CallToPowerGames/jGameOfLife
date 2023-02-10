/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.impl;

import de.calltopower.jgol.api.AdjacentCells;
import de.calltopower.jgol.api.Field;
import de.calltopower.jgol.api.GameField;
import de.calltopower.jgol.api.Initializable;
import de.calltopower.jgol.enums.FieldValue;

/**
 * AdjacentCells implementation
 */
public class AdjacentCellsImpl implements Initializable, AdjacentCells {

    private GameField gameField;
    private Field field;
    private Field[][] adjacentCells;

    /**
     * Constructor
     * 
     * @param gameField The game field
     * @param field The field
     */
    public AdjacentCellsImpl(GameField gameField, Field field) {
        adjacentCells = new FieldImpl[3][3];
        this.gameField = gameField;
        this.field = field;
        init();
    }

    @Override
    public void init() {
        int x = field.getX();
        int y = field.getY();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int newX = x + (i == 0 ? -1 : (i == 1) ? 0 : 1);
                int newY = y + (j == 0 ? -1 : (j == 1) ? 0 : 1);
                try {
                    adjacentCells[i][j] = gameField.get(newX, newY);
                } catch (IndexOutOfBoundsException ex) {
                    adjacentCells[i][j] = new FieldImpl(newX, newY, field.getSize(), FieldValue.INACTIVE);
                }
            }
        }
    }

    @Override
    public int getNumberOfActiveNeighbors() {
        int nr = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (!(i == 1 && j == 1) && adjacentCells[i][j].getValue() == FieldValue.ACTIVE) {
                    ++nr;
                }
            }
        }

        return nr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AdjacentCells{\n");
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                sb.append(adjacentCells[i][j].getValue().getValue()).append(j >= 2 ? "\n" : " ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
