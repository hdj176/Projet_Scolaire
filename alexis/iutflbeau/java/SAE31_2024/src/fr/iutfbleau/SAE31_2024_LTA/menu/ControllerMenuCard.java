package fr.iutfbleau.SAE31_2024_LTA.menu;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ControllerMenuCard gère les actions du menu, notamment le changement de vues
 * et la gestion de la musique de fond lors de l'accès au menu.
 */
public class ControllerMenuCard implements ActionListener {
    private final ModelPrincipale modelPrincipale;

    /**
     * Crée un nouveau ControllerMenuCard.
     *
     * @param modelPrincipale Le modèle principal de l'application.
     */
    public ControllerMenuCard(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
    }

    /**
     * Méthode qui change la vue pour afficher le menu principal.
     * Arrête la musique de jeu, joue un son de clic, et démarre la musique du menu.
     */
    public void goMenu(){
        VuePrincipale vuePrincipale = modelPrincipale.getVuePrincipale();

        modelPrincipale.getMediaPlayerManager().stopClip(modelPrincipale.getModelMediaLoader().getGameMusicClips());
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClicAudioClip(), false);
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getMenuMusicClip(), true);

        vuePrincipale.getPrincipaleLayeredPane().getMainPanel().getCardLayout().show(vuePrincipale.getPrincipaleLayeredPane().getMainPanel(), "menu");

        vuePrincipale.setTitle("DorfJavatik - Menu");
    }

    /**
     * Méthode appelée lors d'un événement d'action.
     * Arrête la musique de jeu, joue un son de clic, et démarre la musique du menu.
     *
     * @param event L'événement d'action qui a été déclenché.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        VuePrincipale vuePrincipale = modelPrincipale.getVuePrincipale();

        for (int i = 0; i < modelPrincipale.getModelMediaLoader().getGameMusicClips().size(); i++){
            modelPrincipale.getMediaPlayerManager().stopClip(modelPrincipale.getModelMediaLoader().getGameMusicClips().get(i));
        }

        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClicAudioClip(), false);
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getMenuMusicClip(), true);

        vuePrincipale.getPrincipaleLayeredPane().getMainPanel().getCardLayout().show(vuePrincipale.getPrincipaleLayeredPane().getMainPanel(), "menu");

        vuePrincipale.setTitle("DorfJavatik - Menu");
        vuePrincipale.getModelPrincipale().getBdd().setGameSaved(false);
    }
}
