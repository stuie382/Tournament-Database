package com.stuart.tourny.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

/**
 * @author Stuart
 */
public class TournamentGUI extends JFrame {

  public static final Color BACKGROUND = new Color(0, 204, 51);
  public static final List<Image> ICON_IMAGES = getIcons();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          TournamentGUI frame = new TournamentGUI();
          frame.setTitle("Tournament Database");
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.setSize(800, 400);
          frame.setResizable(false);
          frame.setIconImages(ICON_IMAGES);
          frame.setVisible(true);
          frame.setLocationRelativeTo(null);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
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
    this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }

  /**
   * Create a new instance of the Manage Games dialog and display it.
   */
  private void btnManageGames_actionPerfomed() {
    ManageGamesDialog mgd = new ManageGamesDialog(SwingUtilities.windowForComponent(this));
    mgd.setBackground(BACKGROUND);
    mgd.setIconImages(ICON_IMAGES);
    mgd.setLocationRelativeTo(null);
    mgd.setVisible(true);
  }

  /**
   * Create a new instance of the Manage Tournaments disalog and display it.
   */
  private void btnManageTournaments_actionPerformed() {
    ManageTournamentsDialog
        mtd =
        new ManageTournamentsDialog(SwingUtilities.windowForComponent(this));
    mtd.setBackground(BACKGROUND);
    mtd.setIconImages(ICON_IMAGES);
    mtd.setLocationRelativeTo(null);
    mtd.setVisible(true);
  }

  /**
   * Create a new instance of the Manage Players dialog and display it.
   */
  private void btnManagePlayers_actionPerformed() {
    ManagePlayersDialog mpd = new ManagePlayersDialog(SwingUtilities.windowForComponent(this));
    mpd.setBackground(BACKGROUND);
    mpd.setIconImages(ICON_IMAGES);
    mpd.setLocationRelativeTo(null);
    mpd.setVisible(true);
  }

  /**
   * Static method to load the image icons and store them within the parent frame.
   *
   * @return List of images to use as icons
   */
  private static List<Image> getIcons() {
    ImageIcon smallIcon = new ImageIcon(
        TournamentGUI.class.getResource("/images/worldcup-icon-small.png"));
    ImageIcon medIcon = new ImageIcon(
        TournamentGUI.class.getResource("/images/worldcup-icon-med.png"));
    System.out.print(TournamentGUI.class.getResource("/"));
    ImageIcon largeIcon = new ImageIcon(
        TournamentGUI.class.getResource("/images/worldcup-icon-lge.png"));

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
    gbl_parentPane.columnWidths = new int[]{143, 0, 0};
    gbl_parentPane.rowHeights = new int[]{258, 0};
    gbl_parentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
    gbl_parentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    parentPane.setLayout(gbl_parentPane);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(BACKGROUND);
    buttonPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
    parentPane
        .add(buttonPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                 GridBagConstraints.VERTICAL,
                                                 new Insets(0, 0, 5, 0), 0, 0));
    GridBagLayout gbl_buttonPanel = new GridBagLayout();
    gbl_buttonPanel.columnWidths = new int[]{111, 0};
    gbl_buttonPanel.rowHeights = new int[]{23, 23, 0, 0, 0, 0, 0, 0, 0};
    gbl_buttonPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gbl_buttonPanel.rowWeights =
        new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    buttonPanel.setLayout(gbl_buttonPanel);

    JButton btnManagePlayers = new JButton("Manage Players");
    btnManagePlayers.setToolTipText("Add/View Players");
    btnManagePlayers.setHorizontalAlignment(JButton.CENTER);
    buttonPanel.add(btnManagePlayers,
                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0,
                                           0));

    JButton btnManageGames = new JButton("Manage Games");
    btnManageGames.setHorizontalAlignment(JButton.CENTER);
    buttonPanel
        .add(btnManageGames, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                    GridBagConstraints.HORIZONTAL,
                                                    new Insets(0, 0, 5, 0), 0, 0));

    JButton btnManageTournaments = new JButton("Manage Tournaments");
    btnManageTournaments.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnManageTournaments_actionPerformed();
      }
    });
    buttonPanel.add(btnManageTournaments,
                    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                           GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));

    JButton btnCloseApp = new JButton("Close App");
    btnCloseApp.setToolTipText("Exit the Application\r\n");
    btnCloseApp.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnCloseApp_actionPerformed();
      }
    });

    JPanel spacerPanel = new JPanel();
    spacerPanel.setBackground(BACKGROUND);
    buttonPanel
        .add(spacerPanel, new GridBagConstraints(0, 3, 1, 4, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
                                                 0));

    GridBagLayout gbl_spacerPanel = new GridBagLayout();
    gbl_spacerPanel.columnWidths = new int[]{0};
    gbl_spacerPanel.rowHeights = new int[]{0};
    gbl_spacerPanel.columnWeights = new double[]{Double.MIN_VALUE};
    gbl_spacerPanel.rowWeights = new double[]{Double.MIN_VALUE};
    spacerPanel.setLayout(gbl_spacerPanel);
    buttonPanel
        .add(btnCloseApp, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 0, 0), 0, 0));

    JPanel welcomeImgPanel = new JPanel();
    welcomeImgPanel.setBackground(BACKGROUND);
    parentPane.add(welcomeImgPanel,
                   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                          GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    JLabel lblWelcomeImage = new JLabel("");
    lblWelcomeImage.setBackground(BACKGROUND);
    lblWelcomeImage
        .setIcon(new ImageIcon(
            TournamentGUI.class.getResource("/images/worldCup_open.jpg")));
    welcomeImgPanel.add(lblWelcomeImage);

    btnManagePlayers.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        btnManagePlayers_actionPerformed();
      }
    });

    btnManageGames.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnManageGames_actionPerfomed();
      }
    });
  }
}
