package fr.iutfbleau.SAE31_2024_LTA.jeux.controller;

import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelJeux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe {@code CentrerAction} implémente un {@link ActionListener} qui,
 * lorsqu'un événement d'action est déclenché, demande au modèle de jeu
 * de centrer la vue du jeu.
 */
public class CentrerAction implements ActionListener {
    private final ModelJeux modelJeux;

    /**
     * Constructeur de la classe {@code CentrerAction}.
     *
     * @param modelJeux le modèle de jeu associé à cette action
     */
    public CentrerAction(ModelJeux modelJeux) {
        this.modelJeux = modelJeux;
    }

    /**
     * Gère l'événement d'action. Cette méthode est appelée lorsque l'action
     * est déclenchée. Elle appelle la méthode {@code centrer()} de la vue
     * du modèle de jeu.
     *
     * @param e l'événement d'action contenant des informations sur l'action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        modelJeux.getVueJeux().centrer();
    }
}