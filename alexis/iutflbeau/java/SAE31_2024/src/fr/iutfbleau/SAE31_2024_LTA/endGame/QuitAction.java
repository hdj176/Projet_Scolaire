package fr.iutfbleau.SAE31_2024_LTA.endGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe {@code QuitAction} implémente l'interface {@code ActionListener}
 * pour gérer l'action de quitter l'application.
 * Lorsqu'un événement d'action est déclenché, l'application se ferme.
 */
public class QuitAction implements ActionListener {
    /**
     * Méthode appelée lorsqu'un événement d'action se produit.
     *
     * @param e l'événement d'action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0); // Ferme l'application
    }
}