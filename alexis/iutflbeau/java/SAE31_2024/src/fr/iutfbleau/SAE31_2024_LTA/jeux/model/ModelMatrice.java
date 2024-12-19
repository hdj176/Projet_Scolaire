package fr.iutfbleau.SAE31_2024_LTA.jeux.model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe sert à gérer le plateau de jeux,
 * Elle fournit des méthodes pour placer et retirer des tuiles, vérifier si un emplacement est occupé ou récupérer les tuiles voisines
 */
public class ModelMatrice {

    private Map<Point, ModelTuile> tuilesPartie;
    private final ModelJeux modelJeux;
    /**
     * Constructeur d'un nouveau ModelMatrice avec le modèle de jeu spécifié.
     * les tuiles sont placés de la façon suivante dans le tableau :
     *      [vide][Nord][vide]
     *  [NordOuest][vide][NordEst]
     *  [vide][référence][vide]
     *  [SudOuest][vide][SudEst]
     *      [vide][Sud][vide]
     *
     * @param modelJeux L'instance de ModelJeux à associer avec cette matrice.
     */
    public ModelMatrice(ModelJeux modelJeux) {
        this.modelJeux = modelJeux;

        this.tuilesPartie = new HashMap<>(); // Utilisation d'une HashMap pour les tuiles
    }
    /**
     * Place une tuile aux coordonnées indiquées sur le plateau de jeu.
     * utilise des méthodes provenant d'autres classes afin de Mettre à jour le score du jeu et de gérer le Undo.
     *
     * @param x coordonnée x où la tuile doit être placée.
     * @param y coordonnée y où la tuile doit être placée.
     */
    public void poseTuile(int x, int y) {
        if (!modelJeux.getListTuiles().isEmpty()) {
            ModelTuile tuile = modelJeux.getListTuiles().getFirst();
            this.tuilesPartie.put(new Point(x, y), tuile);
            this.tuilesPartie.get(new Point(x, y)).setCoordonner(x, y);
            modelJeux.setTuileUndoAble(tuile);
            ModelTuile[] voisin = getVoisins(tuile);
            modelJeux.setScore(ModelComptagePoints.updatePoints(modelJeux.getScore(), tuile, voisin));
            modelJeux.getListTuiles().removeFirst();

            if (!modelJeux.isUndoActivate() && x != 0 && y != 0) {
                modelJeux.setUndoActivate(true);

            }
            if (modelJeux.isUndo()) {
                modelJeux.setUndo(false);
            }
        }
    }

    /**
     * supprime une tuile du plateau
     * @param tuile
     */
    public void deleteTuile (ModelTuile tuile){
        if (!tuilesPartie.isEmpty() && tuile != null && tuile.getVueTuile() != null) {
            modelJeux.getVueJeux().remove(tuile.getVueTuile());
            this.tuilesPartie.remove(new Point(tuile.getX(), tuile.getY()));
        }
    }

    /**
     * Place une tuile Bouton au coordonées (x,y)
     * @param x
     * @param y
     * @param tuile
     */
    public void poseeButton ( int x, int y, ModelTuile tuile){
        this.tuilesPartie.put(new Point(x, y), tuile);
        this.tuilesPartie.get(new Point(x, y)).setCoordonner(x, y);
    }

    /**
     * regarde si l'emplacement est déjà utilisé
     * @param x
     * @param y
     * @return
     */
    public boolean isOccupied ( int x, int y){
        ModelTuile tuile = tuilesPartie.get(new Point(x, y));
        return tuile != null;
    }

    /**
     * Vérifie si une tuile est située au nordOuest (x-1,y-1) de la tuile spécifiée.
     *
     * @param tuile La tuile de référence.
     * @return True s'il existe une tuile au nordOuest, sinon false.
     */
    public boolean isNordOuest (ModelTuile tuile){
        return this.tuilesPartie.containsKey(new Point(tuile.getX() - 1, tuile.getY() - 1));
    }
    /**
     * Vérifie si une tuile est située au nord (x,y-2) de la tuile spécifiée.
     *
     * @param tuile La tuile de référence.
     * @return True s'il existe une tuile au nord, sinon false.
     */
    public boolean isNord (ModelTuile tuile){
        return this.tuilesPartie.containsKey(new Point(tuile.getX(), tuile.getY() - 2));
    }
    /**
     * Vérifie si une tuile est située au nordEst (x+1,y-1) de la tuile spécifiée.
     *
     * @param tuile La tuile de référence.
     * @return True s'il existe une tuile au nordEst, sinon false.
     */
    public boolean isNordEst (ModelTuile tuile){
        return this.tuilesPartie.containsKey(new Point(tuile.getX() + 1, tuile.getY() - 1));
    }
    /**
     * Vérifie si une tuile est située au sudOuest (x-1,y+1) de la tuile spécifiée.
     *
     * @param tuile La tuile de référence.
     * @return True s'il existe une tuile au sudOuest, sinon false.
     */
    public boolean isSudOuest (ModelTuile tuile){
        return this.tuilesPartie.containsKey(new Point(tuile.getX() - 1, tuile.getY() + 1));
    }
    /**
     * Vérifie si une tuile est située au sud (x,y+2) de la tuile spécifiée.
     *
     * @param tuile La tuile de référence.
     * @return True s'il existe une tuile au sud, sinon false.
     */
    public boolean isSud (ModelTuile tuile){
        return this.tuilesPartie.containsKey(new Point(tuile.getX(), tuile.getY() + 2));
    }
    /**
     * Vérifie si une tuile est située au sudEst (x+1,y+1) de la tuile spécifiée.
     *
     * @param tuile La tuile de référence.
     * @return True s'il existe une tuile au sudEst, sinon false.
     */
    public boolean isSudEst (ModelTuile tuile){
        return this.tuilesPartie.containsKey(new Point(tuile.getX() + 1, tuile.getY() + 1));
    }

    /**
     * revnoie la tuile au NordOuest de celle de référence
     * @param tuile
     * @return ModelTuile
     */
    public ModelTuile getNordOuest (ModelTuile tuile){
        return this.tuilesPartie.get(new Point(tuile.getX() - 1, tuile.getY() - 1));
    }
    /**
     * revnoie la tuile au Nord de celle de référence
     * @param tuile
     * @return ModelTuile
     */
    public ModelTuile getNord (ModelTuile tuile){
        return this.tuilesPartie.get(new Point(tuile.getX(), tuile.getY() - 2));
    }
    /**
     * revnoie la tuile au NordEst de celle de référence
     * @param tuile
     * @return ModelTuile
     */
    public ModelTuile getNordEst (ModelTuile tuile){
        return this.tuilesPartie.get(new Point(tuile.getX() + 1, tuile.getY() - 1));
    }
    /**
     * revnoie la tuile au SudOuest de celle de référence
     * @param tuile
     * @return ModelTuile
     */
    public ModelTuile getSudOuest (ModelTuile tuile){
        return this.tuilesPartie.get(new Point(tuile.getX() - 1, tuile.getY() + 1));
    }
    /**
     * revnoie la tuile au Sud de celle de référence
     * @param tuile
     * @return ModelTuile
     */
    public ModelTuile getSud (ModelTuile tuile){
        return this.tuilesPartie.get(new Point(tuile.getX(), tuile.getY() + 2));
    }
    /**
     * revnoie la tuile au SudEst de celle de référence
     * @param tuile
     * @return ModelTuile
     */
    public ModelTuile getSudEst (ModelTuile tuile){
        return this.tuilesPartie.get(new Point(tuile.getX() + 1, tuile.getY() + 1));
    }

    /**
     * renvoie les un tableau de tuile qui sont voisins à la tuile de référence
     * ATTENTION l'ordre est : de  0 a 5 compris respectivement  SudEst,Sud,SudOuest,NordOuest,Nord,NordEst
     * @param tuile
     * @return ModelTuile[]
     */

    public ModelTuile[] getVoisins (ModelTuile tuile){
        ModelTuile[] voisins = new ModelTuile[6];

        if (this.isSudEst(tuile)) {  //trouve bien
            if (!this.getSudEst(tuile).isButton()) {
                voisins[0] = this.getSudEst(tuile);
            }

        }

        if (this.isSud(tuile)) {    // n'arrive pas à le trouver
            if (!this.getSud(tuile).isButton()) {
                voisins[1] = this.getSud(tuile);
            }

        }

        if (this.isSudOuest(tuile)) {   //trouve deux voisins alors que 1 seul
            if (!this.getSudOuest(tuile).isButton()) {
                voisins[2] = this.getSudOuest(tuile);
            }

        }

        if (this.isNordOuest(tuile)) {   //trouve deux voisins alors que 1 seul
            if (!this.getNordOuest(tuile).isButton()) {
                voisins[3] = this.getNordOuest(tuile);

            }

        }

        if (this.isNord(tuile)) {  //n'arrive pas à le trouver
            if (!this.getNord(tuile).isButton()) {
                voisins[4] = this.getNord(tuile);
            }
        }


        if (this.isNordEst(tuile)) {   //trouve 1 seul mais pas le bon endroit
            if (!this.getNordEst(tuile).isButton()) {
                voisins[5] = this.getNordEst(tuile);
            }
        }
        return voisins;

    }

    /**
     * cela permet de récupèrer une valeur qui indique si les sections de la tuile qui touche d'autre tuile sont de la même couleur.
     * @param tuile
     * @param comparaison
     * @param direction
     * @return boolean  , true si la couleur correspond et false sinon.
     */
    public static boolean correspond (ModelTuile tuile, ModelTuile comparaison,int direction){
        if (tuile.getComposition()[direction] == comparaison.getComposition()[(direction + 3) % 6]) {
            return true;
        }
        return false;
    }

    /**
     * regarde depuis la liste voisin d'une tuile de référence si les voisin on la bonne couleur
     * @param tuile
     * @param voisin
     * @return
     */
    public static boolean[] correspondVoisins (ModelTuile tuile, ModelTuile[]voisin){
        boolean[] correspond = new boolean[voisin.length];
        for (int i = 0; i < voisin.length; i++) {
            if (voisin[i] != null) {


                if (ModelMatrice.correspond(tuile, voisin[i], i)) {
                    correspond[i] = true;
                } else {
                    correspond[i] = false;
                }
            }
        }
        return correspond;
    }
    /**
     * Récupère la carte des tuiles plateau de jeu.
     *
     * @return  Map<Point, ModelTuile> carte des tuiles.
     */
    public Map<Point, ModelTuile> getTuilesPartie () {
        return this.tuilesPartie;
    }
}
