package fr.iutfbleau.SAE31_2024_LTA.loading;

import fr.iutfbleau.SAE31_2024_LTA.ControlerMain;
import fr.iutfbleau.SAE31_2024_LTA.endGame.QuitAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleButton;
import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.setStyleImageTitre;

/**
 * La classe LoadingFrame représente une fenêtre de chargement.
 * Elle affiche une barre de progression et un bouton pour annuler l'opération en cours.
 */
public class LoadingFrame extends JFrame {

    /**
     * Constructeur de la classe LoadingFrame.
     * Initialise la fenêtre avec une taille, des composants et un style.
     */
    public LoadingFrame() {
        this.setSize(400, 350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ne pas quitter le programme
        this.setLayout(null);

        // Bouton d'annulation
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBounds(100, 220, 200, 50);
        cancelButton.addActionListener(new QuitAction());
        this.add(setStyleButton(cancelButton, 28));

        // Barre de progression
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(20, this.getHeight() - 70, 360, 50);
        this.add(progressBar);

        // Configuration de la fenêtre
        this.setBackground(new Color(0x787878, false));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Chargement de l'icône
        try {
            URL logoUrl = ControlerMain.class.getResource("/Images/logo.png");
            if (logoUrl != null) {
                this.setIconImage(ImageIO.read(logoUrl));
            } else {
                System.err.println("Logo non trouvé : /Images/Logo.png");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du logo : " + e);
        }

        // Titre
        JLabel titre = setStyleImageTitre();
        titre.setBounds(50, 10, 300, 200);
        this.add(titre);
        this.repaint();
    }
}
