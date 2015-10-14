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

import com.stuart.tourny.controller.QueryController;
import com.stuart.tourny.controller.TournamentController;
import com.stuart.tourny.model.common.dto.DTOTournament;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

public class ManageTournamentsDialog extends ManageDialog {

    private static final Logger LOGGER = Logger.getLogger(ManageTournamentsDialog.class);
    private static final String MANAGE_TOURNAMENTS = "Manage Tournaments";
    private static final String TOURNAMENT = "Tournament";

    private ResultSetTablePanel resultSetTablePanel;
    private JDialog manageDialog;

    public ManageTournamentsDialog(Window parent) {
	super(parent, MANAGE_TOURNAMENTS);
	manageDialog = this.getDialog();
	manageDialog.getContentPane().setBackground(TournamentGUI.getBackgroundColour());
	initGUI();
    }

    @Override
    protected void btnViewAllActionPerformed() {
	try {
	    QueryController query = new QueryController();
	    ResultSet rs = query.manageTournamentsViewAll();
	    resultSetTablePanel.populateData(rs);
	} catch (Exception ex) {
	    String errorMessage = "Error encountered viewing all tournaments." + System.lineSeparator()
		    + "See the log for details.";
	    LOGGER.error("Viewing all tournaments: ", ex);
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog), errorMessage,
		    "Error getting all Tournaments from the database", JOptionPane.ERROR_MESSAGE);
	}

    }

    @Override
    protected void btnAddActionPerformed() {
	String userInput = JOptionPane.showInputDialog(manageDialog,
		"Please enter the full name of the new tournament." + System.lineSeparator()
			+ "Be careful, as once created it cannot be changed!",
		"Add a new Tournament.", JOptionPane.INFORMATION_MESSAGE);
	if (userInput != null) {
	    // OK so the user has entered something. Need to make sure it is
	    // valid.
	    if (isUserInputValid(userInput)) {
		try {
		    TournamentController controller = new TournamentController();
		    DTOTournament dto = new DTOTournament();
		    dto.setTournamentName(userInput);
		    dto = controller.addTournament(dto);
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog),
			    "New Tournament added - " + dto.getTournamentName(), "Success!",
			    JOptionPane.INFORMATION_MESSAGE);
		} catch (ServerProblem sp) {
		    String errorMessage = "Error encountered adding a new Tournament." + System.lineSeparator()
			    + "Please see the log for details.";
		    LOGGER.error("Adding new tournament: ", sp);
		    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog), errorMessage,
			    "Error adding new Tournament to the database.", JOptionPane.ERROR_MESSAGE);
		}
	    } else {
		LOGGER.info("Tried to add invalid Tournament name: " + userInput);
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog),
			"Please use alphanumeric characters only for Tournament names!", "Oops",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}

    }

    /**
     * Setup the GUI. Most of the GUI is handled by the abstract parent class,
     * so we can just handle the tournament specific stuff here.
     */
    private void initGUI() {
	initGUI(MANAGE_TOURNAMENTS, TOURNAMENT);
	resultSetTablePanel = new ResultSetTablePanel();
	manageDialog.getContentPane().add(resultSetTablePanel.getPanel(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

}
