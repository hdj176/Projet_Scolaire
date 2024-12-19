package fr.iutfbleau.SAE31_2024_LTA.jeux.controller;

import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueJeux;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Cursor.MOVE_CURSOR;

/**
 * La classe {@code Controller2D} est un contrôleur qui gère l'interaction de
 * la souris avec la vue du jeu. Elle permet le déplacement de la vue en
 * utilisant des événements de glisser-déposer.
 */
public class Controller2D extends MouseAdapter {
    private final VueJeux vueJeux;
    private boolean dragging = false;
    private int startX;
    private int startY;

    /**
     * Constructeur de la classe {@code Controller2D}.
     *
     * @param vueJeux la vue du jeu à laquelle ce contrôleur est associé
     */
    public Controller2D(VueJeux vueJeux) {
        this.vueJeux = vueJeux;
        vueJeux.addMouseListener(this);
        vueJeux.addMouseMotionListener(this);
    }

    /**
     * Gère l'événement de pression de la souris. Cette méthode est appelée
     * lorsque l'utilisateur appuie sur le bouton gauche de la souris. Elle
     * initialise le déplacement en mode glisser.
     *
     * @param e l'événement de souris contenant des informations sur l'action
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            dragging = true;
            startX = e.getX();
            startY = e.getY();
            vueJeux.setCursor(new Cursor(MOVE_CURSOR));
        }
        vueJeux.repaint();
    }

    /**
     * Gère l'événement de glissement de la souris. Cette méthode est appelée
     * lorsque l'utilisateur fait glisser la souris tout en maintenant le
     * bouton gauche enfoncé. Elle met à jour les décalages de la vue en
     * fonction de la distance parcourue.
     *
     * @param e l'événement de souris contenant des informations sur l'action
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            int currentX = e.getX();
            int currentY = e.getY();
            int deltaX = currentX - startX;
            int deltaY = currentY - startY;

            vueJeux.updateOffsets(deltaX, deltaY);

            startX = currentX;
            startY = currentY;
        }
        vueJeux.repaint();
    }

    /**
     * Gère l'événement de relâchement de la souris. Cette méthode est appelée
     * lorsque l'utilisateur relâche le bouton gauche de la souris. Elle met
     * fin au déplacement.
     *
     * @param e l'événement de souris contenant des informations sur l'action
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            dragging = false;
            vueJeux.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}