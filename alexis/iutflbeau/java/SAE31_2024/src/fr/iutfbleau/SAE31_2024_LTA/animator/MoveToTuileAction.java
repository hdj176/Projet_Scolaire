package fr.iutfbleau.SAE31_2024_LTA.animator;

import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueJeux;
import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueTuile;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static fr.iutfbleau.SAE31_2024_LTA.animator.Animator.easeInOut;

/**
 * Classe interne pour gérer l'animation de déplacement pour une VueTuile.
 */
public class MoveToTuileAction implements ActionListener {
    private final VueTuile vueTuile;
    private final int startX, startY, duration;
    private final VueJeux vueJeux;
    private final Timer timer;
    private final long startTime;

    /**
     * Constructeur de la classe.
     *
     * @param vueTuile la vue de la tuile à déplacer
     * @param startX la position x de départ
     * @param startY la position y de départ
     * @param duration la durée de l'animation
     * @param vueJeux la vue du jeu
     * @param timer le timer pour l'animation
     * @param startTime le temps de départ
     */
    public MoveToTuileAction(VueTuile vueTuile, int startX, int startY, int duration, VueJeux vueJeux, Timer timer, long startTime) {
        this.vueTuile = vueTuile;
        this.startX = startX;
        this.startY = startY;
        this.duration = duration;
        this.vueJeux = vueJeux;
        this.timer = timer;
        this.startTime = startTime;
    }

    /**
     * Méthode pour gérer l'action de l'animation.
     *
     * @param e l'événement
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        vueJeux.getModelJeux().setUndoActivate(false);
        long elapsed = System.currentTimeMillis() - startTime;
        double progress = Math.min(1.0, (double) elapsed / duration);
        double easedProgress = easeInOut(progress);

        int centerX = VuePrincipale.frameWidth / 2;
        int centerY = VuePrincipale.frameHeight / 2;
        int initialOffsetX = centerX;
        int initialOffsetY = centerY - 43;
        int totalOffsetX = initialOffsetX + vueJeux.getOffsetX();
        int totalOffsetY = initialOffsetY + vueJeux.getOffsetY();

        int col = vueTuile.getModelTuile().getX();
        int row = vueTuile.getModelTuile().getY();

        int x = (totalOffsetX + col * (3 * 50 / 2)) - 40;
        int y = (totalOffsetY + row * 43) - 10;

        int newX = (int) (startX + easedProgress * (x - startX));
        int newY = (int) (startY + easedProgress * (y - startY));

        vueTuile.setBounds(newX, newY, vueTuile.getWidth(), vueTuile.getHeight());
        vueTuile.repaint();

        if (progress >= 1.0) {
            timer.stop();
            vueTuile.getModelTuile().setOnBoard(true);
            vueJeux.getModelJeux().setUndoActivate(true);
        }
    }
}