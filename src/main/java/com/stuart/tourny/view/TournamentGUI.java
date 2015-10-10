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

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.stuart.tourny.controller.TournamentController;
import com.stuart.tourny.model.utils.Constants;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

/**
 * Create the Tournament GUI application.
 *
 * @author Stuart
 */
public class TournamentGUI extends JFrame {

    private static final Color BACKGROUND = new Color(0, 204, 51);
    private static final List<Image> ICON_IMAGES = loadIcons();

    private static final Logger log = Logger.getLogger(TournamentGUI.class);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	log.info("Tournament Database started");

	setupLoggers();
	EventQueue.invokeLater(() -> {
	    try {
		TournamentGUI frame = new TournamentGUI();
		frame.setTitle("Tournament Database");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
			log.debug("Application closed by WindowClosing event: " + e);
			System.exit(0);
		    }
		});
		frame.setSize(800, 400);
		frame.setResizable(false);
		frame.setIconImages(ICON_IMAGES);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	    } catch (Exception ex) {
		log.error(ex);
	    }
	});
    }

    /**
     * Get the standard background window colour.
     *
     * @return The standard background colour.
     */
    public static final Color getBackgroundColour() {
	return BACKGROUND;
    }

    /**
     * Get the icon images for the GUI, contains 3 different sizes.
     *
     * @return The list of icon images
     */
    public static final List<Image> getIcons() {
	return ICON_IMAGES;
    }

    /**
     * Setup and configure the loggers.
     */
    private static void setupLoggers() {
	// creates pattern layout
	PatternLayout layout = new PatternLayout();
	String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
	layout.setConversionPattern(conversionPattern);

	// creates console appender
	ConsoleAppender consoleAppender = new ConsoleAppender();
	consoleAppender.setLayout(layout);
	consoleAppender.activateOptions();

	// creates file appender
	FileAppender fileAppender = new FileAppender();
	fileAppender.setFile("tourny_log.txt");
	fileAppender.setLayout(layout);
	fileAppender.activateOptions();

	// configures the root logger
	Logger rootLogger = Logger.getRootLogger();
	rootLogger.setLevel(Level.DEBUG);
	rootLogger.addAppender(consoleAppender);
	rootLogger.addAppender(fileAppender);

	// Configure the logging for C3P0
	Logger.getLogger("com.mchange.v2.c3p0").setLevel(Level.WARN);
	Logger.getLogger("com.mchange.v2").setLevel(Level.WARN);

	Properties p = new Properties(System.getProperties());
	p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.log4j.Log4jMLog");
	p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "INFO");
	System.setProperties(p);
    }

    /**
     * Create the frame.
     */
    public TournamentGUI() {
	initGUI();
    }

    /**
     * Tidy up and close things down when close is clicked.
     */
    private void btnCloseApp_actionPerformed() {
	log.debug("Application closed by the 'Close App' button");
	this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Create a new instance of the Manage Games dialog and display it.
     */
    private void btnManageGames_actionPerformed() {
	ManageGamesDialog mgd = new ManageGamesDialog(SwingUtilities.windowForComponent(this));
	mgd.getDialog().setBackground(BACKGROUND);
	mgd.getDialog().setIconImages(ICON_IMAGES);
	mgd.getDialog().setLocationRelativeTo(null);
	mgd.getDialog().pack();
	mgd.getDialog().setVisible(true);
    }

    /**
     * Create a new instance of the Manage Tournaments Dialog and display it.
     */
    private void btnManageTournaments_actionPerformed() {
	ManageTournamentsDialog mtd = new ManageTournamentsDialog(SwingUtilities.windowForComponent(this));
	mtd.getDialog().setBackground(BACKGROUND);
	mtd.getDialog().setIconImages(ICON_IMAGES);
	mtd.getDialog().setLocationRelativeTo(null);
	mtd.getDialog().pack();
	mtd.getDialog().setVisible(true);
    }

    /**
     * Create a new instance of the Manage Players Dialog and display it.
     */
    private void btnManagePlayers_actionPerformed() {
	ManagePlayersDialog mpd = new ManagePlayersDialog(SwingUtilities.windowForComponent(this));
	mpd.getDialog().setBackground(BACKGROUND);
	mpd.getDialog().setIconImages(ICON_IMAGES);
	mpd.getDialog().setLocationRelativeTo(null);
	mpd.getDialog().pack();
	mpd.getDialog().setVisible(true);
    }

    /**
     * Create a new instance of the Play Game Dialog and display it.
     */
    private void btnPlayGame_actionPerformed() {
	try {
	    TournamentController tournamentController = new TournamentController();
	    List<String> tournaments = tournamentController.getTournaments();
	    if (tournaments.isEmpty()) {
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
			"Please create a tournament before trying to Play a Game!", "Error, no Tournaments exist!",
			JOptionPane.ERROR_MESSAGE);
		return;
	    }
	} catch (ServerProblem sp) {
	    log.error(sp);
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), Constants.LOG_DETAILS,
		    "Error populating components", JOptionPane.ERROR_MESSAGE);
	    throw new IllegalStateException(sp);
	}
	PlayGameDialog pgd = new PlayGameDialog(SwingUtilities.windowForComponent(this));
	pgd.setBackground(TournamentGUI.BACKGROUND);
	pgd.setIconImages(TournamentGUI.ICON_IMAGES);
	pgd.setLocationRelativeTo(null);
	pgd.setResizable(false);
	pgd.pack();
	pgd.setVisible(true);
    }

    /**
     * Static method to load the image icons and store them within the parent
     * frame.
     *
     * @return List of images to use as icons
     */
    private static List<Image> loadIcons() {
	ImageIcon smallIcon = new ImageIcon(TournamentGUI.class.getResource("/images/worldcup-icon-small.png"));
	ImageIcon medIcon = new ImageIcon(TournamentGUI.class.getResource("/images/worldcup-icon-med.png"));
	ImageIcon largeIcon = new ImageIcon(TournamentGUI.class.getResource("/images/worldcup-icon-lge.png"));

	List<Image> icons = new ArrayList<>(3);
	icons.add(smallIcon.getImage());
	icons.add(medIcon.getImage());
	icons.add(largeIcon.getImage());
	return icons;
    }

    /**
     * Setup the GUI
     */
    private void initGUI() {
	JPanel parentPane = new JPanel();
	parentPane.setBackground(BACKGROUND);
	parentPane.setForeground(BACKGROUND);
	parentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	setContentPane(parentPane);
	GridBagLayout gbl_parentPane = new GridBagLayout();
	gbl_parentPane.columnWidths = new int[] { 143, 0, 0 };
	gbl_parentPane.rowHeights = new int[] { 258, 0 };
	gbl_parentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
	gbl_parentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
	parentPane.setLayout(gbl_parentPane);

	JPanel buttonPanel = new JPanel();
	buttonPanel.setBackground(BACKGROUND);
	buttonPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	parentPane.add(buttonPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		GridBagConstraints.VERTICAL, new Insets(0, 0, 5, 0), 0, 0));
	GridBagLayout gbl_buttonPanel = new GridBagLayout();
	gbl_buttonPanel.columnWidths = new int[] { 111, 0 };
	gbl_buttonPanel.rowHeights = new int[] { 23, 23, 0, 0, 0, 0, 0, 0, 0 };
	gbl_buttonPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
	gbl_buttonPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	buttonPanel.setLayout(gbl_buttonPanel);

	JButton btnManagePlayers = new JButton("Manage Players");
	btnManagePlayers.setToolTipText("Add/View Players");
	btnManagePlayers.setHorizontalAlignment(SwingConstants.CENTER);
	buttonPanel.add(btnManagePlayers, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));

	JButton btnManageGames = new JButton("Manage Games");
	btnManageGames.setToolTipText("View Games and related queries");
	btnManageGames.setHorizontalAlignment(SwingConstants.CENTER);
	buttonPanel.add(btnManageGames, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));

	JButton btnManageTournaments = new JButton("Manage Tournaments");
	btnManageTournaments.setToolTipText("View Tournaments and related queries");
	btnManageTournaments.addActionListener(e -> btnManageTournaments_actionPerformed());
	buttonPanel.add(btnManageTournaments, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));

	JButton btnCloseApp = new JButton("Close App");
	btnCloseApp.setToolTipText("Exit the Application\r\n");
	btnCloseApp.addActionListener(e -> btnCloseApp_actionPerformed());

	JButton btnPlayGame = new JButton("Play a Game");
	btnPlayGame.setToolTipText("Play a game, requires a Tournament to exist.");
	btnPlayGame.addActionListener(e -> btnPlayGame_actionPerformed());
	buttonPanel.add(btnPlayGame, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));

	JPanel spacerPanel = new JPanel();
	spacerPanel.setBackground(BACKGROUND);
	spacerPanel.setLayout(new GridBagLayout());
	buttonPanel.add(spacerPanel, new GridBagConstraints(0, 4, 1, 3, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

	buttonPanel.add(btnCloseApp, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

	JPanel welcomeImgPanel = new JPanel();
	welcomeImgPanel.setBackground(BACKGROUND);
	parentPane.add(welcomeImgPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	JLabel lblWelcomeImage = new JLabel("");
	lblWelcomeImage.setBackground(BACKGROUND);
	lblWelcomeImage.setIcon(new ImageIcon(TournamentGUI.class.getResource("/images/worldCup_open.jpg")));
	welcomeImgPanel.add(lblWelcomeImage);

	btnManagePlayers.addActionListener(arg0 -> btnManagePlayers_actionPerformed());

	btnManageGames.addActionListener(e -> btnManageGames_actionPerformed());
    }
}
