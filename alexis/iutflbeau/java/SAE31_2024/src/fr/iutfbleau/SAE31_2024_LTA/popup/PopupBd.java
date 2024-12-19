package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;
import fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent;

import javax.swing.*;
import java.awt.*;


/**
 * Classe représentant une fenêtre contextuelle d'affichage de messages.
 * Cette classe est utilisée pour afficher des messages de succès ou d'erreur, en lien avec les connexions à la Bdd.
 */
public class PopupBd extends JPanel {

    /**
     * Constructeur pour créer une fenêtre contextuelle avec un message spécifique.
     *
     * @param message le message à afficher dans la fenêtre contextuelle.
     * @param good    un indicateur de succès ; true pour un message de succès, false pour un message d'erreur.
     */
    public PopupBd(String message,Boolean good){
        setLayout(null);
        setOpaque(false);
        setBackground(new Color(255,255,255,0));

        setBounds((VuePrincipale.frameWidth - 600)/2,30, 600, 50);

        JLabel label = new JLabel();
        label.setSize(new Dimension(600,50));
        label.setBounds(0 ,0, 600, 50);

        label.setText(message);

        if (good){
            this.add(StyleComponent.setStyleLabelSucces(label,24));
        }else {
            this.add(StyleComponent.setStyleLabelErreur(label,24));
        }

        repaint();
    }
}
