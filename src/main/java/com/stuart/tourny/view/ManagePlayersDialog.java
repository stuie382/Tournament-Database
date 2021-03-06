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
import java.sql.ResultSet;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.stuart.tourny.controller.PlayerController;
import com.stuart.tourny.controller.QueryController;
import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

public class ManagePlayersDialog extends ManageDialog {

    private static final Logger LOGGER = Logger.getLogger(ManagePlayersDialog.class);
    private static final String MANAGE_PLAYERS = "Manage Players";
    private static final String PLAYER = "Player";
    private static final String ERROR_TITLE = "Error getting all players from the database.";

    private ResultSetTablePanel resultSetTablePanel;
    private JDialog manageDialog;

    public ManagePlayersDialog(Window parent) {
	super(parent, MANAGE_PLAYERS);
	manageDialog = this.getDialog();
	manageDialog.getContentPane().setBackground(TournamentGUI.getBackgroundColour());
	initGUI();
    }

    @Override
    protected void btnViewAllActionPerformed() {
	try {
	    QueryController query = new QueryController();
	    ResultSet rs = query.managePlayersViewAll();
	    resultSetTablePanel.populateData(rs);
	} catch (Exception ex) {
	    String errorMessage = "Error encountered viewing all players." + System.lineSeparator()
		    + "See the log for details.";
	    LOGGER.error("Viewing all players: ", ex);
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog), errorMessage, ERROR_TITLE,
		    JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * Get the input from the user, so we can add a new player to the database.
     */
    @Override
    protected void btnAddActionPerformed() {
	String userInput = JOptionPane.showInputDialog(manageDialog,
		"Please enter the full name of the new player." + System.lineSeparator()
			+ "Be careful, as once created it cannot be changed!",
		"Add a new player.", JOptionPane.INFORMATION_MESSAGE);
	if (userInput != null) {
	    // OK so the user has entered something. Need to make sure it is
	    // valid.
	    if (isUserInputValid(userInput)) {
		try {
		    PlayerController controller = new PlayerController();
		    DTOPlayer dto = new DTOPlayer();
		    dto.setName(userInput);
		    dto = controller.addPlayer(dto);
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog),
			    "New player added - " + dto.getName(), "Success!", JOptionPane.INFORMATION_MESSAGE);
		} catch (ServerProblem sp) {
		    String errorMessage = "Error encountered adding a new player." + System.lineSeparator()
			    + "Please see the log for details.";
		    LOGGER.error("Adding new player: ", sp);
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog), errorMessage,
			    "Error adding new player to the database.", JOptionPane.ERROR_MESSAGE);
		}
	    } else {
		LOGGER.info("Tried to add invalid player name: " + userInput);
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog),
			"Please use alphanumeric characters only for player names!", "Oops",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}
    }

    /**
     * Setup the GUI. Most of the GUI is handled by the abstract parent class,
     * so we can just handle the player specific stuff here.
     */
    private void initGUI() {
	initGUI(MANAGE_PLAYERS, PLAYER);
	resultSetTablePanel = new ResultSetTablePanel();
	manageDialog.getContentPane().add(resultSetTablePanel.getPanel(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }
}
