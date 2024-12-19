package fr.iutfbleau.SAE31_2024_LTA.animator;

import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueJeux;
import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueTuile;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe utilitaire pour gérer les animations de déplacement d'éléments dans l'interface graphique.
 */
public class Animator {

    /**
     * Anime le déplacement d'un composant JComponent d'une position de départ à une position finale.
     *
     * @param panel Le composant à déplacer
     * @param startX La position de départ en X
     * @param startY La position de départ en Y
     * @param endX La position finale en X
     * @param endY La position finale en Y
     * @param duration La durée de l'animation en millisecondes
     * @param amortie Indique si l'animation doit être adoucie
     */
    public static void moveTo(JComponent panel, int startX, int startY, int endX, int endY, int duration, boolean amortie) {
        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();
        panel.setLocation(startX, startY);
        timer.addActionListener(new MoveToAction(panel, startX, startY, endX, endY, duration, amortie, timer, startTime));
        timer.start();
    }

    /**
     * Anime le déplacement d'une tuile vers une position sur le plateau de jeu.
     *
     * @param vueTuile La vue de la tuile à déplacer
     * @param startX La position de départ en X
     * @param startY La position de départ en Y
     * @param duration La durée de l'animation en millisecondes
     * @param vueJeux La vue du plateau de jeu
     */
    public static void moveToTuile(VueTuile vueTuile, int startX, int startY, int duration, VueJeux vueJeux) {
        Timer timer = new Timer(10, null);
        final long startTime = System.currentTimeMillis();
        vueTuile.setLocation(startX, startY);
        timer.addActionListener(new MoveToTuileAction(vueTuile, startX, startY, duration, vueJeux, timer, startTime));
        timer.start();
    }

    /**
     * Fonction d'interpolation pour adoucir les animations.
     *
     * @param t La progression de l'animation
     * @return La progression adoucie
     */
    static double easeInOut(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }

    /**
     * Classe interne pour gérer l'animation de déplacement pour une VueTuile.
     */
    private static class MoveToAction implements ActionListener {
        private final JComponent panel;
        private final int startX, startY, endX, endY, duration;
        private final boolean amortie;
        private final Timer timer;
        private final long startTime;

        public MoveToAction(JComponent panel, int startX, int startY, int endX, int endY, int duration, boolean amortie, Timer timer, long startTime) {
            this.panel = panel;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.duration = duration;
            this.amortie = amortie;
            this.timer = timer;
            this.startTime = startTime;
        }

        /**
         * Méthode appelée à chaque tick du Timer pour mettre à jour la position du composant.
         *
         * @param e L'événement d'action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = Math.min(1.0, (double) elapsed / duration);
            double easedProgress = amortie ? easeInOut(progress) : progress;

            int newX = (int) (startX + easedProgress * (endX - startX));
            int newY = (int) (startY + easedProgress * (endY - startY));

            panel.setBounds(newX, newY, panel.getWidth(), panel.getHeight());
            panel.repaint();

            if (progress >= 1.0) {
                timer.stop();
            }
        }
    }
}

