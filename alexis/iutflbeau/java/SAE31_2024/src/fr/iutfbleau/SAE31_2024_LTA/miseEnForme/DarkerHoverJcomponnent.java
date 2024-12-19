package fr.iutfbleau.SAE31_2024_LTA.miseEnForme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * DarkerHoverJComponent est une classe qui modifie l'apparence d'un
 * composant graphique , Lorsque la souris entre dans le composant, son fond devient plus sombre,
 * et lorsqu'elle en sort, le fond redevient plus claire.
 */
public class DarkerHoverJcomponnent extends MouseAdapter{
    JComponent jComponnent;
    DarkerHoverJcomponnent(JComponent jComponnent){
        this.jComponnent = jComponnent;
    }

    /**
     * Lorsque la souris entre dans le composant change la couleur de fond du composant pour une version plus sombre .
     *
     * @param e L'événement de souris qui a déclenché cette méthode.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        Color ancienBG = jComponnent.getBackground();
        jComponnent.setBackground(ancienBG.darker());
    }
    /**
     * Lorsque la souris sort du composant, la couleur de fond du composant change pour une version plus claire .
     *
     * @param e L'événement de souris qui a déclenché cette méthode.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        Color brighterBG =  jComponnent.getBackground();
        jComponnent.setBackground(brighterBG.brighter());
    }
}
