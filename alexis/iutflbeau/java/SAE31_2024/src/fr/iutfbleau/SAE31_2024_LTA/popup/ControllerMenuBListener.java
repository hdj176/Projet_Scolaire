package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.menu.ControllerMenuCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur de gestion des actions du bouton dans la vue des paramètres.
 * Ce contrôleur permet de fermer le menu des paramètres et de revenir au menu principal.
 */
public class ControllerMenuBListener implements ActionListener {

    /**
     * Constructeur du contrôleur de bouton de menu.
     *
     * @param controllerPopup le contrôleur des pop-ups.
     * @param modelPrincipale le modèle principal de l'application.
     */
    private final ControllerPopup controllerPopup;
    private final ModelPrincipale modelPrincipale;
    public ControllerMenuBListener(ControllerPopup controllerPopup, ModelPrincipale modelPrincipale) {
        this.controllerPopup = controllerPopup;
        this.modelPrincipale = modelPrincipale;
    }

    /**
     * Méthode déclenchée lors de l'action sur le bouton.
     * Ferme la fenêtre des paramètres et retourne au menu principal.
     *
     * @param e l'événement déclencheur.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      ControllerMenuCard controllerMenuCard = new ControllerMenuCard(modelPrincipale);
      controllerPopup.closeSettings();
      controllerMenuCard.goMenu();
}
}
