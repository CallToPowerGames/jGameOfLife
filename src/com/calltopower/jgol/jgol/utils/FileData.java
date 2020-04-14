/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.utils;

import com.calltopower.jgol.jgol.enums.FieldValue;

/**
 * File data construct
 */
public class FileData {

    private String name;
    private int nrOfFields;
    private int fieldSize;
    private FieldValue[][] values;

    public FileData(String name, int nrOfFields, int fieldSize, FieldValue[][] values) {
        this.name = name;
        this.nrOfFields = nrOfFields;
        this.fieldSize = fieldSize;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public int getNrOfFields() {
        return nrOfFields;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public FieldValue[][] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return String.format("FileData[Name=%s, NrOfFields=%d, Size=%d]", name, nrOfFields, fieldSize);
    }

}
