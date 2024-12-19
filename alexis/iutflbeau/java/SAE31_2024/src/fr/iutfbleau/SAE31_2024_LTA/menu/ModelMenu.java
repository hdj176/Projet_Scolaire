package fr.iutfbleau.SAE31_2024_LTA.menu;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

/**
 * ModelMenu gère la logique du menu principal de l'application,
 * y compris la création de la vueMenu et la gestion de la musique de fond.
 */
public class ModelMenu {

    private final ModelPrincipale modelPrincipale;
    private VueMenu vueMenu;

    /**
     * Crée un nouveau ModelMenu.
     * Démarre la musique du menu et crée la vue du menu.
     *
     * @param modelPrincipale Le modèle principal de l'application.
     */
    public ModelMenu(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;

        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getMenuMusicClip(), true);

        createVueMenu();
    }
    /**
     * @return La vue du menu.
     */
    public VueMenu getVueMenu() {
        return vueMenu;
    }
    /**
     * Crée la vue du menu et l'ajoute au  Modelprincipal.
     * Affiche la vue du menu et initialise une nouvelle partie à jouer.
     * Si le tutoriel est activé, affiche le tutoriel.
     */
    public void createVueMenu() {
        vueMenu = new VueMenu(modelPrincipale);
        modelPrincipale.getVuePrincipale().getPrincipaleLayeredPane().getMainPanel().add(vueMenu, "menu");
        modelPrincipale.getVuePrincipale().getPrincipaleLayeredPane().getMainCardLayout().show(modelPrincipale.getVuePrincipale().getPrincipaleLayeredPane().getMainPanel(), "menu");
        modelPrincipale.createPartieJouer();
        if (modelPrincipale.getConfigManager().isTuto()){
            modelPrincipale.getControllerPopup().showTutoDialog();
        }
    }
}
