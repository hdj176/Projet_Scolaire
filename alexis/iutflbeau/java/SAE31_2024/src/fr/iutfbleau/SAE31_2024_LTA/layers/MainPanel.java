package fr.iutfbleau.SAE31_2024_LTA.layers;

import javax.swing.*;
import java.awt.*;

/**
 * La classe MainPanel représente le panneau principal de l'application qui utilise un CardLayout
 * pour gérer l'affichage de différentes vues.
 */
public class MainPanel extends JPanel {

    private final CardLayout cardLayout;

    /**
     * Constructeur de la classe MainPanel.
     *
     * @param vuePrincipale La vue principale de l'application, qui peut être ajoutée à ce panneau.
     */
    public MainPanel(VuePrincipale vuePrincipale) {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    /**
     * Retourne le CardLayout utilisé par ce panneau.
     *
     * @return le CardLayout du MainPanel.
     */
    public CardLayout getCardLayout() {
        return this.cardLayout;
    }
}
