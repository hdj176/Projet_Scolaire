package fr.iutfbleau.SAE31_2024_LTA.jeux.vue;

import fr.iutfbleau.SAE31_2024_LTA.animator.Animator;
import fr.iutfbleau.SAE31_2024_LTA.jeux.controller.Controller2D;
import fr.iutfbleau.SAE31_2024_LTA.jeux.controller.ControllerMouseWheelDecalage;
import fr.iutfbleau.SAE31_2024_LTA.jeux.controller.ControllerPoseTuile;
import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelJeux;
import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelTuile;

import javax.swing.*;
import java.awt.*;
import java.util.List; // Importer la bonne classe List
import java.util.ArrayList; // Importer ArrayList
import java.util.Map; // Pour les Map

import static fr.iutfbleau.SAE31_2024_LTA.miseEnForme.StyleComponent.*;


public class VueJeux extends JLayeredPane {

    private final ModelJeux modelJeux;
    private int offsetX = 0, offsetY = 0;
    int tuileCentreRow = 0;
    int tuileCentreCol = 0;

    private final int tuileSize = 50, hexHeight = tuileSize - 7;
    private final ModelTuile[] tuilePreview;
    private boolean end = false;
    private VueInfoPanel infoPanel;

    public VueJeux(ModelJeux modelJeux) {
        setLayout(null);
        new Controller2D(this);
        this.modelJeux = modelJeux;
        tuilePreview = new ModelTuile[modelJeux.getListTuiles().size()];
        createPlayerInfo();
        addMouseWheelListener(new ControllerMouseWheelDecalage(modelJeux.getModelPrincipale()));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (end) {
                    modelJeux.getVueScoreScreen().setBounds(getWidth() - 400, 100, 350, 600);
                } else {
                    updatePreviewTuileList();
                }
            }
        });

        createFirstTuile();
        this.addMouseListener(new ControllerPoseTuile(modelJeux));
        repaint();
    }

    public void centrer() {
        offsetX = 0;
        offsetY = 0;
        repaint();
    }

    private void createFirstTuile() {
        int x = (getWidth() / 2) - (3 * 50 / 2) * 50;
        int y = (getHeight() / 2) - 50 - 7 * 50;
        ModelTuile tuile = modelJeux.getListTuiles().getFirst();
        tuile.createVueTuile(x, y, tuileSize, modelJeux.isAA());
        add(tuile.getVueTuile());
        modelJeux.getModelMatrice().poseTuile(0, 0);
        modelJeux.getModelMatrice().getTuilesPartie().get(new Point(0,0)).setOnBoard(true);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.renderTuiles();
        if (modelJeux.getListTuiles().isEmpty() && !end) {
            endGame();
        }
        this.showCentrer();
    }

    private void showCentrer(){
        if (offsetX > modelJeux.getModelPrincipale().getVuePrincipale().getWidth()/2 || offsetY > modelJeux.getModelPrincipale().getVuePrincipale().getHeight()/2 || offsetX < -modelJeux.getModelPrincipale().getVuePrincipale().getWidth()/2 || offsetY < -modelJeux.getModelPrincipale().getVuePrincipale().getHeight()/2){
            if (infoPanel.getComponentCount() == 3) {
                infoPanel.add(setStyleButtonInGame(infoPanel.getCentrerButton(), 34));
            }
        }else if (infoPanel.getComponentCount() == 4) {
            infoPanel.remove(infoPanel.getCentrerButton());
        }
    }

    private void renderTuiles() {
        Map<Point, ModelTuile> tuiles = modelJeux.getModelMatrice().getTuilesPartie();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int initialOffsetX = centerX - (3 * tuileSize / 2) * tuileCentreCol;
        int initialOffsetY = centerY - hexHeight * tuileCentreRow;

        int totalOffsetX = initialOffsetX + offsetX;
        int totalOffsetY = initialOffsetY + offsetY;

        List<ModelTuile> tuilesToRender = new ArrayList<>();

        for (Map.Entry<Point, ModelTuile> entry : tuiles.entrySet()) {
            ModelTuile tuile = entry.getValue();
            if (tuile != null) {
                tuilesToRender.add(tuile);
            }
        }

        for (ModelTuile tuile : tuilesToRender) {
            int col = tuile.getX();
            int row = tuile.getY();

            int x = totalOffsetX + col * (3 * tuileSize / 2);
            int y = totalOffsetY + row * hexHeight;

            if (x + tuileSize > 0 && x < getWidth() && y + hexHeight > 0 && y < getHeight()) {
                updateTuileView(tuile, x, y);
            }
        }
    }

    private void updateTuileView(ModelTuile tuile, int x, int y) {
        if (tuile != null) {
            if (tuile.getVueTuile() == null) { //si c'est une nouvelle tuile
                if (!tuile.isButton() && !tuile.isOnBoard()) {
                    tuile.createVueTuile(60, getHeight() - (5 * modelJeux.getListTuiles().size() + 45), tuileSize, modelJeux.isAA());
                    this.add(tuile.getVueTuile(), Integer.valueOf(0));
                    this.updatePreviewTuileList();
                    Animator.moveToTuile(tuile.getVueTuile(),60,tuile.getVueTuile().getY(),500, this);
                } else if (!modelJeux.getListTuiles().isEmpty() && tuile.isButton()) {
                    tuile.createVueTuile(x, y, tuileSize / 2, modelJeux.isAA());
                    this.add(tuile.getVueTuile(), Integer.valueOf(0));
                    tuile.getVueTuile().addMouseListener(new ControllerPoseTuile(modelJeux));
                }
            } else { //si c'est une ancienne tuile qu'on déplace
                if (!tuile.isButton() && tuile.isOnBoard()) {
                    tuile.getVueTuile().updateTuile(x, y, tuileSize,modelJeux.isAA());
                } else if (!modelJeux.getListTuiles().isEmpty() && tuile.isButton()) {
                    tuile.getVueTuile().updateTuile(x, y, tuileSize / 2, modelJeux.isAA());
                }
            }
        }
    }

    private void endGame() {
        end = true;
        modelJeux.deleteButtons();
        updatePreviewTuileList();
        repaint();
        modelJeux.createEndView();
        deletePlayerInfo();
        add(modelJeux.getVueScoreScreen(), Integer.valueOf(1));
        Animator.moveTo(modelJeux.getVueScoreScreen() ,modelJeux.getVueScoreScreen().getX(), modelJeux.getVueScoreScreen().getY(), this.getWidth() - (modelJeux.getVueScoreScreen().getWidthSidebar()+20), modelJeux.getVueScoreScreen().getY(), 500,true);
    }

    public void updateOffsets(int deltaX, int deltaY) {
        if (offsetX+deltaX < 3365 && offsetX+deltaX > -3365) {
            offsetX += deltaX;
        }
        if (offsetY+deltaY < 3365 && offsetY+deltaY > -3365) {
            offsetY += deltaY;
        }
    }

    public int getOffsetX(){
        return this.offsetX;
    }

    public int getOffsetY(){
        return this.offsetY;
    }

    public void updatePreviewTuileList() {
        int oracle = 3;
        for (int i = 0; i < tuilePreview.length; i++) {
            if (tuilePreview[i] != null) {
                remove(tuilePreview[i].getVueTuile());
            }
            if (i < modelJeux.getListTuiles().size()) {
                ModelTuile tuile;
                if (i < oracle) {
                    tuile = new ModelTuile(modelJeux.getListTuiles().get(i).getSeed(), false, true, modelJeux.isAA());
                    tuile.setComposition(modelJeux.getListTuiles().get(i).getComposition());
                    //tuile.createVueTuile(60, getHeight() - (5 * (modelJeux.getListTuiles().size()) - i*45 + 45*oracle), 50, modelJeux.isAA());
                }else {
                    tuile = new ModelTuile(modelJeux.getListTuiles().get(i).getSeed(), true, false, modelJeux.isAA());

                }
                tuile.createVueTuile(60, getHeight() - (5 * (modelJeux.getListTuiles().size() - i) + 45), 50, modelJeux.isAA());
                tuilePreview[i] = tuile;
                add(tuilePreview[i].getVueTuile(), Integer.valueOf(modelJeux.getListTuiles().size() - i));
            }
        }
        if (modelJeux.getListTuiles().isEmpty()) {
            modelJeux.createEndView();
            add(modelJeux.getVueScoreScreen(), Integer.valueOf(2));
        }
        modelJeux.createButton();
        repaint();
    }

    public ModelTuile setPreviewOnButton(VueTuile btnHovered) {
        if (btnHovered.getModelTuile().isButton() && !modelJeux.getListTuiles().isEmpty()) {

            ModelTuile previewTuile = new ModelTuile(modelJeux.getListTuiles().getFirst().getSeed(), false, false, modelJeux.isAA());
            previewTuile.setComposition(modelJeux.getListTuiles().getFirst().getComposition());

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            int initialOffsetX = centerX - (3 * tuileSize / 2) * tuileCentreCol;
            int initialOffsetY = centerY - hexHeight * tuileCentreRow;

            int totalOffsetX = initialOffsetX + offsetX;
            int totalOffsetY = initialOffsetY + offsetY;

            int col = btnHovered.getModelTuile().getX();
            int row = btnHovered.getModelTuile().getY();

            int x = totalOffsetX + col * (3 * tuileSize / 2);
            int y = totalOffsetY + row * hexHeight;

            previewTuile.createVueTuile(x, y, (int) (tuileSize*0.8), modelJeux.isAA());
            add(previewTuile.getVueTuile(), Integer.valueOf(1));
            repaint();

            return previewTuile;
        }
        return null;
    }

    public void unsetPreviewOnButton(ModelTuile previewTuile) {
        if (previewTuile != null) {
            remove(previewTuile.getVueTuile());
        }
        repaint();
    }

    public void createPlayerInfo() {
        infoPanel = new VueInfoPanel(modelJeux);

        this.add(infoPanel, Integer.valueOf(2));
    }

    public void updatePlayerInfo() {
        deletePlayerInfo();
        createPlayerInfo();
    }

    public void deletePlayerInfo() {
        infoPanel.removeAll();
        remove(infoPanel);
    }

    public ModelJeux getModelJeux(){
        return this.modelJeux;
    }
}
