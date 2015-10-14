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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

import com.stuart.tourny.controller.GameController;
import com.stuart.tourny.controller.PlayerController;
import com.stuart.tourny.controller.TournamentController;
import com.stuart.tourny.model.common.dto.DTOGame;
import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.utils.Constants;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

public class PlayGameDialog extends JDialog {

    private static final String INVALID_DATA = "Invalid data";
    private static final String PLEASE_DECIDE = "Please decide.";
    private static final Logger LOGGER = Logger.getLogger(PlayGameDialog.class);
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
    private JComboBox<String> cmbTournaments;

    public PlayGameDialog(Window parent) {
	super(parent, TITLE);
	getContentPane().setBackground(TournamentGUI.getBackgroundColour());
	initGUI();
	populateComponents();
	setActiveComponents(GameType.INITIAL);
    }

    /**
     * This will query the database to populate the different combo boxes from
     * the database.
     */
    private void populateComponents() {
	LOGGER.debug("Attempting to populate components required to " + TITLE);
	try {
	    List<String> teams = new GameController().getTeams();
	    List<String> tournaments = new TournamentController().getTournaments();
	    List<String> players = new PlayerController().getAllPlayers();
	    cmbHomeTeam.setModel(new DefaultComboBoxModel<>(teams.toArray(new String[teams.size()])));
	    cmbAwayTeam.setModel(new DefaultComboBoxModel<>(teams.toArray(new String[teams.size()])));
	    cmbHomePlayer.setModel(new DefaultComboBoxModel<>(players.toArray(new String[players.size()])));
	    cmbAwayPlayer.setModel(new DefaultComboBoxModel<>(players.toArray(new String[players.size()])));
	    cmbTournaments.setModel(new DefaultComboBoxModel<>(tournaments.toArray(new String[tournaments.size()])));
	} catch (ServerProblem sp) {
	    LOGGER.error(sp);
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), Constants.LOG_DETAILS,
		    "Error populating components", JOptionPane.ERROR_MESSAGE);
	    throw new IllegalStateException(sp);
	}
    }

    /**
     * Cancel the screen, giving the user the chance to go back if it has been
     * clicked in error.
     */
    private void btnCancelActionPerformed() {
	LOGGER.debug("Close clicked");
	int option = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this),
		"Do you wish to cancel this screen?" + System.lineSeparator() + "Unsaved changes will be lost!",
		PLEASE_DECIDE, JOptionPane.YES_NO_OPTION);
	if (JOptionPane.YES_OPTION == option) {
	    LOGGER.debug("User confirmed close");
	    dispose();
	} else {
	    LOGGER.debug("User cancelled close");
	}
    }

    /**
     * Save a game. Will convert all the screen input into a DTOGame record. If
     * the game is marked as the final, then it should update the tournament
     * with the winner and loser of the final, and calculate the golden boot.
     */
    private void btnSaveGameActionPerformed() {
	boolean isOk = validateGameInformation();
	if (isOk) {
	    try {
		TournamentController tournamentController = new TournamentController();
		DTOTournament dtoTournament = tournamentController
			.getDTOTournamentFromName((String) cmbTournaments.getSelectedItem());
		int homeGoalsValue = (int) homeGoals.getValue();
		int homePenaltyValue = (int) homePenalties.getValue();
		int totalHomeScore = homeGoalsValue + homePenaltyValue;

		int awayGoalsValue = (int) awayGoals.getValue();
		int awayPenaltyValue = (int) awayPenalties.getValue();
		int totalAwayScore = awayGoalsValue + awayPenaltyValue;

		DTOGame newGame = new DTOGame();
		newGame.setHomePlayer((String) cmbHomePlayer.getSelectedItem());
		newGame.setAwayPlayer((String) cmbAwayPlayer.getSelectedItem());
		newGame.setHomeGoals(homeGoalsValue);
		newGame.setAwayGoals(awayGoalsValue);
		newGame.setTournamentId(dtoTournament.getTournamentId());
		newGame.setKnockOut("N");

		newGame.setWinner(
			findWinner(totalHomeScore, totalAwayScore, newGame.getHomePlayer(), newGame.getAwayPlayer()));
		if ((rdbtnKnockOut.isSelected() || rdbtnFinal.isSelected())
			&& Constants.DRAW.equals(newGame.getWinner())) {

		    LOGGER.error("Knock out game cannot end in a draw");
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
			    "Knock out game cannot end in a draw! Please re-check the scores.", "Error Saving New Game",
			    JOptionPane.ERROR_MESSAGE);
		    return;

		}
		if (rdbtnKnockOut.isSelected()) {
		    newGame.setKnockOut("Y");
		    newGame.setHomePens(homePenaltyValue);
		    newGame.setAwayPens(awayPenaltyValue);
		}
		if (rdbtnFinal.isSelected()) {
		    newGame.setKnockOut("Y");
		    newGame.setHomePens(homePenaltyValue);
		    newGame.setAwayPens(awayPenaltyValue);
		    newGame.setHomeTeam((String) cmbHomeTeam.getSelectedItem());
		    newGame.setAwayTeam((String) cmbAwayTeam.getSelectedItem());

		}
		GameController gameController = new GameController();
		LOGGER.debug("Game to save:" + newGame);
		newGame = gameController.addGame(newGame);
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "New game added!", "Success!",
			JOptionPane.INFORMATION_MESSAGE);
		if (rdbtnFinal.isSelected()) {
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
			    "About to finalise the tournament...", "Congratulations to all players!",
			    JOptionPane.INFORMATION_MESSAGE);
		    Map<Long, String> goldenBoot = tournamentController
			    .calculateGoldenBootForTournament(dtoTournament.getTournamentId());
		    dtoTournament.setGoldenBootGoals(goldenBoot.keySet().iterator().next());
		    dtoTournament.setGoldenBoot(goldenBoot.get(dtoTournament.getGoldenBootGoals()));
		    dtoTournament.setTournamentWinner(newGame.getWinner());
		    String runnerUp = newGame.getWinner().equals(newGame.getHomePlayer()) ? newGame.getAwayPlayer()
			    : newGame.getHomePlayer();
		    dtoTournament.setWoodenSpoon(runnerUp);
		    dtoTournament = tournamentController.updateTournament(dtoTournament);
		    StringBuilder message = new StringBuilder();
		    message.append("Tournament has been Won:");
		    message.append(System.lineSeparator());
		    message.append("Winner: ").append(dtoTournament.getTournamentWinner());
		    message.append(System.lineSeparator());
		    message.append("Runner Up: ").append(dtoTournament.getWoodenSpoon());
		    message.append(System.lineSeparator());
		    message.append("Golden Boot: ").append(dtoTournament.getGoldenBoot());
		    message.append(System.lineSeparator());
		    message.append("Golden Boot Goals: ").append(dtoTournament.getGoldenBootGoals());
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), message,
			    "Congratulations to all players!", JOptionPane.INFORMATION_MESSAGE);
		    dispose();
		}
		resetAllComponents();
	    } catch (Exception ex) {
		LOGGER.error("Problem saving new game: ", ex);
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), Constants.LOG_DETAILS,
			"Error Saving New Game", JOptionPane.ERROR_MESSAGE);
	    }
	}

    }

    private static String findWinner(int totalHomeScore, int totalAwayScore, String homePlayer, String awayPlayer) {
	String winningPlayer;
	if (totalHomeScore > totalAwayScore) {
	    winningPlayer = homePlayer;
	} else if (totalAwayScore > totalHomeScore) {
	    winningPlayer = awayPlayer;
	} else {
	    winningPlayer = Constants.DRAW;
	}
	return winningPlayer;
    }

    /**
     * Check that:
     * <ul>
     * <li>A game does not have the same two players</li>
     * <li>Home/Away goals are populated</li>
     * <li>For knock out games, the penalties are populated</li>
     * <li>For the final, penalties and teams are populated and correct (could
     * be the same team for both players)</li>
     * </ul>
     *
     * @return true if user provided information is valid
     */
    private boolean validateGameInformation() {
	if (cmbHomePlayer.getSelectedItem().equals(cmbAwayPlayer.getSelectedItem())) {
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
		    "Players cannot play themselves, please choose different players.", INVALID_DATA,
		    JOptionPane.WARNING_MESSAGE);
	    return false;
	}
	if ((homeGoals.getValue() == null) || (awayGoals.getValue() == null)) {
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
		    "Games need both home and away scores populated.", INVALID_DATA, JOptionPane.WARNING_MESSAGE);
	    return false;
	}
	if (rdbtnKnockOut.isSelected() && ((homePenalties.getValue() == null) || (awayPenalties.getValue() == null))) {
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
		    "Knock Out games need penalties populated, even if they are zero.", INVALID_DATA,
			JOptionPane.WARNING_MESSAGE);
		return false;
	}
	if (rdbtnFinal.isSelected()) {
	    if ((homePenalties.getValue() == null) || (awayPenalties.getValue() == null)) {
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
			"The Final needs penalties populated, even if they are zero.", INVALID_DATA,
			JOptionPane.WARNING_MESSAGE);
		return false;
	    }
	    if (cmbHomeTeam.getSelectedItem().equals(cmbAwayTeam.getSelectedItem())) {
		int option = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this),
			"Both players have the same team selected!" + System.lineSeparator() + "Is this correct?",
			PLEASE_DECIDE, JOptionPane.YES_NO_OPTION);
		if (JOptionPane.YES_OPTION != option) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Reset all components back to a default state
     */
    private void btnResetActionPerformed() {
	int option = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this),
		"Do you wish to reset this screen?" + System.lineSeparator() + "Unsaved changes will be lost!",
		PLEASE_DECIDE, JOptionPane.YES_NO_OPTION);
	if (JOptionPane.YES_OPTION == option) {
	    LOGGER.debug("User confirmed reset");
	    resetAllComponents();
	} else {
	    LOGGER.debug("User cancelled reset");
	}
    }

    /**
     * Set all user editable components back to their initial state.
     */
    private void resetAllComponents() {
	buttonGroup.clearSelection();
	setActiveComponents(GameType.INITIAL);
	homeGoals.setValue(0);
	homePenalties.setValue(0);
	cmbHomeTeam.setSelectedIndex(0);
	cmbHomePlayer.setSelectedIndex(0);
	awayGoals.setValue(0);
	awayPenalties.setValue(0);
	cmbAwayTeam.setSelectedIndex(0);
	cmbAwayPlayer.setSelectedIndex(0);
    }

    /**
     * Set the required components for the final match.
     */
    private void rdbtnFinalActionPerformed() {
	setActiveComponents(GameType.FINAL);
    }

    /**
     * Set the required components for a knock out match.
     */
    private void rdbtnKnockOutActionPerformed() {
	setActiveComponents(GameType.KNOCK_OUT);
    }

    /**
     * Set the required components for a group match.
     */
    private void rdbtnGroup_actionPerformed() {
	setActiveComponents(GameType.GROUP);
    }

    /**
     * Set the required components active based on the type of game passed in.
     * In this context, the state of 'INITIAL' is also considered a game type,
     * and is used on first entry and on reset.
     *
     * @param type
     *            - The type of game we are playing
     */
    private void setActiveComponents(GameType type) {
	if (GameType.GROUP.equals(type)) {
	    homeGoals.setEnabled(true);
	    homePenalties.setEnabled(false);
	    cmbHomeTeam.setEnabled(false);
	    awayGoals.setEnabled(true);
	    awayPenalties.setEnabled(false);
	    cmbAwayTeam.setEnabled(false);

	    String toolTip = "Available to different game types.";
	    homeGoals.setToolTipText(null);
	    homePenalties.setToolTipText(toolTip);
	    cmbHomeTeam.setToolTipText(toolTip);
	    awayGoals.setToolTipText(null);
	    awayPenalties.setToolTipText(toolTip);
	    cmbAwayTeam.setToolTipText(toolTip);
	} else if (GameType.KNOCK_OUT.equals(type)) {
	    homeGoals.setEnabled(true);
	    homePenalties.setEnabled(true);
	    cmbHomeTeam.setEnabled(false);
	    awayGoals.setEnabled(true);
	    awayPenalties.setEnabled(true);
	    cmbAwayTeam.setEnabled(false);

	    String toolTip = "Available to different game types.";
	    homeGoals.setToolTipText(null);
	    homePenalties.setToolTipText(null);
	    cmbHomeTeam.setToolTipText(toolTip);
	    awayGoals.setToolTipText(null);
	    awayPenalties.setToolTipText(null);
	    cmbAwayTeam.setToolTipText(toolTip);
	} else if (GameType.FINAL.equals(type)) {
	    homeGoals.setEnabled(true);
	    homePenalties.setEnabled(true);
	    cmbHomeTeam.setEnabled(true);
	    awayGoals.setEnabled(true);
	    awayPenalties.setEnabled(true);
	    cmbAwayTeam.setEnabled(true);

	    homeGoals.setToolTipText(null);
	    homePenalties.setToolTipText(null);
	    cmbHomeTeam.setToolTipText(null);
	    awayGoals.setToolTipText(null);
	    awayPenalties.setToolTipText(null);
	    cmbAwayTeam.setToolTipText(null);
	} else if (GameType.INITIAL.equals(type)) {
	    homeGoals.setEnabled(false);
	    homeGoals.setValue(0);
	    homePenalties.setEnabled(false);
	    homePenalties.setValue(0);
	    cmbHomeTeam.setEnabled(false);
	    awayGoals.setEnabled(false);
	    awayGoals.setValue(0);
	    awayPenalties.setEnabled(false);
	    awayPenalties.setValue(0);
	    cmbAwayTeam.setEnabled(false);

	    String toolTip = "Please select a Game Type to continue.";
	    homeGoals.setToolTipText(toolTip);
	    homePenalties.setToolTipText(toolTip);
	    cmbHomeTeam.setToolTipText(toolTip);
	    awayGoals.setToolTipText(toolTip);
	    awayPenalties.setToolTipText(toolTip);
	    cmbAwayTeam.setToolTipText(toolTip);
	}
    }

    /**
     * Used to determine which components are enabled/disabled at any one time.
     */
    public enum GameType {
	GROUP, KNOCK_OUT, FINAL, INITIAL
    }

    /**
     * Setup the GUI components
     */
    private void initGUI() {
	LOGGER.debug("Creating " + TITLE);
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[] { 21, 0 };
	gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
	gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
	gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	getContentPane().setLayout(gridBagLayout);

	final JPanel titlePanel = new JPanel();
	titlePanel.setOpaque(false);
	getContentPane().add(titlePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
	GridBagLayout gblTitlePanel = new GridBagLayout();
	gblTitlePanel.columnWidths = new int[] { 0, 0, 0, 0 };
	gblTitlePanel.rowHeights = new int[] { 0, 0 };
	gblTitlePanel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
	gblTitlePanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	titlePanel.setLayout(gblTitlePanel);

	cmbHomePlayer = new JComboBox<>();
	titlePanel.add(cmbHomePlayer, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	final JLabel lblVs = new JLabel("VS.");
	lblVs.setFont(new Font("Tahoma", Font.BOLD, 24));
	titlePanel.add(lblVs, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

	cmbAwayPlayer = new JComboBox<>();
	titlePanel.add(cmbAwayPlayer, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	final JPanel holderPanel = new JPanel();
	holderPanel.setOpaque(false);
	getContentPane().add(holderPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));

	final JPanel rdoPanel = new JPanel();
	rdoPanel.setOpaque(false);
	rdoPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
	holderPanel.add(rdoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
	GridBagLayout gblRdoPanel = new GridBagLayout();
	gblRdoPanel.columnWidths = new int[] { 0, 0 };
	gblRdoPanel.rowHeights = new int[] { 0, 0, 0, 0 };
	gblRdoPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
	gblRdoPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
	rdoPanel.setLayout(gblRdoPanel);
	rdoPanel.setBorder(BorderFactory.createTitledBorder("Game Type"));

	rdbtnGroup = new JRadioButton("Group");
	rdbtnGroup.setOpaque(false);
	rdbtnGroup.addActionListener(e -> rdbtnGroup_actionPerformed());
	rdoPanel.add(rdbtnGroup, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
	buttonGroup.add(rdbtnGroup);

	rdbtnKnockOut = new JRadioButton("Knock Out");
	rdbtnKnockOut.setOpaque(false);
	rdbtnKnockOut.addActionListener(e -> rdbtnKnockOutActionPerformed());
	rdoPanel.add(rdbtnKnockOut, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
	buttonGroup.add(rdbtnKnockOut);

	rdbtnFinal = new JRadioButton("Final");
	rdbtnFinal.setOpaque(false);
	rdbtnFinal.addActionListener(e -> rdbtnFinalActionPerformed());
	rdoPanel.add(rdbtnFinal, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	buttonGroup.add(rdbtnFinal);

	final JPanel scoresPanel = new JPanel();
	scoresPanel.setOpaque(false);
	holderPanel.add(scoresPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));
	GridBagLayout gblScoresPanel = new GridBagLayout();
	gblScoresPanel.columnWidths = new int[] { 0, 0, 0, 0 };
	gblScoresPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
	gblScoresPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
	gblScoresPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	scoresPanel.setLayout(gblScoresPanel);

	final JLabel lblHomeTeam = new JLabel("Home Team");
	lblHomeTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
	scoresPanel.add(lblHomeTeam, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	final JLabel lblAwayTeam = new JLabel("Away Team");
	lblAwayTeam.setFont(new Font("Tahoma", Font.BOLD, 16));
	scoresPanel.add(lblAwayTeam, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	NumberFormat longFormat = NumberFormat.getIntegerInstance();
	NumberFormatter nf = new NumberFormatter(longFormat);
	nf.setValueClass(Integer.class);
	nf.setMinimum(0);
	nf.setMaximum(Integer.MAX_VALUE);
	nf.setCommitsOnValidEdit(true);
	nf.setAllowsInvalid(false);
	homeGoals = new JFormattedTextField(nf);

	scoresPanel.add(homeGoals, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	final JLabel lblGoals = new JLabel("Goals");
	scoresPanel.add(lblGoals, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

	awayGoals = new JFormattedTextField(nf);
	scoresPanel.add(awayGoals, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	homePenalties = new JFormattedTextField(nf);
	scoresPanel.add(homePenalties, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	final JLabel lblPenalties = new JLabel("Penalties (AET)");
	scoresPanel.add(lblPenalties, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

	awayPenalties = new JFormattedTextField(nf);
	scoresPanel.add(awayPenalties, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	cmbHomeTeam = new JComboBox<>();
	scoresPanel.add(cmbHomeTeam, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

	final JLabel lblTeam = new JLabel("Team");
	scoresPanel.add(lblTeam, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

	cmbAwayTeam = new JComboBox<>();
	scoresPanel.add(cmbAwayTeam, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	final JPanel tournamentPanel = new JPanel();
	tournamentPanel.setOpaque(false);
	getContentPane().add(tournamentPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
	GridBagLayout gblTournamentPanel = new GridBagLayout();
	gblTournamentPanel.columnWidths = new int[] { 0, 0, 0 };
	gblTournamentPanel.rowHeights = new int[] { 0, 0 };
	gblTournamentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
	gblTournamentPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	tournamentPanel.setLayout(gblTournamentPanel);

	final JLabel lblTournamentId = new JLabel("Tournament ID:");
	lblTournamentId.setFont(new Font("Tahoma", Font.BOLD, 16));
	tournamentPanel.add(lblTournamentId, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
		GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

	cmbTournaments = new JComboBox<>();
	tournamentPanel.add(cmbTournaments, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

	final JPanel controlPanel = new JPanel();
	controlPanel.setOpaque(false);
	getContentPane().add(controlPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
		GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	GridBagLayout gblControlPanel = new GridBagLayout();
	gblControlPanel.columnWidths = new int[] { 0, 0, 0, 0 };
	gblControlPanel.rowHeights = new int[] { 0, 0 };
	gblControlPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
	gblControlPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	controlPanel.setLayout(gblControlPanel);

	final JButton btnReset = new JButton("Reset");
	btnReset.addActionListener(arg0 -> btnResetActionPerformed());
	controlPanel.add(btnReset, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

	final JButton btnSaveGame = new JButton("Save Game");
	btnSaveGame.addActionListener(e -> btnSaveGameActionPerformed());
	controlPanel.add(btnSaveGame, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

	final JButton btnCancel = new JButton("Cancel");
	btnCancel.setToolTipText("Cancel and exit this screen without saving any information.");
	btnCancel.addActionListener(e -> btnCancelActionPerformed());
	controlPanel.add(btnCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    }
}
