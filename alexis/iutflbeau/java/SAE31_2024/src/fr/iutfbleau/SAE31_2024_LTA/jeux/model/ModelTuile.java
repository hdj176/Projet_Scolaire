package fr.iutfbleau.SAE31_2024_LTA.jeux.model;

import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueJeux;
import fr.iutfbleau.SAE31_2024_LTA.jeux.vue.VueTuile;
/**
 * La classe ModelTuile représente une tuile, comprenant sa composition, ses coordonnées,
 * et les informations nécessaires à son affichage.
 */
public class ModelTuile {
    private int[] composition;

    private ModelPoche[] inpoche;

    private int seed;

    private int x;
    private int y;

    private final boolean button;
    private boolean preview = false;
    private boolean suivante;
    private boolean onBoard = false;

    private VueTuile vueTuile;

    private TuileRandomFactory tuileRandomFactory;
    /**
     * Constructeur pour créer une tuile.
     *
     * @param seed          La graine pour générer des couleurs en pseudo-aléatoire.
     * @param preview       Indique si la tuile est en mode aperçu.
     * @param suivante      Indique si la tuile est suivante.
     * @param AntiAliasing  Indique si l'anti-aliasing doit être appliqué.
     */
    public ModelTuile(int seed, boolean preview, boolean suivante, boolean AntiAliasing) {//Tuile de jeux
        composition = new int[6];
        this.inpoche = new ModelPoche[2];
        this.suivante = suivante;

        this.tuileRandomFactory = new TuileRandomFactory(seed);
        this.seed = seed;
        this.preview = preview;

        composition = tuileRandomFactory.getComposition();

        button = false;
    }
    /**
     * Constructeur pour créer une tuile grise qui sert de bouton.
     */
    public ModelTuile() {//Tuile grise qui sert de bouton
        button = true;
    }

    /**
     * Crée la représentation visuelle d'un polygone de la tuile à des coordonnées indiquées.
     *
     * @param centerX   La coordonnée x du centre de la tuile.
     * @param centerY   La coordonnée y du centre de la tuile.
     * @param radius    Le rayon de la tuile.
     * @param isAA      Indique si l'anti-aliasing doit être appliqué.
     */
    public void createVueTuile(int centerX, int centerY, int radius, boolean isAA) {

        vueTuile = new VueTuile(this, centerX, centerY, radius, isAA);
    }
    /**
     * Récupère la composition de la tuile.
     *
     * @return Un tableau d'entiers représentant la composition de la tuile.
     */
    public int[] getComposition() {
        return this.composition;
    }
    /**
     * Définit les coordonnées de la tuile dans la matrice.
     *
     * @param x La coordonnée x de la tuile.
     * @param y La coordonnée y de la tuile.
     */
    public void setCoordonner(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Décale la composition de la tuile en fonction de la direction spécifiée.
     *
     * @param decaler La valeur de décalage (positive pour descendre, négative pour monter).
     * @param vueJeux La vue de jeu à mettre à jour.
     */
    public void decalage(int decaler, VueJeux vueJeux){
        //decaler > 0 = molette vers le bas
        if(decaler>0) {
            int tmp = this.composition[0];
            for (int i = 0; i < composition.length - 1; i++) {
                this.composition[i] = this.composition[i + 1];
                if(i == composition.length-2){
                    this.composition[i+1] = tmp;
                }
            }
        }
        //decaler < 0 = molette vers le haut
        else{
            int tmp = this.composition[this.composition.length-1];
            for (int i = composition.length - 1; i > 0 ; i--) {
                this.composition[i] = this.composition[i - 1];
                if(i == 1){
                    this.composition[i-1] = tmp;
                }
            }
        }
        vueJeux.updatePreviewTuileList();
    }
    /**
     * Récupère la coordonnée x de la tuile.
     *
     * @return int
     */
    public int getX() {
        return this.x;
    }
    /**
     * Récupère la coordonnée y de la tuile.
     *
     * @return int
     */
    public int getY() {
        return this.y;
    }

    /**
     * Vérifie si la tuile est un bouton.
     *
     * @return True si la tuile est un bouton, sinon false.
     */
    public boolean isButton() {
        return button;
    }
    /**
     * donne la Vue qui est liée à cette tuile.
     * @return VueTuile
     */
    public VueTuile getVueTuile() {
        return vueTuile;
    }
    /**
     * Supprime la Vue qui était liée à cette tuile.
     */
    public void deleteVueTuile() {
        vueTuile = null;
    }

    /**
     * Récupère la graine utilisée pour générer des éléments aléatoires.
     *
     * @return seed
     */
    public int getSeed() {
        return seed;
    }
    /**
     * Récupère l'index du son associé à cette tuile.
     *
     * @return int
     */
    public int getSoundIndex() {
        return tuileRandomFactory.getSoundIndex();
    }
    /**
     * demande si la tuile est en preview.
     *
     * @return True si la tuile est en mode aperçu, sinon false.
     */
    public boolean isPreview() {
        return preview;
    }
    /**
     * Définit la composition de la tuile.
     *
     * @param composition Un tableau d'entiers représentant la nouvelle composition de la tuile.
     */
    public void setComposition(int[] composition){
        this.composition = composition;
    }
    /**
     * Récupère l'int correspondant à la première couleur de la tuile.
     *
     * @return int
     */
    public int getIndexcouleur1() {
        return tuileRandomFactory.getIndexcouleur1();
    }
    /**
     * Récupère l'int correspondant à la dexuième couleur de la tuile.
     *
     * @return int
     */
    public int getIndexcouleur2() {
        return tuileRandomFactory.getIndexcouleur2();
    }
    /**
     * Définit la représentation Vue de la tuile.
     *
     * @param vueTuile L'instance de VueTuile à associer à cette tuile.
     */
    public void setVueTuile(VueTuile vueTuile) {
        this.vueTuile = vueTuile;
    }
    /**
     * Vérifie si la tuile est suivante.
     *
     * @return True si la tuile est suivante, sinon false.
     */
    public boolean isSuivante() {
        return suivante;
    }
    /**
     * Définit les modèles des poches associées à cette tuile.
     *
     * @param poche1 le premier.
     * @param poche2 le deuxième.
     */
    public void setPoche(ModelPoche poche1,ModelPoche poche2) {
        this.inpoche[0] = poche1;
        this.inpoche[1] = poche2;
    }
    /**
     * Définit le modèle de la première poche associée à cette tuile.
     *
     * @param poche la première poche.
     */
    public void setPoche1(ModelPoche poche) {
        this.inpoche[0]=poche;

    }
    /**
     * Définit le modèle de la deuxième poche associée à cette tuile.
     *
     * @param poche la deuxième poche.
     */
    public void setPoche2(ModelPoche poche) {
        this.inpoche[1]=poche;
    }

    /**
     * Récupère les poches associées à cette tuile sous forme de tableau.
     * @return ModelPoche un tableau contenant les deux poches.
     */
    public ModelPoche[] getPoche() {
        return inpoche;
    }

    /**
     * regarde si la tuile est sur le plateau
     * @return onBoard true Si oui, False sinon
     */
    public boolean isOnBoard() {
        return onBoard;
    }

    /**
     * indique si la tuile est sur le plateau ou non
     * @param onBoard True si oui, false sinon
     */
    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }
}
