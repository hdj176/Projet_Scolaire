package fr.iutfbleau.SAE31_2024_LTA.jeux.model;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.endGame.VueScoreScreen;
import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueJeux;

import javax.swing.*;
import java.awt.Point;
import java.util.*;

/**
 * La classe ModelJeux gere la logique du jeu, y compris les tuiles, les scores et les interactions avec la vue.
 *  Elle s'occupe egalement de la creation et de la gestion des boutons associés aux tuiles.
 *
 *
 */

public class ModelJeux {
    private VueJeux vueJeux;
    private VueScoreScreen vueScoreScreen;

    private final ModelPrincipale modelPrincipale;
    private final ModelMatrice modelMatrice;


    private final LinkedList<ModelTuile> listTuiles;//Liste de tuile généré

    private boolean undoActivate = false;
    private boolean undo = false;
    private ModelTuile tuileUndoAble;

    private int score = 0;

    private static final int nombreTuile = 50;

    /**
     * Constructeur de ModelJeux
     * @param modelPrincipale
     * @param seed
     */
    public ModelJeux(ModelPrincipale modelPrincipale, int seed) {
        this.modelPrincipale = modelPrincipale;

        this.modelMatrice = new ModelMatrice(this);
        listTuiles = new LinkedList<>();

        for (int i = nombreTuile; i >= 0; i--) {
            ModelTuile tuile = new ModelTuile(seed*i, false,false,modelPrincipale.getConfigManager().isAA());
            listTuiles.add(tuile);
        }

        createView();

        vueJeux.updatePreviewTuileList();
    }

    /**
     * créer une VueJeux associer a ModelJeux
     */
    private void createView(){
        this.vueJeux = new VueJeux(this);
        modelPrincipale.getVuePrincipale().getPrincipaleLayeredPane().getMainPanel().add(vueJeux, "jeux");
    }

    /**
     * créer une EndView associer a ModelJeux, cela s'affiche lorsque que la partie est finie
     */
    public void createEndView() {
        this.vueScoreScreen = new VueScoreScreen(modelPrincipale);
        this.getVueScoreScreen().setBounds(vueJeux.getWidth(), (vueJeux.getHeight()-this.getVueScoreScreen().getHeightSidebar())/2, this.getVueScoreScreen().getWidthSidebar(), this.getVueScoreScreen().getHeightSidebar());
    }

    /**
     * @return  LinkedList<ModelTuile>
     */
    public LinkedList<ModelTuile> getListTuiles() {
        return listTuiles;
    }

    /**
     *
     * @return VueJeux
     */
    public VueJeux getVueJeux() {
        return this.vueJeux;
    }

    /**
     *
     * @return ModelMatrice
     */
    public ModelMatrice getModelMatrice() {
        return this.modelMatrice;
    }

    /**
     *
     * @return ModelPrincipale
     */
    public ModelPrincipale getModelPrincipale() {
        return this.modelPrincipale;
    }

    /**
     * methode permetant de creer les boutons dans le model, en cherchant les tuiles preexistantes dans la matriceet en
     * y ajoutant de nouvelles sous la forme de boutons
     */
    public void createButton() {
        List<Point> pointsToAdd = new ArrayList<>(); // Liste pour stocker les points à ajouter

        for (Map.Entry<Point, ModelTuile> entry : modelMatrice.getTuilesPartie().entrySet()) {
            Point point = entry.getKey();
            ModelTuile tuile = entry.getValue();

            if (tuile != null && !tuile.isButton()) {
                // verifie si chaque position est disponible et l'ajoute à la liste pour print les bouttons
                if (tryCreateButton(point.x - 1, point.y - 1)) {  // Nord-Ouest
                    pointsToAdd.add(new Point(point.x - 1, point.y - 1));
                }
                if (tryCreateButton(point.x , point.y - 2)) {      // Nord
                    pointsToAdd.add(new Point(point.x , point.y - 2));
                }
                if (tryCreateButton(point.x - 1, point.y + 1)) {  // Nord-Est
                    pointsToAdd.add(new Point(point.x - 1, point.y + 1));
                }
                if (tryCreateButton(point.x + 1, point.y - 1)) {  // Sud-Ouest
                    pointsToAdd.add(new Point(point.x + 1, point.y - 1));
                }
                if (tryCreateButton(point.x , point.y + 2)) {      // Sud
                    pointsToAdd.add(new Point(point.x , point.y + 2));
                }
                if (tryCreateButton(point.x + 1, point.y + 1)) {  // Sud-Est
                    pointsToAdd.add(new Point(point.x + 1, point.y + 1));
                }
            }
        }

        for (Point newPoint : pointsToAdd) {
            modelMatrice.poseeButton(newPoint.x, newPoint.y, new ModelTuile());
        }
    }

    /**
     *  Cette methode regarde dans la matrice du model afin de voir si l'emplacement (x;y) est occupé
     * @param x
     * @param y
     * @return boolean
     */
    private boolean tryCreateButton(int x, int y) {
        return !modelMatrice.isOccupied(x, y);
    }

    /**
     * détruit tous les boutons
     */
    public void deleteButtons() {
        Map<Point, ModelTuile> tuiles = modelMatrice.getTuilesPartie();

        for (ModelTuile tuile : tuiles.values()) {
            if (tuile.isButton() && tuile.getVueTuile() != null) {
                vueJeux.remove(tuile.getVueTuile());
            }
        }

        tuiles.values().removeIf(ModelTuile::isButton);
    }

    /**
     *  joue un son en fonction de son soundindex
     * @param soundIndex
     */
    public void playTuileSound(int soundIndex) {
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClipsTuiles()[soundIndex], false);
    }

    /**
     *
     * @return VueScoreScreen
     */
    public VueScoreScreen getVueScoreScreen() {
        return vueScoreScreen;
    }

    /**
     * méthode qui donne le score de ModelJeux
     * @return int
     */
    public int getScore() {
        return this.score;
    }

    /**
     * set le score de modelJeux égale au int en paramètre
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    public boolean isUndo() {
        return undo;
    }

    /**
     * met la valeur d'undo en false ou true
     * @param undo
     */
    public void setUndo(boolean undo) {
        this.undo = undo;
    }

    /**
     * revoie une valeur qui permet de savoir si undo est activable
     * @return boolean
     */
    public boolean isUndoActivate() {
        return undoActivate;
    }

    /**
     *
     * @param undoActivate
     */
    public void setUndoActivate(boolean undoActivate) {
        this.undoActivate = undoActivate;
    }

    public void setTuileUndoAble(ModelTuile tuileUndoAble) {
        this.tuileUndoAble = tuileUndoAble;
    }

    /**
     *  annule la dernière pose d'une tuile cela n'est
     * @return
     */
    public Action undoLastTuile() {
        if (!undo) {
            listTuiles.addFirst(tuileUndoAble);
            deleteButtons();

            modelMatrice.deleteTuile(tuileUndoAble);
            setScore(ModelComptagePoints.UndoPoints(getScore(),listTuiles.getFirst(),modelMatrice));
            getVueJeux().updatePreviewTuileList();
            tuileUndoAble.setVueTuile(null);
            createButton();

            //Alexis ici

            undo = true;
            tuileUndoAble.setOnBoard(false);

        }
        return null;
    }

    public boolean isAA() {
        return modelPrincipale.getConfigManager().isAA();
    }



}
