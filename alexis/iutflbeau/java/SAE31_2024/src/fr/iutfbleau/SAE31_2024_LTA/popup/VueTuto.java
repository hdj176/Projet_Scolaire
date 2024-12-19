package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;
import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;
import fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleCheckBox;
import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleLabelScore;


/**
 * Vue pour afficher le tutoriel à l'utilisateur.
 * Affiche une image de tutoriel, une option pour montrer le tutoriel au démarrage, et un bouton de reprise.
 */
public class VueTuto extends JLayeredPane {
    private final JCheckBox showAtStartupCheckBox;

    /**
     * Constructeur de VueTuto.
     *
     * @param controllerPopup le contrôleur de pop-up.
     * @param configManager   le gestionnaire de configuration pour gérer les préférences de l'utilisateur.
     */
    VueTuto(ControllerPopup controllerPopup, ConfigManager configManager) {
        setLayout(null);
        this.setBackground(StyleComponent.getPopupColor());
        setSize(700,430);

        JLabel tutoLabel = new JLabel("Tutoriel");
        tutoLabel.setBounds((getWidth()-190)/2,20,190,50);
        add(setStyleLabelScore(tutoLabel,24),Integer.valueOf(1));

        this.add(createImageTuto(700,430),Integer.valueOf(0));

        showAtStartupCheckBox = setStyleCheckBox(new JCheckBox("Montrer au démarrage", configManager.isTuto()));
        showAtStartupCheckBox.setBounds(60, this.getHeight()-60, 220, 50);
        showAtStartupCheckBox.addActionListener(new ControllerTutoStartUp(configManager, showAtStartupCheckBox));
        add(showAtStartupCheckBox,Integer.valueOf(1));

        JButton resumeButton = StyleComponent.setStyleButton(new JButton("Resume"),18);
        resumeButton.setBounds(this.getWidth()-240, this.getHeight()-60, 180, 50);
        resumeButton.addActionListener(new ControllerResumeListener(controllerPopup,1));
        add(resumeButton ,Integer.valueOf(1));

        updateVueTuto(controllerPopup.getVuePrincipale());
    }

    /**
     * Crée une image redimensionné pour le tutoriel.
     *
     * @param maxWidth  largeur maximale de l'image.
     * @param maxHeight hauteur maximale de l'image.
     * @return un JLabel contenant l'image redimensionnée du tutoriel.
     */
    private JLabel createImageTuto(int maxWidth, int maxHeight) {
        ImageIcon tutoIcon = new ImageIcon(Objects.requireNonNull(StyleComponent.class.getResource("/Images/tuto.png")));
        Image image = tutoIcon.getImage();

        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        Image resizedImage = image.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        logoLabel.setBounds(0, 0, newWidth, newHeight);
        return logoLabel;
    }

    /**
     * Met à jour la position de la vue tutoriel pour la centrer dans la fenêtre principale.
     *
     * @param vuePrincipale la vue principale de l'application.
     */
    public void updateVueTuto(VuePrincipale vuePrincipale) {
        this.setBounds((vuePrincipale.getWidth()-this.getWidth())/2,-this.getHeight(),getWidth(),getHeight());
    }
}
