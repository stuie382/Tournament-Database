package com.stuart.tourny.view;

import com.stuart.tourny.controller.PlayerController;
import com.stuart.tourny.controller.QueryController;
import com.stuart.tourny.model.common.dto.DTOPlayer;
import com.stuart.tourny.model.utils.exceptions.ServerProblem;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ManagePlayersDialog extends ManageDialog {

  private static final String ALPHA_NUMERIC_REGEX = "^[a-zA-Z0-9\\s]*$";
  private static final String MANAGE_PLAYERS = "Manage Players";
  private static final String PLAYER = "Player";
  public static final int MAX_LENGTH = 50;

  private ResultSetTablePanel resultSetTablePanel;

  public ManagePlayersDialog(Window parent) {
    super(parent, MANAGE_PLAYERS);
    getContentPane().setBackground(TournamentGUI.BACKGROUND);
    initGUI();
  }

  @Override
  protected void btnViewAll_actionPerformed() {
    try {
      QueryController query = new QueryController();
      ResultSet rs = query.managePlayers_viewAll();
      resultSetTablePanel.populateData(rs);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
                                    "<html><h2>Error getting players:</h2> " + "Error details:" + ex
                                    + "</html>",
                                    "Error getting all players from the database.",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  /**
   * Get the input from the user, so we can add a new player to the database.
   */
  @Override
  protected void btnAdd_actionPerformed() {
    String userInput = JOptionPane.showInputDialog(this,
                                                   "Please enter the full name of the new player. Be careful, as once created it cannot be changed!",
                                                   "Add a new player.",
                                                   JOptionPane.INFORMATION_MESSAGE);
    if (userInput != null) {
      // OK so the user has entered something. Need to make sure it is
      // valid.
      if (isUserInputValid(userInput)) {
        try {
          PlayerController controller = new PlayerController();
          DTOPlayer dto = new DTOPlayer();
          dto.setName(userInput);
          dto = controller.addPlayer(dto);
          JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
                                        "New player added - " + dto.getName(),
                                        "Success!",
                                        JOptionPane.INFORMATION_MESSAGE);
          return;
        } catch (ServerProblem sp) {
          JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
                                        "<html><h2>Error adding a player</h2>" + "Error details:"
                                        + sp + "</html>",
                                        "Error adding new player to the database.",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }
      } else {
        JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
                                      "Please use alphanumeric characters only for player names!",
                                      "Oops",
                                      JOptionPane.INFORMATION_MESSAGE);
        return;
      }
    }
  }

  /**
   * Checks that the user has provided a name that only contains alphanumeric characters and is no
   * longer than 50 characters.
   *
   * @param userInput
   *
   * @return true if both conditions are met
   */
  private boolean isUserInputValid(String userInput) {
    return userInput.matches(ALPHA_NUMERIC_REGEX)
           && userInput.length() <= MAX_LENGTH;
  }

  private void throwException() throws Exception {
    throw new Exception(stackTrace);
  }

  // TODO remove this later
  private final String
      stackTrace =
      "java.lang.NullPointerException\nabc.investxa.presentation.controllers.UnixServerJobController.handleRequest(UnixServerJobController.java:66)\norg.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter.handle(SimpleControllerHandlerAdapter.java:48)\norg.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:875)\norg.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:807)\norg.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:571)\norg.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:501)\njavax.servlet.http.HttpServlet.service(HttpServlet.java:690)\njavax.servlet.http.HttpServlet.service(HttpServlet.java:803)\norg.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:96)\norg.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:76)";

  /**
   * Setup the GUI
   */
  private void initGUI() {
    initGUI(MANAGE_PLAYERS, PLAYER);
    resultSetTablePanel = new ResultSetTablePanel();
    getContentPane().add(resultSetTablePanel,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
                                                0));
  }
}
