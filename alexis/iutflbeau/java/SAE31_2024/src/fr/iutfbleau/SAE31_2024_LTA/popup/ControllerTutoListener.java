package fr.iutfbleau.SAE31_2024_LTA.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur pour afficher le tutoriel en fermant d'abord les paramètres s'ils sont déjà ouverts.
 */
public class ControllerTutoListener implements ActionListener {
    ControllerPopup controllerPopup;

    /**
     * Constructeur  de ControllerTutoListener.
     *
     * @param controllerPopup le contrôleur des pop-ups.
     */
    ControllerTutoListener(ControllerPopup controllerPopup) {
        this.controllerPopup = controllerPopup;
    }

    /**
     * Méthode déclenchée lors de l'action sur le bouton : ferme les paramètres et affiche le tutoriel.
     *
     * @param e l'événement déclencheur.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (controllerPopup != null) {
            controllerPopup.closeSettings();
            controllerPopup.showTutoDialog();
        } else {
            System.err.println("erreur qui existe depuis le MakeFile");
        }
    }
}
