/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.utils;

import de.calltopower.jgol.enums.FieldValue;
import lombok.Getter;

/**
 * File data construct
 */
@Getter
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

    @Override
    public String toString() {
        return String.format("FileData[Name=%s, NrOfFields=%d, Size=%d]", name, nrOfFields, fieldSize);
    }

}
