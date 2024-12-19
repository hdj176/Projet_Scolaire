package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.animator.Animator;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;
import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;

import javax.swing.*;

/**
 * Controleur pour gérer les popups dans l'application, y compris les
 * fenêtres de réglages et de tutoriel.
 * Il permet d'afficher, masquer et mettre à jour les popups.
 */
public class ControllerPopup  {

    private ConfigManager configManager;

    private VueSettingsPopup vueSettings;

    private VueTuto vueTuto;

    private final VuePrincipale vuePrincipale;


    /**
     * Constructeur du contrôleur de popup.
     *
     * @param vuePrincipale la vue principale de l'application.
     */
    public ControllerPopup(VuePrincipale vuePrincipale) {
        this.vuePrincipale = vuePrincipale;

    }

    /**
     * Crée et affiche la popup de réglages si elle n'existe pas déjà.
     * Initialise le gestionnaire de configuration et ajuste les volumes audio.
     */
    public void createSettings(){
        if (vueSettings == null) {
            this.vueSettings = new VueSettingsPopup(this, vuePrincipale.getModelPrincipale());
            vuePrincipale.getPrincipaleLayeredPane().add(vueSettings, Integer.valueOf(1));
            if (this.configManager == null) {
                this.configManager = vuePrincipale.getModelPrincipale().getConfigManager();
                vuePrincipale.getModelPrincipale().getMediaPlayerManager().setVolumeMusique(configManager.getVolumeMusique());
                vuePrincipale.getModelPrincipale().getMediaPlayerManager().setVolumeEffect(configManager.getVolumeEffet());
            }
            vueSettings.setMusicVolume(configManager.getVolumeMusique());
            vueSettings.setEffectsVolume(configManager.getVolumeEffet());
        }
    }

    /**
     * Ferme le popup de réglages avec une animation et met à jour l'état d'ouverture.
     */
    public void closeSettings(){
        if (vueSettings != null) {
            Animator.moveTo(vueSettings , (vuePrincipale.getWidth()-vueSettings.getWidth())/2, vueSettings.getY()  ,(vuePrincipale.getWidth()-vueSettings.getWidth())/2,-vueSettings.getHeight(), 800,true);
        }
        vueSettings.setOpen(false);
    }

    /**
     * Ferme le popup de tutoriel (avec une animation).
     */
    public void closeTuto(){
        if (vueTuto != null) {
            Animator.moveTo(vueTuto , (vuePrincipale.getWidth()-vueTuto.getWidth())/2,vueTuto.getY() ,(vuePrincipale.getWidth()-vueTuto.getWidth())/2,-vueTuto.getHeight(), 600,true);
        }
    }


    /**
     * Affiche le popup de réglages (avec une animation).
     *
     * @return une action pour montrer le popup.
     */
    public Action showSettingsDialog() {
            Animator.moveTo(vueSettings , (vuePrincipale.getWidth()-vueSettings.getWidth())/2, vueSettings.getY() ,(vuePrincipale.getWidth()-vueSettings.getWidth())/2,(vuePrincipale.getHeight()-vueSettings.getHeight())/2, 800,true);

        return null;
    }


    /**
     * Affiche le popup de tutoriel (avec une animation).
     */
    public void showTutoDialog() {
        if (vueTuto == null) {
            this.vueTuto = new VueTuto(this,this.configManager);
            vuePrincipale.getPrincipaleLayeredPane().add(vueTuto, Integer.valueOf(2));
        }
        Animator.moveTo(vueTuto , (vuePrincipale.getWidth()-vueTuto.getWidth())/2,vueTuto.getY() ,(vuePrincipale.getWidth()-vueTuto.getWidth())/2,(vuePrincipale.getHeight()-vueTuto.getHeight())/2, 600,true);
    }

    /**
     * @return l'instance de {@link VuePrincipale}.
     */
    public VuePrincipale getVuePrincipale(){
        return this.vuePrincipale;
    }

    /**
     * Alterne l'affichage du popup de réglages. Affiche le popup si fermé, le
     * ferme sinon, et met à jour l'état d'ouverture.
     */
    public void togleSettingsDialog() {
        if (!vueSettings.isOpen()){
            showSettingsDialog();
            vueSettings.setOpen(true);
        }else {
            closeSettings();
            vueSettings.setOpen(false);
        }
        vuePrincipale.getPrincipaleLayeredPane().repaint();
    }

    /**
     * Met à jour les vues des popups de réglages et de tutoriel.
     */
    public void updatePopup() {
        if (vueSettings != null) {
            vueSettings.updateVueSettings();
        }
        if (vueTuto != null){
            vueTuto.updateVueTuto(vuePrincipale);
        }
    }
}
