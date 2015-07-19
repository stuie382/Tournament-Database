package com.stuart.tourny.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;

public class ManageGamesDialog extends ManageDialog {

  private static final String MANAGE_GAMES = "Manage Games";
  private static final String GAME = "Game";

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
    // TODO Auto-generated method stub

  }

  @Override
  protected void btnViewAll_actionPerformed() {
    // TODO Auto-generated method stub

  }

  /**
   * Setup the GUI. Most of the GUI is handled by the abstract parent class, so we can just handle
   * the games specific stuff here.
   */
  private void initGUI() {
    initGUI(MANAGE_GAMES, GAME);
    ResultSetTablePanel resultSetTablePanel = new ResultSetTablePanel();
    getContentPane().add(resultSetTablePanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                                                     GridBagConstraints.CENTER,
                                                                     GridBagConstraints.BOTH,
                                                                     new Insets(0, 0, 0, 0), 0, 0));
  }

}
