package fr.iutfbleau.SAE31_2024_LTA.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur pour gérer les actions de reprise d'un pop-up, soit en fermant les paramètres,
 * soit en fermant le tutoriel en fonction de la valeur de wich.
 */
public class ControllerResumeListener implements ActionListener {
    ControllerPopup controllerPopup;
    int wich;

    /**
     * Constructeur de ControllerResumeListener.
     *
     * @param controllerPopup le controleur des pop-ups.
     * @param wich indique l'action à effectuer (0 pour les paramètres, 1 pour le tutoriel).
     */
    ControllerResumeListener(ControllerPopup controllerPopup, int wich) {
        this.controllerPopup = controllerPopup;
        this.wich = wich;
    }

    /**
     * Méthode déclenchée lors de l'action sur le bouton.
     * En fonction de la valeur de wich, ouvre ou ferme le pop-up correspondant.
     *
     * @param e l'événement déclencheur.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (wich == 0){
            controllerPopup.togleSettingsDialog();
        }
        else if (wich == 1){
            controllerPopup.closeTuto();
        }

    }
}
