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

import com.stuart.tourny.controller.QueryController;

import org.apache.log4j.Logger;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ManageGamesDialog extends ManageDialog {

  private static final Logger log = Logger.getLogger(ManageGamesDialog.class);
  private static final String MANAGE_GAMES = "Manage Games";
  private static final String GAME = "Game";
  private static final String ERROR_TITLE = "Error getting all games from the database.";

  private ResultSetTablePanel resultSetTablePanel;

  /**
   * Create an instance of the ManageGamesDialog.
   *
   * @param parent
   *     - Reference to the parent who owns this Dialog.
   */
  public ManageGamesDialog(Window parent) {
    super(parent, MANAGE_GAMES);
    getContentPane().setBackground(TournamentGUI.BACKGROUND);
    initGUI();
  }

  @Override
  protected void btnAdd_actionPerformed() {
    PlayGameDialog
        pgd =
        new PlayGameDialog(SwingUtilities.windowForComponent(this));
    pgd.setBackground(TournamentGUI.BACKGROUND);
    pgd.setIconImages(TournamentGUI.ICON_IMAGES);
    pgd.setLocationRelativeTo(null);
    pgd.pack();
    pgd.setVisible(true);
  }

  @Override
  protected void btnViewAll_actionPerformed() {
    try {
      QueryController query = new QueryController();
      ResultSet rs = query.manageGames_viewAll();
      resultSetTablePanel.populateData(rs);
    } catch (Exception ex) {
      String errorMessage = "Error encountered getting all Games.." + System.lineSeparator()
                            + "See the log for details.";
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
                                    errorMessage,
                                    ERROR_TITLE,
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Setup the GUI. Most of the GUI is handled by the abstract parent class, so we can just handle
   * the games specific stuff here.
   */
  private void initGUI() {
    initGUI(MANAGE_GAMES, GAME);
    resultSetTablePanel = new ResultSetTablePanel();
    getContentPane().add(resultSetTablePanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                                                     GridBagConstraints.CENTER,
                                                                     GridBagConstraints.BOTH,
                                                                     new Insets(0, 0, 0, 0), 0, 0));
  }

}
