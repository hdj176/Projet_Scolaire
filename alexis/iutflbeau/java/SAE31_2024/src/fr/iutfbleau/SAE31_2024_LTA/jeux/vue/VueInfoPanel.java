package fr.iutfbleau.SAE31_2024_LTA.jeux.vue;

import fr.iutfbleau.SAE31_2024_LTA.jeux.controller.CentrerAction;
import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelJeux;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;

import javax.swing.*;
import java.awt.*;

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleButtonInGame;
import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleLabelScore;

/**
 * La classe VueInfoPanel représente un panneau d'information dans l'interface du jeu,
 * affichant le score actuel du joueur, le meilleur score et un bouton pour centrer la vue.
 */
public class VueInfoPanel extends javax.swing.JPanel {

    private final JLabel currentScore; // Étiquette affichant le score actuel
    private JButton centrer; // Bouton pour centrer la vue

    /**
     * Constructeur de VueInfoPanel.
     *
     * @param modelJeux Le modèle du jeu, contenant les informations nécessaires pour afficher
     *                  les données du joueur et du score.
     */
    public VueInfoPanel(ModelJeux modelJeux) {
        this.setLayout(null);
        this.setBackground(new Color(40, 40, 40, 10));
        this.setBounds(0, 0, modelJeux.getModelPrincipale().getVuePrincipale().getWidth(), modelJeux.getModelPrincipale().getVuePrincipale().getHeight());
        this.setOpaque(false);

        JLabel playerNameLabel = new JLabel(modelJeux.getModelPrincipale().getPlayerName());
        playerNameLabel.setBounds(30, 30, 200, 50);
        this.add(setStyleLabelScore(playerNameLabel, 18));

        JLabel bestScoreLabel = new JLabel("Record " + modelJeux.getModelPrincipale().getModelPartieJouer().getVuePartieJouer().getControllerSearchPartieJouer().searchPartieOfPlayer(modelJeux.getModelPrincipale().getPlayerName(), modelJeux.getModelPrincipale().getSelectedSeed()) + " Points");
        bestScoreLabel.setBounds(250, 30, 250, 50);
        this.add(setStyleLabelScore(bestScoreLabel, 16));

        currentScore = new JLabel("Score " + modelJeux.getScore() + " Points");
        currentScore.setBounds(520, 30, 250, 50);
        this.add(setStyleLabelScore(currentScore, 18));

        centrer = new JButton("Centrer");
        centrer.addActionListener(new CentrerAction(modelJeux));
        centrer.setBounds((VuePrincipale.frameWidth / 2) - 200, 100, 200, 50);
        centrer = setStyleButtonInGame(centrer, 34);
    }

    /**
     * Récupère l'étiquette affichant le score actuel.
     *
     * @return L'étiquette du score actuel.
     */
    public JLabel getCurrentScore() {
        return currentScore;
    }

    /**
     * Récupère le bouton pour centrer la vue.
     *
     * @return Le bouton centrer.
     */
    public JButton getCentrerButton() {
        return centrer;
    }
}