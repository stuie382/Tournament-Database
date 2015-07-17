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
   * Setup the GUI
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
