/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.utils;

/**
 * Some constants
 */
public final class Constants {

    public static final String APP_NAME = "jGameOfLife";
    public static final String APP_COPYRIGHT = "Copyright (C) 2016-2020 Denis Meyer";
    public static final String APP_VERSION = "v1.1.0";

    public static final String JGOL_DATA_NAME = "jgol";
    public static final String JGOL_FILE_SUFFIX = "jgol";
    public static final String JGOL_DATA_SEPARATOR = ";";

    public static final int INFO_FIELD_SIZE = 40;
    public static final int DEFAULT_NR_OF_FIELDS = 80;
    public static final int DEFAULT_FIELD_SIZE = 10; // Pixel per Field
    public static final long DEFAULT_GENERATION_TIMEOUT = 100;

    public static final int GAME_MIN_NR_OF_FIELDS = 5;
    public static final int GAME_MAX_NR_OF_FIELDS = 500;
    public static final int GAME_MIN_FIELD_SIZE = 2;
    public static final int GAME_MAX_FIELD_SIZE = 100;
    public static final int GAME_MAX_GENERATION_LENGTH = 1000;

    private Constants() {
        // Nothing to see here...
    }

}
