/*
* Copyright (c) Stuart Clark
*
* This project by Stuart Clark is free software: you can redistribute it and/or modify it under the terms
* of the GNU General Public License as published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version. This project is distributed in the hope that it will be
* useful for educational purposes, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License along with this project.
* If not, please see the GNU website.
*/
package com.stuart.tourny.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

/**
 * Class to display a Result Set as a non-modifiable table. Simply add this as a
 * component to a panel, then call the populateData method.
 */
public class ResultSetTablePanel {

    private static final String CHOOSER_TITLE = "Save as...";
    private static final Logger LOGGER = Logger.getLogger(ResultSetTablePanel.class);

    private JScrollPane scrollPane;
    private JTable dataTable;
    private ResultSet results;
    private JPanel panel;

    /**
     * Create a ResultSetTablePanel
     */
    public ResultSetTablePanel() {
	panel = new JPanel();
	panel.setBackground(TournamentGUI.getBackgroundColour());
	initGUI();
    }

    public JPanel getPanel() {
	return panel;
    }

    /**
     * Create the GUI
     */
    private void initGUI() {
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[] { 0, 0 };
	gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
	gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
	gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
	panel.setLayout(gridBagLayout);
	panel.setPreferredSize(new Dimension(400, 400));
	panel.setMinimumSize(new Dimension(400, 400));
	panel.setMaximumSize(new Dimension(400, 400));

	dataTable = new JTable();
	dataTable.setMinimumSize(new Dimension(200, 300));
	dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	scrollPane = new JScrollPane(dataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scrollPane.setMinimumSize(new Dimension(200, 200));
	scrollPane.setPreferredSize(new Dimension(200, 200));
	scrollPane.setViewportView(dataTable);

	panel.add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));
	JPopupMenu popupMenu = new JPopupMenu();
	JMenuItem saveItem = new JMenuItem("Save to CSV");
	saveItem.addActionListener(e -> saveResultsToCsv());
	popupMenu.add(saveItem);
	dataTable.setComponentPopupMenu(popupMenu);
    }

    /**
     * Convert the {@link ResultSet} into a table to display on the screen.
     *
     * @param results
     *            - ResultSet to display
     */
    public void populateData(ResultSet liveResults) {
	this.results = liveResults;
	TableModel tableModel = resultSetToTableModel(this.results);
	dataTable.setModel(tableModel);
	setupColumns(dataTable.getColumnModel());
	scrollPane.setViewportView(dataTable);
	LOGGER.debug("ResultSet successfully converted to a table.");
    }

    private static void setupColumns(TableColumnModel columnModel) {
	int columns = columnModel.getColumnCount();
	for (int i = 0; i < columns; i++) {
	    columnModel.getColumn(i).setPreferredWidth(100);
	}
    }

    private static TableModel resultSetToTableModel(ResultSet results) {
	try {
	    ResultSetMetaData metaData = results.getMetaData();
	    int numberOfColumns = metaData.getColumnCount();
	    Vector<String> columnNames = new Vector<>(numberOfColumns);

	    // Get the column names
	    for (int column = 0; column < numberOfColumns; column++) {
		columnNames.add(metaData.getColumnLabel(column + 1));
	    }
	    // Get all rows.
	    Vector<Object> rows = new Vector<>();

	    while (results.next()) {
		Vector<Object> newRow = new Vector<>();
		for (int i = 1; i <= numberOfColumns; i++) {
		    // Add each column to the new row...
		    newRow.add(results.getObject(i));
		}
		// Then add each new row to the Vector of rows
		rows.add(newRow);
	    }
	    return new DefaultTableModel(rows, columnNames);
	} catch (Exception e) {
	    String error = "Something went wrong converting the ResultSet: " + e;
	    LOGGER.error(error);
	    throw new IllegalStateException(error);
	}
    }

    private void saveResultsToCsv() {
	JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new java.io.File("."));
	chooser.setDialogTitle(CHOOSER_TITLE);
	FileFilter filter = new FileNameExtensionFilter("Comma Delimited (*.csv)", "csv");
	chooser.addChoosableFileFilter(filter);
	chooser.setFileFilter(filter);
	int userSelection = chooser.showSaveDialog(panel);
	if (userSelection == JFileChooser.APPROVE_OPTION) {
	    File rawFileToSave = chooser.getSelectedFile();
	    // Make sure the file ends with ".csv"
	    if (!rawFileToSave.getAbsolutePath().endsWith(".csv")) {
		File fileToSave = new File(rawFileToSave.getAbsolutePath() + ".csv");
		createAndWriteCsv(fileToSave);
	    } else {
		createAndWriteCsv(rawFileToSave);
	    }
	}
    }

    /**
     * Will create a CSV from a ResultSet, with each item wrapped in double
     * quotes.
     *
     * @param fileToSave
     *            - File to save
     */
    private void createAndWriteCsv(File fileToSave) {
	LOGGER.debug("Attempting to save " + fileToSave.getName());
	try (PrintWriter pw = new PrintWriter(fileToSave)) {
	    this.results.first();
	    ResultSetMetaData meta = this.results.getMetaData();
	    int numberOfColumns = meta.getColumnCount();
	    String dataHeaders = meta.getColumnName(1);
	    for (int i = 2; i < (numberOfColumns + 1); i++) {
		dataHeaders += "," + meta.getColumnName(i);
	    }
	    pw.println(dataHeaders);
	    while (this.results.next()) {
		String row = this.results.getString(1);
		for (int i = 2; i < (numberOfColumns + 1); i++) {
		    row += "," + this.results.getString(i);
		}
		pw.println(row);
	    }
	    LOGGER.debug("CSV Created!");
	} catch (Exception ex) {
	    LOGGER.error("Problem creating CSV file: " + ex);
	}
    }

}
