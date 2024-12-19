package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cette class permet de gérer l'antialiasing à l'aide du configmanage, et de sa checkbox associée.
 */
public class AntiAliasingChange implements ActionListener {
    ConfigManager configManager;
    JCheckBox AACheckBox;

    /**
     * constructeur de AntiAliasingChange
     *
     * @param configManager son ConfigManager associé
     * @param AACheckBox son bouton associé
     */
    public AntiAliasingChange(ConfigManager configManager, JCheckBox AACheckBox) {
        this.configManager = configManager;
        this.AACheckBox = AACheckBox;
    }

    /**
     * coche ou décoche la case pour indiquer si on veut de l'AA
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        configManager.setAA(AACheckBox.isSelected());
    }
}
