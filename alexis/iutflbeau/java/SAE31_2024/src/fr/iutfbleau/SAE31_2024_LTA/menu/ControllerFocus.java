package fr.iutfbleau.SAE31_2024_LTA.menu;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * ControllerFocus gère les événements de focus pour un JPanel et
 * interagit avec le ModelPrincipale pour jouer un son.
 */
public class ControllerFocus implements FocusListener {
    private final JPanel panel;
    private final ModelPrincipale modelPrincipale;

    /**
     * Crée un nouveau ControllerFocus.
     *
     * @param panel Le JPanel associé à ce contrôleur.
     * @param modelPrincipale Le modèle principal de l'application.
     */
    public ControllerFocus(JPanel panel, ModelPrincipale modelPrincipale) {
        this.panel = panel;
        this.modelPrincipale = modelPrincipale;
    }
    /**
     * Méthode appelée lorsque le composant obtient le focus.
     * Joue un son de clic et efface le texte si celui-ci correspond
     * au nom du joueur configuré.
     *
     * @param e L'événement de focus.
     */
    @Override
    public void focusGained(FocusEvent e) {
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClicAudioClip(), false);

        if (e.getSource() instanceof JTextField) {
            JTextField playerNameInput = (JTextField) e.getComponent();
            if (playerNameInput.getText().equals(modelPrincipale.getConfigManager().getPlayerName())) {
                playerNameInput.setText("");
                playerNameInput.setForeground(Color.BLACK);
            }

        }
        panel.repaint();
    }
    /**
     * Méthode appelée lorsque le composant perd le focus.
     * Réinitialise le texte du champ si celui-ci est vide.
     *
     * @param e L'événement de focus.
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() instanceof JTextField) {
            JTextField playerNameInput = (JTextField) e.getComponent();
            if (playerNameInput.getText().isEmpty()) {
                playerNameInput.setForeground(Color.GRAY);
                playerNameInput.setText(modelPrincipale.getConfigManager().getPlayerName());
            }

        }
        panel.repaint();
    }
}
