package com.stuart.tourny.view;

import org.apache.log4j.Logger;

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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Class to display a Result Set as a non-modifiable table. Simply add this as a component to a
 * panel, then call the populateData method.
 */
public class ResultSetTablePanel extends JPanel {

  private JScrollPane scrollPane;
  private JTable dataTable;
  private ResultSet results;

  private static final String CHOOSER_TITLE = "Save as...";
  private static final Logger log = Logger.getLogger(ResultSetTablePanel.class);

  /**
   * Create a ResultSetTablePanel
   */
  public ResultSetTablePanel() {
    setBackground(TournamentGUI.BACKGROUND);
    initGUI();
  }

  /**
   * Create the GUI
   */
  private void initGUI() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0};
    gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
    setLayout(gridBagLayout);
    scrollPane = new JScrollPane();
    add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                           GridBagConstraints.BOTH,
                                           new Insets(5, 0, 5, 5), 0, 0));
    dataTable = new JTable();
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
   *     - ResultSet to display
   */
  public void populateData(ResultSet results) {
    this.results = results;
    TableModel tableModel = resultSetToTableModel(this.results);
    dataTable.setModel(tableModel);
    scrollPane.setViewportView(dataTable);

  }

  private TableModel resultSetToTableModel(ResultSet results) {
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
      String error = "Something went wrong! " + e;
      log.error(error);
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
    int userSelection = chooser.showSaveDialog(this);
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
   * Will create a CSV from a ResultSet, with each item wrapped in double quotes.
   *
   * @param fileToSave
   *     - File to save
   */
  private void createAndWriteCsv(File fileToSave) {
    log.debug("Attempting to save " + fileToSave.getName());
    try (PrintWriter pw = new PrintWriter(fileToSave)) {
      results.first();
      ResultSetMetaData meta = results.getMetaData();
      int numberOfColumns = meta.getColumnCount();
      String dataHeaders = meta.getColumnName(1);
      for (int i = 2; i < numberOfColumns + 1; i++) {
        dataHeaders += "," + meta.getColumnName(i);
      }
      pw.println(dataHeaders);
      while (results.next()) {
        String row = results.getString(1);
        for (int i = 2; i < numberOfColumns + 1; i++) {
          row += "," + results.getString(i);
        }
        pw.println(row);
      }
    } catch (Exception ex) {
      log.error("Problem creating CSV file: " + ex);
    }
  }

}
