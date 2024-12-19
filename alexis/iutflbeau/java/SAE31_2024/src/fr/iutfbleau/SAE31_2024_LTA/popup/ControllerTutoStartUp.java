package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur pour gérer l'option "Montrer au démarrage" dans la VueTuto.
 * Ce controleur permet de sauvegarder la préférence de l'utilisateur via le  ConfigManager.
 */
public class ControllerTutoStartUp implements ActionListener {

    private final ConfigManager configManager;
    private JCheckBox showAtStartupCheckBox;

    /**
     * Constructeur de ControllerTutoStartUp
     *
     * @param configManager       le gestionnaire de configuration utilisé pour sauvegarder les préférences de l'utilisateur.
     * @param showAtStartupCheckBox la case à cocher représentant l'option "Montrer au démarrage".
     */
    public ControllerTutoStartUp(ConfigManager configManager, JCheckBox showAtStartupCheckBox) {
        this.configManager = configManager;
        this.showAtStartupCheckBox = showAtStartupCheckBox;
    }

    /**
     * Définit la préférence de démarrage du tutoriel en fonction de l'état de la case à cocher.
     *
     * @param e l'événement d'action déclenché lors de la sélection de la case à cocher.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        configManager.setTuto(showAtStartupCheckBox.isSelected());
    }
}
