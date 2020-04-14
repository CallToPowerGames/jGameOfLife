/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.calltopower.jgol.jgol.api.Field;
import com.calltopower.jgol.jgol.enums.FieldValue;

/**
 * Some file utils.
 * Singleton.
 */
public final class FileUtils {

    private static FileUtils instance;

    private FileUtils() {
        // Nothing to see here...
    }

    /**
     * Returns the instance of the singleton
     * 
     * @return the instance of the singleton
     */
    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    /**
     * Reads field from file
     * 
     * @param fileName The file name
     * @return FileData
     * @throws IOException
     */
    public FileData readFromFile(String fileName) throws IOException {
        Path file = Paths.get(fileName);
        List<String> allLines = Files.readAllLines(file, Charset.forName("UTF-8"));
        List<String> lines = new ArrayList<>();

        String parsedName = "";
        int parsedNrOfFields = -1;
        int parsedFieldSize = -1;
        for (int i = 0; i < allLines.size(); ++i) {
            if (i == 0) {
                String[] valSplit = allLines.get(i).split(Constants.JGOL_DATA_SEPARATOR);
                if (valSplit.length != 3) {
                    throw new IOException("Could not parse input file: Header data unknown");
                }
                parsedName = valSplit[0];
                parsedNrOfFields = Integer.parseInt(valSplit[1]);
                parsedFieldSize = Integer.parseInt(valSplit[2]);
                if (!parsedName.equals(Constants.JGOL_DATA_NAME)) {
                    throw new IOException("Could not parse input file: Header data unknown (name)");
                }
            } else {
                lines.add(allLines.get(i));
            }
        }

        if (parsedName.equals("") || parsedNrOfFields < 0) {
            throw new IOException("Could not parse input file: Header data corrupt");
        }

        if (parsedFieldSize < Constants.GAME_MIN_FIELD_SIZE) {
            throw new IOException(String.format("Field size must be >= %d", Constants.GAME_MIN_FIELD_SIZE));
        }

        int size = parsedNrOfFields; // / parsedFieldSize;
        FieldValue[][] values = new FieldValue[size][size];
        for (int i = 0; i < lines.size(); ++i) {
            String[] valSplit = lines.get(i).split("");
            for (int j = 0; j < valSplit.length; ++j) {
                values[i][j] = valSplit[j].equals("O") ? FieldValue.ACTIVE : FieldValue.INACTIVE;
            }
        }
 
        return new FileData(parsedName, parsedNrOfFields, parsedFieldSize, values);
    }

    /**
     * Writes to file
     * 
     * @param fileName The file name
     * @param field The field
     * @param nrOfFields The number of fields
     * @param fieldSize The field size
     * @throws IOException
     */
    public void writeToFile(String fileName,
                            Field[][] field,
                            int nrOfFields,
                            int fieldSize) throws IOException {
        List<String> lines = new ArrayList<>();

        StringBuilder sbFirstLine = new StringBuilder("");
        sbFirstLine.append(Constants.JGOL_DATA_NAME)
                    .append(Constants.JGOL_DATA_SEPARATOR)
                    .append(nrOfFields)
                    .append(Constants.JGOL_DATA_SEPARATOR)
                    .append(fieldSize);
        lines.add(sbFirstLine.toString());

        for (Field[] aField : field) {
            StringBuilder sb = new StringBuilder("");
            for (int j = 0; j < field[0].length; ++j) {
                sb.append(aField[j].getValue() == FieldValue.ACTIVE ? "O" : "X");
            }
            lines.add(sb.toString());
        }
        Path file = Paths.get(fileName + "." + Constants.JGOL_FILE_SUFFIX);
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

}
