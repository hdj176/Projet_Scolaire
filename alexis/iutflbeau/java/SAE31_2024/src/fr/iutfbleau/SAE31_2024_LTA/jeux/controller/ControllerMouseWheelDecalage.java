package fr.iutfbleau.SAE31_2024_LTA.jeux.controller;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * La classe {@code ControllerMouseWheelDecalage} implémente un écouteur
 * de la molette de la souris pour gérer le décalage des tuiles dans le jeu.
 * Elle est responsable de la manipulation des tuiles lorsque l'utilisateur
 * fait défiler la molette de la souris.
 */
public class ControllerMouseWheelDecalage implements MouseWheelListener {
    private final ModelPrincipale modelPrincipale;

    /**
     * Constructeur de la classe {@code ControllerMouseWheelDecalage}.
     *
     * @param modelPrincipale le modèle principal du jeu
     */
    public ControllerMouseWheelDecalage(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
    }

    /**
     * Gère l'événement de mouvement de la molette de la souris. Cette méthode
     * est appelée lorsque l'utilisateur fait défiler la molette. Elle effectue
     * un décalage des tuiles en fonction de la direction du défilement.
     *
     * @param e l'événement de la molette de la souris contenant des informations
     *          sur l'action
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int wheelRotation = e.getWheelRotation();
        if (!modelPrincipale.getModelJeux().getListTuiles().isEmpty()) {
            modelPrincipale.getModelJeux().getListTuiles().getFirst().decalage(wheelRotation, modelPrincipale.getModelJeux().getVueJeux());
        }
    }
}