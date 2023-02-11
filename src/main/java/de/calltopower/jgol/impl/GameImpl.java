/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2023 Denis Meyer
 */

package de.calltopower.jgol.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import de.calltopower.jgol.api.FieldDimension;
import de.calltopower.jgol.api.Game;
import de.calltopower.jgol.api.GameField;
import de.calltopower.jgol.api.Initializable;
import de.calltopower.jgol.enums.FieldValue;
import de.calltopower.jgol.utils.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * Game implementation
 */
public class GameImpl implements Initializable, Game {

    private JPanel panel;
    private BufferedImage backBuffer;
    private Graphics panelGraphics;
    private Graphics backBufferGraphics;
    private long generations;
    private boolean gameLoopRunning;
    private boolean run;
    private int nrAllCells;
    private FieldDimension fieldDimension;

    @Getter
    private GameField gameField;

    @Getter
    @Setter
    private long generationTimeout;

    @Getter
    private int nrOfFields;

    @Getter
    private int fieldSize;

    /**
     * Constructor
     * 
     * @param panel      The swing panel
     * @param nrOfFields The number of fields
     * @param fieldSize  The field size
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
    public FieldValue getFieldValue(int x, int y) {
        return getGameField().get(x, y).getValue();
    }

    @Override
    public boolean isRunning() {
        return gameLoopRunning;
    }

    @Override
    public boolean setValues(FieldValue[][] values) {
        // clear();
        boolean set = getGameField().setValues(values);
        redraw(null);

        return set;
    }

    @Override
    public void toggleField(int x, int y) {
        getGameField().toggleField(x, y);
        redraw(null);
    }

    @Override
    public void reset() {
        backBuffer = new BufferedImage(fieldDimension.getWidth(),
                fieldDimension.getHeight() + Constants.INFO_FIELD_SIZE, BufferedImage.TYPE_INT_RGB);
        gameField = new GameFieldImpl(nrOfFields, fieldSize);
        gameLoopRunning = false;
        generations = 0;
        run = false;
        getGameField().seed();
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
                fieldDimension.getHeight() + Constants.INFO_FIELD_SIZE, BufferedImage.TYPE_INT_RGB);
        gameField = new GameFieldImpl(nrOfFields, fieldSize);
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
        getGameField().highlightField(x, y);
        redraw(null);
    }

    @Override
    public void draw(Graphics graphics) {
        backBufferGraphics.setColor(Color.black);
        backBufferGraphics.fillRect(0, 0, backBuffer.getWidth(), backBuffer.getHeight());
        getGameField().draw(backBufferGraphics);

        backBufferGraphics.setColor(Color.black);
        backBufferGraphics.fillRect(0, backBuffer.getHeight() - Constants.INFO_FIELD_SIZE, backBuffer.getWidth(),
                Constants.INFO_FIELD_SIZE);
        backBufferGraphics.setColor(Color.white);
        backBufferGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        int nrAliveCells = getGameField().getNrOfAliveCells();
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
        getGameField().generateNewGeneration();
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
