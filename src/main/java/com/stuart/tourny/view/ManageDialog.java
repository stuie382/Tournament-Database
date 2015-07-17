package com.stuart.tourny.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * Abstract parent that will create the standard 'Manage' screen layout. Each implementing class
 * will need to add their own {@link ResultSetTablePanel} as they will probably need to manipulate
 * it in their own way.
 * <p>
 * Each of the required action listeners is defined as an abstract method, so each managment screen
 * is forced to handle them. This allows each screen to perform a custom action. Except for close as
 * that will need to do the same thing so that can be handled here.
 *
 * @author Stuart
 */
public abstract class ManageDialog extends JDialog {

  /* Protected so the implementing classes can add any additional buttons
   * required to the {@code buttonPanel}.
   */
  protected JPanel buttonPanel;

  /**
   * Create the dialog providing the parent window and a title. This will be which type of
   * management screen you wish to create.
   *
   * @param parent
   *     - Parent frame that owns this dialog
   * @param title
   *     - The title for the dialog
   */
  public ManageDialog(Window parent, String title) {
    super(parent, title);
  }

  /**
   * Each implementor will need to call this method first in their own initGUI method. This creates
   * the standard layout to which the ResultSetTablePanel can be added. <p> This also ensures that
   * each management screen is nice and consistent with each other.</p>
   *
   * @param title
   *     - Title of the dialog screen
   * @param type
   *     - Singular of the title
   */
  protected void initGUI(String title, String type) {
    setSize(400, 400);
    super.setBackground(TournamentGUI.BACKGROUND);
    setModal(true);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{99, 0, 0};
    gridBagLayout.rowHeights = new int[]{362, 0};
    gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    getContentPane().setLayout(gridBagLayout);

    // Buttons panel
    buttonPanel = new JPanel();
    buttonPanel.setBackground(TournamentGUI.BACKGROUND);
    buttonPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
    getContentPane()
        .add(buttonPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
                                                 GridBagConstraints.VERTICAL,
                                                 new Insets(0, 0, 0, 5), 0, 0));
    GridBagLayout gbl_buttonPanel = new GridBagLayout();
    gbl_buttonPanel.columnWidths = new int[]{85, 0};
    gbl_buttonPanel.rowHeights = new int[]{23, 0, 0, 0, 0};
    gbl_buttonPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gbl_buttonPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
    buttonPanel.setLayout(gbl_buttonPanel);

    JButton btnViewAll = new JButton("View All");
    btnViewAll.setToolTipText("Show all " + type + "s that exist.");
    btnViewAll.addActionListener(e -> btnViewAll_actionPerformed());

    JButton btnAdd = new JButton("Add " + type);
    btnAdd.setToolTipText("Add a new " + type + " to the database.");
    btnAdd.addActionListener(e -> btnAdd_actionPerformed());

    buttonPanel
        .add(btnAdd, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 0, 5, 0), 0, 0));

    buttonPanel
        .add(btnViewAll, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 5, 0), 0, 0));

    // Simple transparent spacer for the buttons panel
    JPanel spacerPanel = new JPanel();
    spacerPanel.setOpaque(false);
    buttonPanel
        .add(spacerPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
                                                 0));

    JButton btnClose = new JButton("Close");
    btnClose.setToolTipText("Close the " + title + " window.");
    btnClose.addActionListener(e -> btnClose_actionPerformed());
    buttonPanel
        .add(btnClose, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                                              GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
                                              0, 0));
  }

  /**
   * Simply dispose of this Dialog.
   */
  protected void btnClose_actionPerformed() {
    dispose();
  }

  protected abstract void btnAdd_actionPerformed();

  protected abstract void btnViewAll_actionPerformed();

}