package fr.iutfbleau.SAE31_2024_LTA.partieJouer;

import fr.iutfbleau.SAE31_2024_LTA.menu.ControllerMenuCard;
import fr.iutfbleau.SAE31_2024_LTA.Bdd.BddPartieJouer;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VuePartieJouer extends JPanel {

    private final ModelPrincipale modelPrincipale;
    private final ModelPartieJouer modelPartieJouer;

    private JTable tableView;
    private JTextField searchField;

    private ControllerSearchPartieJouer controllerSearchPartieJouer;

    public VuePartieJouer(ModelPrincipale modelPrincipale, ModelPartieJouer modelPartieJouer) {

        this.modelPrincipale = modelPrincipale;

        this.modelPartieJouer = modelPartieJouer;
        setLayout(new BorderLayout());

        initSwingComponents();
    }

    /**
     * Initialise les composants Swing avec une table et une barre de recherche.
     */
    private void initSwingComponents() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(1750, 900));

        JPanel sidebar = createSidebar();

        contentPane.add(sidebar, BorderLayout.EAST);

        tableView = createTableView();
        JScrollPane scrollPane = new JScrollPane(tableView);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        add(contentPane, BorderLayout.CENTER);
    }

    /**
     * Crée la sidebar avec des boutons et des options de recherche.
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(300, getHeight()));

        JLabel searchLabel = new JLabel("Rechercher une partie");
        searchLabel.setBounds(modelPrincipale.getVuePrincipale().getWidth()-200,30,180,50);

        searchField = new JTextField(20);
        searchField.setMaximumSize(new Dimension(200, 30));

        JButton searchButton = new JButton("Rechercher");
        searchButton.setPreferredSize(new Dimension(200, 50));
        this.controllerSearchPartieJouer = new ControllerSearchPartieJouer(modelPrincipale);
        searchButton.addActionListener(controllerSearchPartieJouer);

        JButton menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(200, 50));
        menuButton.addActionListener(new ControllerMenuCard(modelPrincipale));
        
        if (modelPrincipale.getBdd().updateBdd()) {
            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(StyleComponent.setStyleLabel(searchLabel,18));
            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(StyleComponent.setStyleTextField(searchField,18));
            sidebar.add(Box.createVerticalStrut(20));
            sidebar.add(StyleComponent.setStyleButton(searchButton,19));
        }
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(StyleComponent.setStyleButton(menuButton,19));
        sidebar.setBackground(StyleComponent.getPanelColor());
        return sidebar;
    }

    /**
     * Crée un tableau Swing (JTable) pour afficher les parties jouées.
     */
    private JTable createTableView() {
        String[] columnNames = {"Joueur", "Suite", "Score"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        initTableValue(tableModel);

        return table;
    }

    /**
     * Charge les valeurs des parties jouées dans le tableau.
     */
    private void initTableValue(DefaultTableModel tableModel) {
        if (modelPrincipale.getBdd().isInConnexion()){
            while (modelPrincipale.getBdd().isInConnexion()){
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        List<BddPartieJouer> allParties = modelPartieJouer.getAllParties();
        if (allParties != null) {
            for (BddPartieJouer partie : allParties) {
                Object[] rowData = {partie.getPlayerName(), partie.getScore(), partie.getListeTuile().getId()};
                tableModel.addRow(rowData);
            }
        }else{
            Object[] rowData = {"Tu n'est pas connecter a la base de donnée", "offline", "offline"};
            tableModel.addRow(rowData);
        }
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTable getTableView() {
        return tableView;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) tableView.getModel();
    }

    public ModelPartieJouer getModelPartieJouer() {
        return modelPartieJouer;
    }

    public ControllerSearchPartieJouer getControllerSearchPartieJouer() {
        return controllerSearchPartieJouer;
    }
}
