package fr.iutfbleau.SAE31_2024_LTA.jeux.controller;

import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelJeux;
import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelTuile;
import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueTuile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * La classe {@code ControllerPoseTuile} gère les événements de la souris
 * liés à la pose des tuiles dans le jeu.
 * Elle permet d'interagir avec les tuiles affichées et d'appliquer des actions
 * comme poser une tuile, annuler une pose, et afficher un aperçu des tuiles.
 */
public class ControllerPoseTuile implements MouseListener {

    private final ModelJeux modelJeux;
    private ModelTuile modeleTuilePreviewed;
    private boolean clicked = false;

    /**
     * Constructeur de la classe {@code ControllerPoseTuile}.
     *
     * @param modelJeux le modèle de jeu qui gère la logique des tuiles
     */
    public ControllerPoseTuile(ModelJeux modelJeux) {
        this.modelJeux = modelJeux;
    }

    /**
     * Gère l'événement de clic de souris. Actuellement, cette méthode est vide.
     *
     * @param e l'événement de clic de souris
     */
    @Override
    public void mouseClicked(MouseEvent e) { }

    /**
     * Gère l'événement lorsque le bouton de la souris est enfoncé.
     *
     * @param e l'événement de pression de souris
     */
    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }

    /**
     * Gère l'événement lorsque le bouton de la souris est relâché.
     * Vérifie si une tuile peut être posée ou si une action d'annulation est demandée.
     *
     * @param e l'événement de relâchement de souris
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (clicked) {
            Object source = e.getSource();

            if (modelJeux.getListTuiles().isEmpty()) {
                modelJeux.setUndo(false);
                modelJeux.setUndoActivate(false);
            }
            modelJeux.getVueJeux().unsetPreviewOnButton(modeleTuilePreviewed);
            if (source instanceof VueTuile btnHovered) {
                if (e.getButton() == MouseEvent.BUTTON1 && !modelJeux.getListTuiles().isEmpty()) {
                    modelJeux.playTuileSound(modelJeux.getListTuiles().getFirst().getSoundIndex());
                    modelJeux.getModelMatrice().deleteTuile(btnHovered.getModelTuile());
                    modelJeux.getModelMatrice().poseTuile(btnHovered.getModelTuile().getX(), btnHovered.getModelTuile().getY());
                    modelJeux.getVueJeux().updatePlayerInfo();
                    modelJeux.getVueJeux().updatePreviewTuileList();
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3 && modelJeux.isUndoActivate()) {
                modelJeux.undoLastTuile();
                modelJeux.getVueJeux().updatePlayerInfo();
            }
            clicked = false;
        }
    }

    /**
     * Gère l'événement lorsque la souris entre dans un composant.
     * Affiche un aperçu de la tuile sur laquelle la souris est actuellement positionnée.
     *
     * @param e l'événement d'entrée de souris
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        clicked = true;

        Object source = e.getSource();
        if (source instanceof VueTuile btnHovered) {
            modeleTuilePreviewed = modelJeux.getVueJeux().setPreviewOnButton(btnHovered);
        }
    }

    /**
     * Gère l'événement lorsque la souris quitte un composant.
     * Supprime l'aperçu de la tuile.
     *
     * @param e l'événement de sortie de souris
     */
    @Override
    public void mouseExited(MouseEvent e) {
        clicked = false;
        modelJeux.getVueJeux().unsetPreviewOnButton(modeleTuilePreviewed);
    }
}
