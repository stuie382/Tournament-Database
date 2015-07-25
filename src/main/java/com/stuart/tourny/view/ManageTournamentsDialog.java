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

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;

public class ManageTournamentsDialog extends ManageDialog {

  private static final String MANAGE_TOURNAMENTS = "Manage Tournaments";
  private static final String TOURNAMENT = "Tournament";

  public ManageTournamentsDialog(Window parent) {
    super(parent, MANAGE_TOURNAMENTS);
    getContentPane().setBackground(TournamentGUI.BACKGROUND);
    initGUI();
  }

  @Override
  protected void btnViewAll_actionPerformed() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void btnAdd_actionPerformed() {
    // TODO Auto-generated method stub

  }

  /**
   * Setup the GUI. Most of the GUI is handled by the abstract parent class, so we can just handle
   * the tournament specific stuff here.
   */
  private void initGUI() {
    initGUI(MANAGE_TOURNAMENTS, TOURNAMENT);
    ResultSetTablePanel resultSetTablePanel = new ResultSetTablePanel();
    getContentPane().add(resultSetTablePanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                                                     GridBagConstraints.CENTER,
                                                                     GridBagConstraints.BOTH,
                                                                     new Insets(0, 0, 0, 0), 0, 0));
  }

}
