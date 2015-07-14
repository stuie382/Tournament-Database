package com.stuart.tourny.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ResultSetTablePanel extends JPanel {

  private JScrollPane scrollPane;
  private TableModel tableModel;
  private JTable dataTable;
  private ResultSet results;

  public ResultSetTablePanel() {
    setBackground(TournamentGUI.BACKGROUND);
    initGUI();
  }

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
  }

  public void populateData(ResultSet results) {
    this.results = results;
    tableModel = resultSetToTableModel();
    dataTable.setModel(tableModel);
    scrollPane.setViewportView(dataTable);

  }

  private TableModel resultSetToTableModel() {
    try {
      ResultSetMetaData metaData = results.getMetaData();
      int numberOfColumns = metaData.getColumnCount();
      Vector<String> columnNames = new Vector<>();
      // Get the column names
      for (int column = 0; column < numberOfColumns; column++) {
        columnNames.add(metaData.getColumnLabel(column + 1));
      }
      // Get all rows.
      Vector<Object> rows = new Vector<>();

      while (results.next()) {
        Vector<Object> newRow = new Vector<>();
        for (int i = 1; i <= numberOfColumns; i++) {
          newRow.add(results.getObject(i));
        }
        rows.add(newRow);
      }
      return new DefaultTableModel(rows, columnNames);
    } catch (Exception e) {
      throw new IllegalStateException("Something went wrong! " + e);
    }
  }

}
