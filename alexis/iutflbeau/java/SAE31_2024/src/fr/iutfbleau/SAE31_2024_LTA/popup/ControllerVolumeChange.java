package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controleur qui permet de gérer les changements de volume de la musique et des effets sonores.
 */
public class ControllerVolumeChange implements ChangeListener {
    private final int type;
    private final ConfigManager configManager;
    private final ModelPrincipale modelPrincipale;
    /**
     * Constructeur pour ControllerVolumeChange.
     *
     * @param configManager    le gestionnaire de configuration pour sauvegarder les niveaux de volume.
     * @param groupToChange    le type de groupe à modifier : 0 pour la musique, 1 pour les effets.
     * @param modelPrincipal    le ModelPrincipal  utilisé pour gérer l'application.
     */
    public ControllerVolumeChange(ConfigManager configManager, int groupToChange, ModelPrincipale modelPrincipal) {
        this.type = groupToChange; // si groupToChange = 0 c'est les musique, si c'est 1 c'est les effets
        this.configManager = configManager;
        this.modelPrincipale = modelPrincipal;
    }

    /**
     * Méthode appelée lorsque l'état du slider de volume change.
     * Met à jour le niveau de volume correspondant dans le gestionnaire de configuration
     * et dans le gestionnaire de médias.
     *
     * @param e l'événement de changement d'état déclenché par le slider.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        int volume = ((JSlider) e.getSource()).getValue();
        if (type == 0) {
            configManager.setVolumeMusique(volume);
            modelPrincipale.getMediaPlayerManager().setVolumeMusique(configManager.getVolumeMusique());
            modelPrincipale.getMediaPlayerManager().setVolumeEffect(configManager.getVolumeEffet());
        } else if (type == 1) {
            configManager.setVolumeEffet(volume);
        }
    }
}
