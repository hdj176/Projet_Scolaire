package fr.iutfbleau.SAE31_2024_LTA.jeux.vue;

import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelTuile;

import javax.swing.*;
import java.awt.*;

/**
 * La classe VueTuile représente une tuile dans l'interface graphique du jeu.
 * Elle est responsable de l'affichage visuel de la tuile et de son état,
 * ainsi que de la gestion des interactions graphiques.
 */
public class VueTuile extends JComponent {

    private final ModelTuile modelTuile; // Modèle de la tuile associée

    private Polygon polygon; // Polygone représentant la forme de la tuile

    private final int[] xPoints; // Coordonnées X des sommets du polygone
    private final int[] yPoints; // Coordonnées Y des sommets du polygone

    private int centerX, centerY; // Centre de la tuile

    private boolean AA; // Indique si l'anticrénelage est activé

    /**
     * Constructeur de VueTuile.
     *
     * @param modelTuile Le modèle de la tuile associée.
     * @param centerX La coordonnée X du centre de la tuile.
     * @param centerY La coordonnée Y du centre de la tuile.
     * @param radius Le rayon de la tuile (utilisé pour définir la taille du polygone).
     * @param AA Indique si l'anticrénelage doit être activé.
     */
    public VueTuile(ModelTuile modelTuile, int centerX, int centerY, int radius, boolean AA) {
        this.modelTuile = modelTuile;
        this.AA = AA;
        this.xPoints = new int[6];
        this.yPoints = new int[6];

        updateTuile(centerX, centerY, radius, AA);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Récupère le modèle de la tuile associée.
     *
     * @return Le modèle de la tuile.
     */
    public ModelTuile getModelTuile() {
        return modelTuile;
    }

    /**
     * Crée un hexagone basé sur le rayon spécifié.
     *
     * @param radius Le rayon de l'hexagone.
     * @return Un objet Polygon représentant l'hexagone.
     */
    private Polygon createHexagon(int radius) {
        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (radius + radius * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (radius + radius * Math.sin(i * Math.PI / 3));
        }

        this.centerX = (xPoints[0] + xPoints[1] + xPoints[2] +
                xPoints[3] + xPoints[4] + xPoints[5]) / 6;

        this.centerY = (yPoints[0] + yPoints[1] + yPoints[2] +
                yPoints[3] + yPoints[4] + yPoints[5]) / 6;

        return new Polygon(xPoints, yPoints, 6);
    }

    /**
     * Met à jour la tuile en définissant sa position et sa taille.
     *
     * @param centerX La coordonnée X du nouveau centre de la tuile.
     * @param centerY La coordonnée Y du nouveau centre de la tuile.
     * @param radius Le nouveau rayon de la tuile.
     * @param AA Indique si l'anticrénelage doit être activé.
     */
    public void updateTuile(int centerX, int centerY, int radius, boolean AA) {
        polygon = createHexagon(radius);
        this.AA = AA;
        this.setBounds(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    /**
     * Dessine la tuile sur le composant graphique.
     *
     * @param g L'objet Graphics utilisé pour dessiner la tuile.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (AA) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (modelTuile.isSuivante()) {
            g2d.scale(1, 0.5);
            g2d.setColor(new Color(0x333333));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(polygon.xpoints[0], polygon.ypoints[0], polygon.xpoints[1], polygon.ypoints[1]);
            g2d.drawLine(polygon.xpoints[1], polygon.ypoints[1], polygon.xpoints[2], polygon.ypoints[2]);
            g2d.drawLine(polygon.xpoints[2], polygon.ypoints[2], polygon.xpoints[3], polygon.ypoints[3]);
        }

        if (!modelTuile.isButton()) {
            int[] composition = modelTuile.getComposition();
            Color[] colorPalette = new Color[6];

            if (!modelTuile.isPreview()) {
                colorPalette[0] = new Color(30, 142, 216);
                colorPalette[1] = new Color(119, 119, 119);
                colorPalette[2] = new Color(235, 222, 33);
                colorPalette[3] = new Color(119, 198, 119);
                colorPalette[4] = new Color(20, 119, 69);

                // Dessiner les tuiles avec leurs compositions
                for (int i = 0; i < 6; i++) {
                    int[] xPoints = {
                            polygon.xpoints[i],
                            polygon.xpoints[(i + 1) % 6],
                            centerX
                    };
                    int[] yPoints = {
                            polygon.ypoints[i],
                            polygon.ypoints[(i + 1) % 6],
                            centerY
                    };

                    g2d.setColor(colorPalette[composition[i]]);
                    g2d.fillPolygon(xPoints, yPoints, 3);
                }

                g2d.setColor(new Color(64, 64, 64, 226));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawPolygon(polygon);
            } else {
                g2d.scale(1, 0.5); // Effet 3D
                g2d.setColor(new Color(0x333333));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(polygon.xpoints[0], polygon.ypoints[0], polygon.xpoints[1], polygon.ypoints[1]);
                g2d.drawLine(polygon.xpoints[1], polygon.ypoints[1], polygon.xpoints[2], polygon.ypoints[2]);
                g2d.drawLine(polygon.xpoints[2], polygon.ypoints[2], polygon.xpoints[3], polygon.ypoints[3]);

                g2d.setColor(new Color(124, 124, 124));
                g2d.fillPolygon(polygon);
            }

            g2d.setColor(new Color(51, 51, 51));
            g2d.drawPolygon(polygon);
            if (modelTuile.isOnBoard()) {
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(polygon.xpoints[0], polygon.ypoints[0], polygon.xpoints[1], polygon.ypoints[1]);
                g2d.drawLine(polygon.xpoints[1], polygon.ypoints[1], polygon.xpoints[2], polygon.ypoints[2]);
                g2d.drawLine(polygon.xpoints[2], polygon.ypoints[2], polygon.xpoints[3], polygon.ypoints[3]);
            }
        } else {
            g2d.setColor(new Color(151, 151, 151));
            g2d.fillPolygon(polygon);
        }
    }
}
