package fr.iutfbleau.SAE31_2024_LTA.layers;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.popup.ControllerInputMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

/**
 * La classe VuePrincipale représente la fenêtre principale de l'application.
 * Elle hérite de JFrame et contient un PrincipaleLayeredPane pour gérer
 * les différents panneaux de l'application.
 */
public class VuePrincipale extends JFrame {

    private final PrincipaleLayeredPane principaleLayeredPane;
    private final ModelPrincipale modelPrincipale;

    public static int frameWidth;
    public static int frameHeight;

    public static int screenWidth;
    public static int screenHeight;

    /**
     * Constructeur de la classe VuePrincipale.
     *
     * @param modelPrincipale Le modèle principal de l'application.
     */
    public VuePrincipale(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
        setTitle("DorfRomantique");

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize);
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        frameWidth = this.getSize().width;
        frameHeight = this.getSize().height;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));
        setResizable(true);

        principaleLayeredPane = new PrincipaleLayeredPane(this);
        this.add(principaleLayeredPane);
        this.getPrincipaleLayeredPane().getMainPanel().setSize(this.getWidth(), this.getHeight());

        // Chargement du logo
        try {
            URL logoUrl = getClass().getResource("/Images/logo.png");
            if (logoUrl != null) {
                setIconImage(ImageIO.read(logoUrl));
            } else {
                System.err.println("Logo non trouvé : /Images/Logo.png");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du logo : " + e);
        }

        // Écouteur pour redimensionner la fenêtre
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateSize();
                modelPrincipale.getControllerPopup().updatePopup();
            }
        });
    }

    /**
     * Met à jour les dimensions de la fenêtre et du panneau principal.
     */
    public void updateSize() {
        frameWidth = this.getWidth();
        frameHeight = this.getHeight();
        this.getPrincipaleLayeredPane().getMainPanel().setSize(this.getWidth(), this.getHeight());
    }

    /**
     * Retourne le PrincipaleLayeredPane contenu dans cette fenêtre.
     *
     * @return le PrincipaleLayeredPane de la fenêtre.
     */
    public PrincipaleLayeredPane getPrincipaleLayeredPane() {
        return this.principaleLayeredPane;
    }

    /**
     * Retourne le modèle principal de l'application.
     *
     * @return le modèle principal de l'application.
     */
    public ModelPrincipale getModelPrincipale() {
        return modelPrincipale;
    }

    /**
     * Configure les mappages d'actions pour les événements de touche.
     */
    public void setActionMap() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "toggleSettings");
        actionMap.put("toggleSettings", new ControllerInputMap(modelPrincipale, "toggleSettingsAction"));

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "undo");
        actionMap.put("undo", new ControllerInputMap(modelPrincipale, "undo"));
    }
}
