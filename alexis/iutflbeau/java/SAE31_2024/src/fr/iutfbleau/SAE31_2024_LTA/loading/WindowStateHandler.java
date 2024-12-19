package fr.iutfbleau.SAE31_2024_LTA.loading;

import fr.iutfbleau.SAE31_2024_LTA.ControlerMain;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La classe WindowStateHandler étend WindowAdapter et gère les événements de changement d'état de la fenêtre.
 * Elle est utilisée pour mettre à jour la taille de l'interface utilisateur lorsque l'état de la fenêtre change.
 */
public class WindowStateHandler extends WindowAdapter {

    /**
     * Méthode appelée lorsqu'un changement d'état de la fenêtre se produit.
     *
     * @param e L'événement de changement d'état de la fenêtre.
     */
    @Override
    public void windowStateChanged(WindowEvent e) {
        ControlerMain.getModelPrincipale().getVuePrincipale().updateSize();
    }
}
