package fr.iutfbleau.SAE31_2024_LTA.layers;

import javax.swing.*;
import java.awt.*;

/**
 * La classe PrincipaleLayeredPane est un panneau superposé (JLayeredPane) qui contient
 * un MainPanel. Elle permet de gérer plusieurs couches de composants graphiques.
 */
public class PrincipaleLayeredPane extends JLayeredPane {

    private final MainPanel mainPanel;

    /**
     * Constructeur de la classe PrincipaleLayeredPane.
     *
     * @param vuePrincipale La vue principale de l'application, qui sera utilisée pour créer le MainPanel.
     */
    public PrincipaleLayeredPane(VuePrincipale vuePrincipale) {
        mainPanel = new MainPanel(vuePrincipale);
        this.add(mainPanel, Integer.valueOf(0));
    }

    /**
     * Retourne le MainPanel contenu dans ce panneau superposé.
     *
     * @return le MainPanel de ce PrincipaleLayeredPane.
     */
    public MainPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Retourne le CardLayout utilisé par le MainPanel de ce panneau superposé.
     *
     * @return le CardLayout du MainPanel.
     */
    public CardLayout getMainCardLayout() {
        return mainPanel.getCardLayout();
    }
}
