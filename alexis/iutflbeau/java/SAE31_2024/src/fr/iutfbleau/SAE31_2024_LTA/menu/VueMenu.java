package fr.iutfbleau.SAE31_2024_LTA.menu;

import fr.iutfbleau.SAE31_2024_LTA.Bdd.BddListeTuiles;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.endGame.QuitAction;
import fr.iutfbleau.SAE31_2024_LTA.jeux.controller.ControllerPlayCard;
import fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent;
import fr.iutfbleau.SAE31_2024_LTA.partieJouer.ControllerPartieJouerBTN;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleButton;
import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleImageTitre;

/**
 * VueMenu représente l'interface graphique du menu principal du jeu.
 * Elle gère l'affichage des composants du menu et certainnes interactions avec le joueur.
 */
public class VueMenu extends JPanel {
    private JTextField playerNameInput;
    private JComboBox<String> suiteSelector;
    private final ModelPrincipale modelPrincipale;
    private final JLabel backgroundImage;
    private JPanel sidebarPanel;
    private final JLayeredPane layeredPane;

    /**
     * Crée une nouvelle instance de VueMenu.
     *
     * @param modelPrincipale Le modèle principal de l'application.
     */
    public VueMenu(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
        setLayout(null);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, modelPrincipale.getVuePrincipale().getWidth(), modelPrincipale.getVuePrincipale().getHeight());
        add(layeredPane);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/FondMenu.jpg")));
        backgroundImage = new JLabel(bgIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = bgIcon.getImage();

                int windowWidth = getWidth();
                int windowHeight = getHeight();
                int imageWidth = img.getWidth(null);
                int imageHeight = img.getHeight(null);

                double windowRatio = (double) windowWidth / windowHeight;
                double imageRatio = (double) imageWidth / imageHeight;

                int newWidth, newHeight;

                if (imageRatio > windowRatio) {
                    newWidth = windowWidth;
                    newHeight = (int) (windowWidth / imageRatio);
                }else {
                    newHeight = windowHeight;
                    newWidth = (int) (windowHeight * imageRatio);
                }

                int x = (windowWidth - newWidth) / 2;
                int y = (windowHeight - newHeight) / 2;

                g.drawImage(img, x, y, newWidth, newHeight, this);
            }
        };

        layeredPane.add(backgroundImage, Integer.valueOf(0));

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateMenu();
            }
        });

        initSidebarComponent();
        layeredPane.add(sidebarPanel, Integer.valueOf(1));
        modelPrincipale.getVuePrincipale().setActionMap();
    }

    /**
     * Met à jour la taille et la position des composants du menu.
     */
    public void updateMenu() {
        int widthSidebar = 450;
        int heightSidebar = 630;
        backgroundImage.setSize(getWidth(), getHeight());
        layeredPane.setBounds(0, 0, modelPrincipale.getVuePrincipale().getWidth(), modelPrincipale.getVuePrincipale().getHeight());
        sidebarPanel.setBounds(getWidth() - 550, (getHeight() - heightSidebar) / 2, widthSidebar, heightSidebar); // centrer verticalement et coler a droite
    }

    /**
     * Initialise la barre latérale avec les composants nécessaires.
     */
    private void initSidebarComponent() {
        int buttonFontSize = 30;

        sidebarPanel = new JPanel(new GridBagLayout());
        sidebarPanel.setBackground(StyleComponent.getPanelColor());
        sidebarPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.gridx = 0;


        sidebarPanel.add(setStyleImageTitre(), gbc);

        playerNameInput = new JTextField("Player Name", 15);
        playerNameInput.addFocusListener(new ControllerFocus(this, modelPrincipale));
        playerNameInput.setText(modelPrincipale.getConfigManager().getPlayerName());

        gbc.gridy = 1;
        sidebarPanel.add(StyleComponent.setStyleTextField(playerNameInput,22), gbc);

        suiteSelector = new JComboBox<>();
        suiteSelector.addItem("Choisir une suite...");

        List<BddListeTuiles> listeTuiles;
        if (modelPrincipale.getBdd().updateBdd()) {
            listeTuiles = modelPrincipale.getBdd().getAllListe();
            for (BddListeTuiles tuileListSeed : listeTuiles) {
                String suiteName;
                if (tuileListSeed.getId() != -2) {
                    suiteName = "Suite : " + tuileListSeed.getId();
                } else {
                    suiteName = "Suite : Aléatoire";
                }
                suiteSelector.addItem(suiteName + " - BestScore: " +
                        (tuileListSeed.getBestScore() != null ? tuileListSeed.getBestScore() : "N/A"));
            }
        }else {
            suiteSelector.addItem("Suite : Aléatoire");
            listeTuiles = null;
        }

        gbc.gridy = 2;
        sidebarPanel.add(StyleComponent.setStyleComboBox(suiteSelector,24,this), gbc);

        JButton playButton = new JButton("Jouer");
        playButton.addActionListener(new ControllerPlayCard(modelPrincipale, listeTuiles));

        gbc.gridy = 3;
        sidebarPanel.add(setStyleButton(playButton,buttonFontSize), gbc);


        JButton partieJouerBtn = new JButton("Partie Jouer");
        partieJouerBtn.addActionListener(new ControllerPartieJouerBTN(modelPrincipale));

        gbc.gridy = 4;
        sidebarPanel.add(setStyleButton(partieJouerBtn,buttonFontSize), gbc);

        JButton settingsButton = new JButton("Paramètres");
        settingsButton.addActionListener(modelPrincipale.getControllerInputMap());

        gbc.gridy = 5;
        sidebarPanel.add(setStyleButton(settingsButton,buttonFontSize), gbc);

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(new QuitAction());

        gbc.gridy = 6;
        sidebarPanel.add(setStyleButton(quitButton,buttonFontSize), gbc);
    }

    /**
     * @return Le JComboBox pour sélectionner la suite.
     */
    public JComboBox<String> getSuiteSelector() {
        return suiteSelector;
    }

    /**
     * @return Le JTextField pour le nom du joueur.
     */
    public JTextField getPlayerNameInput() {
        return playerNameInput;
    }
}
