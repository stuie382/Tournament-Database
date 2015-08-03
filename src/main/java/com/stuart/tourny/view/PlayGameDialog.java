/*
 *
 *  Copyright (c) Stuart Clark 2015
 *
 *  This project by Stuart Clark is free software: you can redistribute it and/or modify it under the terms
 *  of the GNU General Public License as published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version. This project is distributed in the hope that it will be
 *  useful for educational purposes, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this project.
 *  If not, please see the GNU website.
 *
 */
package com.stuart.tourny.view;

import com.stuart.tourny.controller.GameController;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import org.apache.log4j.Logger;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class PlayGameDialog extends JDialog {

  private static final Logger log = Logger.getLogger(PlayGameDialog.class);
  private static final String TITLE = "Play a Game";

  private final ButtonGroup buttonGroup = new ButtonGroup();
  private JComboBox<String> cmbHomePlayer;
  private JComboBox<String> cmbAwayPlayer;
  private JRadioButton rdbtnGroup;
  private JRadioButton rdbtnKnockOut;
  private JRadioButton rdbtnFinal;
  private JFormattedTextField homeGoals;
  private JFormattedTextField awayGoals;
  private JFormattedTextField homePenalties;
  private JFormattedTextField awayPenalties;
  private JComboBox<String> cmbHomeTeam;
  private JComboBox<String> cmbAwayTeam;
  private JComboBox<String> cmbTournamentId;

  public PlayGameDialog(Window parent) {
    super(parent, TITLE);
    getContentPane().setBackground(TournamentGUI.BACKGROUND);
    initGUI();
    populateComponents();
  }

  private void populateComponents() {
    log.debug("Attempting to populate components required to " + TITLE);
    GameController gameController = new GameController();
    try {
      List<String> teams = gameController.getTeams();
      cmbHomeTeam.setModel(new DefaultComboBoxModel(teams.toArray()));
      cmbAwayTeam.setModel(new DefaultComboBoxModel(teams.toArray()));
    } catch (ServerProblem sp) {
      log.error(sp);
    }


  }

  private void btnCancel_actionPerformed() {
    dispose();
  }

  private void btnSaveGame_actionPerformed() {

  }

  private void btnReset_actionPerformed() {

  }

  private void rdbtnFinal_actionPerformed() {

  }

  private void rdbtnKnockOut_actionPerformed() {

  }

  private void rdbtnGroup_actionPerformed() {

  }

  private void initGUI() {
    log.debug("Creating " + TITLE);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{21, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
    gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    getContentPane().setLayout(gridBagLayout);

    final JPanel titlePanel = new JPanel();
    titlePanel.setOpaque(false);
    getContentPane()
        .add(titlePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 5, 0), 0, 0));
    GridBagLayout gbl_titlePanel = new GridBagLayout();
    gbl_titlePanel.columnWidths = new int[]{0, 0, 0, 0};
    gbl_titlePanel.rowHeights = new int[]{0, 0};
    gbl_titlePanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
    gbl_titlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    titlePanel.setLayout(gbl_titlePanel);

    cmbHomePlayer = new JComboBox<>();
    titlePanel
        .add(cmbHomePlayer, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 0, 5, 5), 0, 0));

    final JLabel lblVs = new JLabel("VS.");
    lblVs.setFont(new Font("Tahoma", Font.BOLD, 24));
    titlePanel.add(lblVs, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                                                 GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0,
                                                 0));

    cmbAwayPlayer = new JComboBox<>();
    titlePanel
        .add(cmbAwayPlayer, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 0, 5, 5), 0, 0));

    final JPanel holderPanel = new JPanel();
    holderPanel.setOpaque(false);
    getContentPane()
        .add(holderPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 5, 0), 0, 0));

    final JPanel rdoPanel = new JPanel();
    rdoPanel.setOpaque(false);
    rdoPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
    holderPanel.add(rdoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                     GridBagConstraints.NONE,
                                                     new Insets(0, 0, 5, 5), 0, 0));
    GridBagLayout gbl_rdoPanel = new GridBagLayout();
    gbl_rdoPanel.columnWidths = new int[]{0, 0};
    gbl_rdoPanel.rowHeights = new int[]{0, 0, 0, 0};
    gbl_rdoPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
    gbl_rdoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
    rdoPanel.setLayout(gbl_rdoPanel);
    rdoPanel.setBorder(BorderFactory.createTitledBorder("Game Type"));

    rdbtnGroup = new JRadioButton("Group");
    rdbtnGroup.setOpaque(false);
    rdbtnGroup.addActionListener(e -> rdbtnGroup_actionPerformed());
    rdoPanel.add(rdbtnGroup, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE, new Insets(0, 0, 5, 0),
                                                    0, 0));
    buttonGroup.add(rdbtnGroup);

    rdbtnKnockOut = new JRadioButton("Knock Out");
    rdbtnKnockOut.setOpaque(false);
    rdbtnKnockOut.addActionListener(e -> rdbtnKnockOut_actionPerformed());
    rdoPanel
        .add(rdbtnKnockOut, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                   GridBagConstraints.NONE, new Insets(0, 0, 5, 0),
                                                   0, 0));
    buttonGroup.add(rdbtnKnockOut);

    rdbtnFinal = new JRadioButton("Final");
    rdbtnFinal.setOpaque(false);
    rdbtnFinal.addActionListener(e -> rdbtnFinal_actionPerformed());
    rdoPanel.add(rdbtnFinal, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0),
                                                    0, 0));
    buttonGroup.add(rdbtnFinal);

    final JPanel scoresPanel = new JPanel();
    scoresPanel.setOpaque(false);
    holderPanel
        .add(scoresPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
                                                 0));
    GridBagLayout gbl_scoresPanel = new GridBagLayout();
    gbl_scoresPanel.columnWidths = new int[]{0, 0, 0, 0};
    gbl_scoresPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
    gbl_scoresPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
    gbl_scoresPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    scoresPanel.setLayout(gbl_scoresPanel);

    final JLabel lblHomeTeam = new JLabel("Home Team");
    lblHomeTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
    scoresPanel
        .add(lblHomeTeam, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 5, 5), 0, 0));

    final JLabel lblAwayTeam = new JLabel("Away Team");
    lblAwayTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
    scoresPanel
        .add(lblAwayTeam, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 5, 5), 0, 0));


        homeGoals =
        new JFormattedTextField(NumberFormat.getIntegerInstance());
    scoresPanel
        .add(homeGoals, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 0, 5, 5), 0, 0));

    final JLabel lblGoals = new JLabel("Goals");
    scoresPanel
        .add(lblGoals, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0,
                                              0));

    awayGoals = new JFormattedTextField();
    scoresPanel
        .add(awayGoals, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 0, 5, 5), 0, 0));


        homePenalties =
        new JFormattedTextField(NumberFormat.getIntegerInstance());
    scoresPanel
        .add(homePenalties, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 0, 5, 5), 0, 0));

    final JLabel lblPenalties = new JLabel("Penalties (AET)");
    scoresPanel
        .add(lblPenalties, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE, new Insets(0, 0, 5, 5),
                                                  0, 0));

     awayPenalties = new JFormattedTextField();
    scoresPanel
        .add(awayPenalties, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 0, 5, 5), 0, 0));

     cmbHomeTeam = new JComboBox<>();
    scoresPanel
        .add(cmbHomeTeam, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 0, 5), 0, 0));

    final JLabel lblTeam = new JLabel("Team");
    scoresPanel.add(lblTeam, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                    GridBagConstraints.NONE, new Insets(0, 0, 0, 5),
                                                    0, 0));

     cmbAwayTeam = new JComboBox<>();
    scoresPanel
        .add(cmbAwayTeam, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 5, 5), 0, 0));

    final JPanel tournamentPanel = new JPanel();
    tournamentPanel.setOpaque(false);
    getContentPane().add(tournamentPanel,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 5, 0), 0, 0));
    GridBagLayout gbl_tournamentPanel = new GridBagLayout();
    gbl_tournamentPanel.columnWidths = new int[]{0, 0, 0};
    gbl_tournamentPanel.rowHeights = new int[]{0, 0};
    gbl_tournamentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
    gbl_tournamentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    tournamentPanel.setLayout(gbl_tournamentPanel);

    final JLabel lblTournamentId = new JLabel("Tournament ID:");
    lblTournamentId.setFont(new Font("Tahoma", Font.BOLD, 16));
    tournamentPanel
        .add(lblTournamentId, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                                                     GridBagConstraints.NONE,
                                                     new Insets(0, 0, 0, 5), 0, 0));

    cmbTournamentId = new JComboBox<>();
    tournamentPanel
        .add(cmbTournamentId, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                     GridBagConstraints.NONE,
                                                     new Insets(0, 0, 0, 0), 0, 0));

    final JPanel controlPanel = new JPanel();
    controlPanel.setOpaque(false);
    getContentPane().add(controlPanel,
                         new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                                                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
                                                0));
    GridBagLayout gbl_controlPanel = new GridBagLayout();
    gbl_controlPanel.columnWidths = new int[]{0, 0, 0, 0};
    gbl_controlPanel.rowHeights = new int[]{0, 0};
    gbl_controlPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
    gbl_controlPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    controlPanel.setLayout(gbl_controlPanel);

    final JButton btnReset = new JButton("Reset");
    btnReset.addActionListener(arg0 -> btnReset_actionPerformed());
    controlPanel
        .add(btnReset, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0,
                                              0));

    final JButton btnSaveGame = new JButton("Save Game");
    btnSaveGame.addActionListener(e -> btnSaveGame_actionPerformed());
    controlPanel
        .add(btnSaveGame, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0,
                                                 0));

    final JButton btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(e -> btnCancel_actionPerformed());
    controlPanel
        .add(btnCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0,
                                               0));
  }

}
