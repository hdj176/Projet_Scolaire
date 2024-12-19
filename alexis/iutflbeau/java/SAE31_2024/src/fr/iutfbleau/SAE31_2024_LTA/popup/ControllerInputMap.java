package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Contrôleur pour gérer les touches, permettant
 * de déclencher des actions dans le modèle principal.
 */
public class ControllerInputMap extends AbstractAction {

    private final ModelPrincipale modelPrincipale;
    private final String action;

    /**
     * Constructeur du contrôleur de la carte d'entrées.
     *
     * @param modelPrincipale le modèle principal de l'application.
     * @param action          l'action associée à cette touche.
     */
    public ControllerInputMap(ModelPrincipale modelPrincipale, String action){
        this.modelPrincipale = modelPrincipale;
        this.action = action;
    }

    /**
     * Effectue l'action définie lorsque l'événement est déclenché.
     * Exécute l'annulation d'une action si "undo" est spécifié,
     * ou alterne l'affichage des paramètres pour d'autres commandes.
     *
     * @param e l'événement déclencheur.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("undo".equals(action)) {
            modelPrincipale.getModelJeux().undoLastTuile();
        }else if ("toggleSettingsAction".equals(action) || "Paramètres".equals(e.getActionCommand())) {
            modelPrincipale.getControllerPopup().togleSettingsDialog();
        }
    }
}
