package fr.iutfbleau.SAE31_2024_LTA.partieJouer;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Le contrôleur pour gérer les actions sur les boutons liés aux parties jouées.
 * Lorsque le bouton est cliqué, le contrôleur demande au ModelPrincipale de mettre à jours la base de données et
 * affiche l'interface pour la liste des parties jouées.
 */
public class ControllerPartieJouerBTN implements ActionListener {
    VuePrincipale vuePrincipale;
    ModelPrincipale modelPrincipale;

    /**
     * Controller pour les actions des boutons liés aux parties jouées.
     *
     * @param modelPrincipale le modèle principal qui gère l'état de l'application.
     */
    public ControllerPartieJouerBTN(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
        this.vuePrincipale = modelPrincipale.getVuePrincipale();
    }

    /**
     * Gère les actions effectuées sur les boutons.
     * Demande au ModelPrincipale de mettre à jours la base de données et affiche la vue de la liste des parties jouées.
     *
     * @param e l'événement d'action déclenché par le bouton.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        modelPrincipale.getBdd().updateBdd();
        vuePrincipale.getPrincipaleLayeredPane().getMainPanel().getCardLayout().show(vuePrincipale.getPrincipaleLayeredPane().getMainPanel(), "partieJouer");
        vuePrincipale.setTitle("DorfJavatik - Liste des parties jouer");
    }
}
