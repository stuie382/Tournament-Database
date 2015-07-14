package com.stuart.tourny.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;

public class ManageGamesDialog extends ManageDialog {

  private static final String MANAGE_GAMES = "Manage Games";
  private static final String GAME = "Game";

  private ResultSetTablePanel resultSetTablePanel;

  public ManageGamesDialog(Window parent) {
    super(parent, MANAGE_GAMES);
    getContentPane().setBackground(TournamentGUI.BACKGROUND);
    initGUI();
  }

  @Override
  protected void btnAdd_actionPerformed() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void btnViewAll_actionPerformed() {
    // TODO Auto-generated method stub

  }

  protected void initGUI() {
    initGUI(MANAGE_GAMES, GAME);
    resultSetTablePanel = new ResultSetTablePanel();
    getContentPane().add(resultSetTablePanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                                                     GridBagConstraints.CENTER,
                                                                     GridBagConstraints.BOTH,
                                                                     new Insets(0, 0, 0, 0), 0, 0));
  }

}
