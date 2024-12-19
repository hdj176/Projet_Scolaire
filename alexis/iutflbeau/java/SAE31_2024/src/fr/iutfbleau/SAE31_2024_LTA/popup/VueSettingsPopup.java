package fr.iutfbleau.SAE31_2024_LTA.popup;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;
import fr.iutfbleau.SAE31_2024_LTA.menu.ControllerMenuCard;
import fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent;

import javax.swing.*;
import java.awt.*;

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.*;


/**
 * Cette classe représente la vue des paramètres du jeu,gère l'affichage des paramètres de volume de musique,
 * des effets sonores, et des options de configuration supplémentaires comme l'anti-aliasing.
 */
public class VueSettingsPopup extends JPanel {
    private final JSlider musicVolumeSlider;
    private final JSlider effectsVolumeSlider;
    private final ModelPrincipale modelPrincipale;
    private boolean open = false;
    private JCheckBox AACheckBox;

    /**
     * Constructeur pour créer la vue des paramètres.
     *
     * @param controllerPopup le contrôleur de la fenêtre contextuelle.
     * @param modelPrincipale le modèle principal qui contient la logique de l'application.
     */
    public VueSettingsPopup(ControllerPopup controllerPopup, ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
        ConfigManager configManager = modelPrincipale.getConfigManager();
        setLayout(null);
        setOpaque(false);
        this.setSize(700,430);
        this.setBackground(StyleComponent.getPopupColor());
        this.updateVueSettings();

        JLabel settingsLabel = new JLabel("Paramètres");
        settingsLabel.setBounds((getWidth()-190)/2,20,190,50);
        add(StyleComponent.setStyleLabelWhite(settingsLabel,24));

        musicVolumeSlider = new JSlider(40, 100, configManager.getVolumeMusique());
        musicVolumeSlider.setBounds(250, 100, 350, 40);
        add(setStyleSlider(musicVolumeSlider));

        effectsVolumeSlider = new JSlider(40, 100, configManager.getVolumeEffet());
        effectsVolumeSlider.setBounds(250, 200, 350, 40);
        add(setStyleSlider(effectsVolumeSlider));

        JLabel musicLabel = new JLabel("Musique Volume:");
        musicLabel.setBounds(30, 100, 200, 40);
        add(setStyleLabelWhite(musicLabel,19));

        JLabel effectsLabel = new JLabel("Effets Volume:");
        effectsLabel.setBounds(30, 200, 200, 40);
        add(setStyleLabelWhite(effectsLabel,19));

        musicVolumeSlider.addChangeListener(new ControllerVolumeChange(configManager, 0,modelPrincipale));
        effectsVolumeSlider.addChangeListener(new ControllerVolumeChange(configManager, 1,modelPrincipale));

        AACheckBox = new JCheckBox("Anti-Aliasing", configManager.isAA());
        setStyleCheckBox(AACheckBox);
        AACheckBox.setBounds(20, this.getHeight()-140, 220, 50);
        AACheckBox.addActionListener(new AntiAliasingChange(configManager, AACheckBox));
        add(AACheckBox);

        JButton tutoButton = new JButton("Tutoriel");
        tutoButton.setBounds(20, 350, 200, 50);
        tutoButton.addActionListener(new ControllerTutoListener(controllerPopup));
        add(setStyleButton(tutoButton,18));

        JButton resumeButton = new JButton("Resume");
        resumeButton.setBounds(240, 350, 200, 50);
        resumeButton.addActionListener(new ControllerResumeListener(controllerPopup,0));
        add(setStyleButton(resumeButton,18));

        JButton menuButton = new JButton("Menu");
        menuButton.setBounds(460, 350, 200, 50);
        menuButton.addActionListener(new ControllerMenuBListener(controllerPopup, modelPrincipale));
        add(setStyleButton(menuButton,18));
    }

    /**
     * Met à jour la position de la vue des paramètres dans la fenêtre.
     */
    public void updateVueSettings(){
        if (!open){
            this.setBounds((modelPrincipale.getVuePrincipale().getWidth()-this.getWidth())/2,-this.getHeight(),getWidth(),getHeight());
        }else {
            this.setBounds((modelPrincipale.getVuePrincipale().getWidth()-this.getWidth())/2, (modelPrincipale.getVuePrincipale().getHeight()-this.getHeight())/2,getWidth(),getHeight());
        }
    }

    /**
     * Méthode pour dessiner.

     * @param g l'objet Graphics utilisé pour dessiner le composant.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(45, 45, 45, 118);
        Color color2 = new Color(99, 99, 99,100);
        int width = getWidth();
        int height = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }

    /**
     * Définit le volume de la musique.
     *
     * @param volume le niveau de volume à définir.
     */
    public void setMusicVolume(int volume) {
        musicVolumeSlider.setValue(volume);
    }

    /**
     * Définit le volume des effets.
     *
     * @param volume le niveau de volume à définir.
     */
    public void setEffectsVolume(int volume) {
        effectsVolumeSlider.setValue(volume);
    }

    /**
     * Indique si la vue des paramètres est ouverte.
     *
     * @return true si la vue est ouverte, sinon false.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Définit l'état ouvert ou fermé de la vue des paramètres.
     *
     * @param open true pour ouvrir la vue, sinon false pour la fermer.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }
}

