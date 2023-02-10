/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.enums;

/**
 * Enumeration for a field value
 */
public enum FieldValue {

    /**
     * The field is active (living)
     */
    ACTIVE("O"),
    /**
     * The field is inactive (dead)
     */
    INACTIVE("X");

    private String val;

    /**
     * Constructor
     * 
     * @param val The value of the field
     */
    FieldValue(String val) {
        this.val = val;
    }

    /**
     * Returns the value of the field
     * 
     * @return the value of the field
     */
    public String getValue() {
        return this.val;
    }

    @Override
    public String toString() {
        return getValue();
    }

}
