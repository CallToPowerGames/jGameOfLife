/**
 * This file is part of JGameOfLife.
 * 
 * Copyright (C) 2016-2020 Denis Meyer
 */

package com.calltopower.jgol.jgol.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.calltopower.jgol.jgol.impl.GameImpl;
import com.calltopower.jgol.jgol.utils.Constants;
import com.calltopower.jgol.jgol.utils.FileData;
import com.calltopower.jgol.jgol.utils.FileUtils;

/**
 * The field UI
 */
public class FieldUI extends JFrame {

    private static final long serialVersionUID = -7001188129435057997L;

    private JPanel panelMain;
    private GameImpl game;

    /**
     * Constructor
     */
    public FieldUI() {
        setTitle(Constants.APP_NAME);

        initUI();

        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridLayout layout = new GridLayout(1, 1, -1, -1);
        panelMain.setLayout(layout);

        setUndecorated(true);
        setContentPane(panelMain);

        int size = Constants.DEFAULT_NR_OF_FIELDS * Constants.DEFAULT_FIELD_SIZE;
        Dimension dimension = new Dimension(size, size);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        registerMouseListeners();

        game = new GameImpl(panelMain, Constants.DEFAULT_NR_OF_FIELDS, Constants.DEFAULT_FIELD_SIZE);
    }

    protected void startGameLoop() {
        new JGOLGameLoop().execute();
    }

    protected void pauseGameLoop() {
        game.pause();
    }

    protected void generateNextGeneration() {
        game.pause();
        game.generateNextGeneration();
    }

    protected void resetGame() {
        game.reset();
    }

    protected void clearGame() {
        game.clear();
    }

    protected void updateRoundTimeout(long timeout) {
        game.pause();
        if (timeout > 0) {
            game.setRoundTimeout(timeout);
        } else {
            game.resetRoundTimeout();
        }
    }

    protected void updateFieldSize(int fieldSize) {
        clearGame();
        long roundTimeout = game.getRoundTimeout();
        int nrOfFields = game.getNrOfFields();

        int size = nrOfFields * fieldSize;
        Dimension dimension = new Dimension(size, size);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        pack();
        setLocationRelativeTo(null);

        game = new GameImpl(panelMain, nrOfFields, fieldSize);
        game.setRoundTimeout(roundTimeout);
    }

    protected void updateNrOfFields(int nrOfFields) {
        clearGame();

        long roundTimeout = game.getRoundTimeout();
        int fieldSize = game.getFieldSize();

        int size = nrOfFields * fieldSize;
        Dimension dimension = new Dimension(size, size);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        pack();
        setLocationRelativeTo(null);

        game = new GameImpl(panelMain, nrOfFields, fieldSize);
        game.setRoundTimeout(roundTimeout);
    }

    protected void toggleShowRounds() {
        game.toggleShowRounds();
    }

    protected FileData importFile() {
        game.pause();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JGameOfLife files", Constants.JGOL_FILE_SUFFIX);
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                return FileUtils.getInstance().readFromFile(chooser.getSelectedFile().getAbsolutePath());
            } catch (IOException e) {
                showErrorDialog("Error importing", String.format(Locale.ENGLISH,
                                                                    "The file could not be imported: %s",
                                                                    e.getLocalizedMessage()));
            }
        }

        return null;
    }

    protected void setFile(FileData fileData) {
        int size = fileData.getNrOfFields() * fileData.getFieldSize();
        Dimension dimension = new Dimension(size, size);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        pack();
        setLocationRelativeTo(null);

        game.pause();
        game = new GameImpl(panelMain, fileData.getNrOfFields(), fileData.getFieldSize());
        if (!game.setValues(fileData.getValues())) {
            showErrorDialog("Error importing", "The file could not be imported.");
        }
    }

    protected void exportFile() {
        game.pause();
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileUtils.getInstance().writeToFile(chooser.getSelectedFile().getAbsolutePath(),
                                                    game.getGameField().getField(),
                                                    game.getNrOfFields(),
                                                    game.getFieldSize());
            } catch (IOException e) {
                showErrorDialog("Error exporting", "The file could not be exported.");
            }
        }
    }

    private void initUI() {
        panelMain = new JPanel();
    }

    private void showErrorDialog(String title, String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg, title, JOptionPane.ERROR_MESSAGE);
    }

    private void registerMouseListeners() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                game.highlightField(e.getX(), e.getY());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                game.toggleField(e.getX(), e.getY());
            }
        });
    }

    private class JGOLGameLoop extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            game.loop();
            return null;
        }
    }

}
