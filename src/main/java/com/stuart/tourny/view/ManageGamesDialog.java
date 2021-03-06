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

public class ManageGamesDialog extends ManageDialog {

    private static final Logger LOGGER = Logger.getLogger(ManageGamesDialog.class);
    private static final String MANAGE_GAMES = "Manage Games";
    private static final String GAME = "Game";
    private static final String ERROR_TITLE = "Error getting all games from the database.";

    private ResultSetTablePanel resultSetTablePanel;
    private JDialog manageDialog;

    /**
     * Create an instance of the ManageGamesDialog.
     *
     * @param parent
     *            - Reference to the parent who owns this Dialog.
     */
    public ManageGamesDialog(Window parent) {
	super(parent, MANAGE_GAMES);
	manageDialog = this.getDialog();
	manageDialog.getContentPane().setBackground(TournamentGUI.getBackgroundColour());
	initGUI();
    }

    @Override
    protected void btnAddActionPerformed() {
	// Method required by interface but non-functional in this context.
    }

    @Override
    protected void btnViewAllActionPerformed() {
	try {
	    QueryController query = new QueryController();
	    ResultSet rs = query.manageGamesViewAll();
	    resultSetTablePanel.populateData(rs);
	} catch (Exception ex) {
	    String errorMessage = "Error encountered getting all Games.." + System.lineSeparator()
		    + "See the log for details.";
	    LOGGER.error("Getting all games:", ex);
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(manageDialog), errorMessage, ERROR_TITLE,
		    JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * Setup the GUI. Most of the GUI is handled by the abstract parent class,
     * so we can just handle the games specific stuff here.
     */
    private void initGUI() {
	initGUI(MANAGE_GAMES, GAME);
	resultSetTablePanel = new ResultSetTablePanel();
	manageDialog.getContentPane().add(resultSetTablePanel.getPanel(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

}
