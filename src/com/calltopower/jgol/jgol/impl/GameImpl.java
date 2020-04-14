/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.calltopower.jgol.jgol.api.FieldDimension;
import com.calltopower.jgol.jgol.api.Game;
import com.calltopower.jgol.jgol.api.GameField;
import com.calltopower.jgol.jgol.api.Initializable;
import com.calltopower.jgol.jgol.enums.FieldValue;
import com.calltopower.jgol.jgol.utils.Constants;

/**
 * Game implementation
 */
public class GameImpl implements Initializable, Game {

    private JPanel panel;
    private BufferedImage backBuffer;
    private GameField field;
    private Graphics panelGraphics;
    private Graphics backBufferGraphics;
    private long generations;
    private boolean gameLoopRunning;
    private boolean run;
    private long generationTimeout;
    private FieldDimension fieldDimension;
    private int nrOfFields;
    private int fieldSize;
    private int nrAllCells;

    /**
     * Constructor
     * 
     * @param panel The swing panel
     * @param nrOfFields The number of fields
     * @param fieldSize The field size
     */
    public GameImpl(JPanel panel, int nrOfFields, int fieldSize) {
        this.panel = panel;
        this.nrOfFields = nrOfFields;
        this.fieldSize = fieldSize;

        gameLoopRunning = false;
        generations = 0;
        run = false;

        int val = this.nrOfFields * this.fieldSize;
        fieldDimension = new FieldDimensionImpl(val, val);

        this.nrAllCells = this.nrOfFields * this.nrOfFields;

        resetGenerationTimeout();
        init();
    }

    @Override
    public void init() {
        if (!gameLoopRunning) {
            reset();
        }
    }

    @Override
    public GameField getGameField() {
        return field;
    }

    @Override
    public int getNrOfFields() {
        return nrOfFields;
    }

    @Override
    public int getFieldSize() {
        return fieldSize;
    }

    @Override
    public long getGenerationTimeout() {
        return generationTimeout;
    }

    @Override
    public FieldValue getFieldValue(int x, int y) {
        return field.get(x, y).getValue();
    }

    @Override
    public boolean isGameRunning() {
        return gameLoopRunning;
    }

    @Override
    public boolean setValues(FieldValue[][] values) {
        // clear();
        boolean set = field.setValues(values);
        redraw(null);

        return set;
    }

    @Override
    public void setGenerationTimeout(long timeout) {
        generationTimeout = timeout;
    }

    @Override
    public void toggleField(int x, int y) {
        field.toggleField(x, y);
        redraw(null);
    }

    @Override
    public void reset() {
        backBuffer = new BufferedImage(fieldDimension.getWidth(),
                                        fieldDimension.getHeight() + Constants.INFO_FIELD_SIZE,
                                        BufferedImage.TYPE_INT_RGB);
        field = new GameFieldImpl(nrOfFields, fieldSize);
        gameLoopRunning = false;
        generations = 0;
        run = false;
        field.seed();
        redraw(null);
    }

    @Override
    public void resetGenerationTimeout() {
        generationTimeout = Constants.DEFAULT_GENERATION_TIMEOUT;
    }

    @Override
    public void loop() {
        if (!gameLoopRunning) {
            gameLoopRunning = true;
            run = true;

            while (run) {
                generateNextGenerationInternal();
                sleep(generationTimeout);
            }
            gameLoopRunning = false;
        }
    }

    @Override
    public void pause() {
        run = false;
    }

    @Override
    public void clear() {
        backBuffer = new BufferedImage(fieldDimension.getWidth(),
                                        fieldDimension.getHeight() + Constants.INFO_FIELD_SIZE,
                                        BufferedImage.TYPE_INT_RGB);
        field = new GameFieldImpl(nrOfFields, fieldSize);
        gameLoopRunning = false;
        generations = 0;
        run = false;
        redraw(null);
    }

    @Override
    public void generateNextGeneration() {
        if (!gameLoopRunning) {
            generateNextGenerationInternal();
        }
    }

    @Override
    public void highlightField(int x, int y) {
        field.highlightField(x, y);
        redraw(null);
    }

    @Override
    public void draw(Graphics graphics) {
        backBufferGraphics.setColor(Color.black);
        backBufferGraphics.fillRect(0, 0, backBuffer.getWidth(), backBuffer.getHeight());
        field.draw(backBufferGraphics);

        backBufferGraphics.setColor(Color.black);
        backBufferGraphics.fillRect(0,
                                    backBuffer.getHeight() - Constants.INFO_FIELD_SIZE,
                                    backBuffer.getWidth(),
                                    Constants.INFO_FIELD_SIZE);
        backBufferGraphics.setColor(Color.white);
        backBufferGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        int nrAliveCells = field.getNrOfAliveCells();
        int nrDeadCells = nrAllCells - nrAliveCells;
        StringBuilder sb = new StringBuilder();
        sb.append("Number of generations: ");
        sb.append(generations);
        sb.append("    ");
        sb.append(nrAllCells);
        sb.append(" Cells (alive: ").append(nrAliveCells).append(", dead: ").append(nrDeadCells).append(")");
        backBufferGraphics.drawString(sb.toString(), 5, backBuffer.getHeight() - Constants.INFO_FIELD_SIZE + 25);

        panelGraphics.drawImage(backBuffer, 0, 0, null);
    }

    @Override
    public void redraw(Graphics graphics) {
        panelGraphics = this.panel.getGraphics();
        backBufferGraphics = backBuffer.getGraphics();
        draw(null);
        backBufferGraphics.dispose();
        panelGraphics.dispose();
    }

    private void generateNextGenerationInternal() {
        ++generations;
        field.generateNewGeneration();
        panelGraphics = this.panel.getGraphics();
        backBufferGraphics = backBuffer.getGraphics();
        draw(null);
        backBufferGraphics.dispose();
        panelGraphics.dispose();
    }

    private void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
