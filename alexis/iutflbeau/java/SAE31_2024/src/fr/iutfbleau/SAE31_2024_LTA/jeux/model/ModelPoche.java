package fr.iutfbleau.SAE31_2024_LTA.jeux.model;

import java.util.ArrayList;

/**
 * cette classe poche contient deux attributs, tuiles qui est une liste de tuile
 * et couleur qui est la couleur que toutes les tuiles de la poche partage
 * pour que deux tuiles soient dans la meme poche, il faut que les deux tuiles
 * soient adjacents et qu'il est la meme couleur sur le coté où ils sont adjacents
 * ou alors qu'une autre tuile fassent pour eux indirectement
 */


public class ModelPoche {
    private final int couleur;
    private final ArrayList<ModelTuile> tuiles;

    public ModelPoche(int couleur, ModelTuile tuile) {
        this.couleur = couleur;
        this.tuiles = new ArrayList<>();
        this.tuiles.add(tuile);
    }
    /** cette fonction retourne la poche de la tuile "comparaison" qui a la meme couleur
     * que la couleur de la tuile "tuile" dans la direction "direction"
     * @return ModelPoche
     */
    public static ModelPoche comparaisonPoche(ModelTuile tuile, ModelTuile comparaison, int direction) {
        int couleur = tuile.getComposition()[direction];
        if (comparaison.getPoche()[0] == comparaison.getPoche()[1]) {
            return comparaison.getPoche()[0];
        }
        return comparaison.getPoche()[0].getCouleur() == couleur ? comparaison.getPoche()[0] : comparaison.getPoche()[1];
    }

    /** retourne la liste des tuiles dans la poche
     * @return  ArrayList<ModelTuile>
     */
    public ArrayList<ModelTuile> getTuiles() {
        return tuiles;
    }

    /** ajoute la tuile "m" à la poche
     * @param m
     */
    public void addTuile(ModelTuile m) {
        this.tuiles.add(m);
    }

    /** retourne la couleur de la poche
     * 0 = mer;1 = Montagne;2 = Champ;3 = Plaine;4 = Foret;
     * @return int qui est l'equivalent d'une couleur
     */
    public int getCouleur() {
        return this.couleur;
    }

    /** enlève la tuile "m" de la poche
     *
     * @param m
     */
    public void removeTuile(ModelTuile m) {
        this.tuiles.remove(m);
    }

    /** fonction (static) qui va appelé la fonction récursive
     *
     * @param m
     * @param poche
     * @param matrice
     * @param tuileEnleve
     * @return ModelPoche
     */
    public static ModelPoche createPocheVoisinProfondeur(ModelTuile m, ModelPoche poche, ModelMatrice matrice, ModelTuile tuileEnleve) {
        ArrayList<ModelTuile> visited = new ArrayList<>();
        ArrayList<ModelTuile> Avisite = new ArrayList<>();
        visited.add(tuileEnleve);
        return createPocheVoisinProfondeur(m, poche,matrice, visited, Avisite);
    }

    /** fonction (static) récursive qui recrée la poche de avant que la dernière tuile soit poser
     *
     * @param m
     * @param poche
     * @param matrice
     * @param visited
     * @param Avisite
     * @return ModelPoche
     */
    private static ModelPoche createPocheVoisinProfondeur(ModelTuile m, ModelPoche poche, ModelMatrice matrice, ArrayList<ModelTuile> visited, ArrayList<ModelTuile> Avisite) {
        Avisite.remove(m);
        if (visited.contains(m)) {
            return createPocheVoisinProfondeur(m, poche, matrice, visited, Avisite);
        }
        visited.add(m);
        ModelTuile[] voisin = matrice.getVoisins(m);
        boolean[] correspond = ModelMatrice.correspondVoisins(m, voisin);

        for (int i = 0; i < correspond.length; i++) {
            if (correspond[i] && !poche.tuiles.contains(voisin[i]) && m.getComposition()[i] == poche.getCouleur()) {
                poche.addTuile(voisin[i]);
                if (!Avisite.contains(voisin[i])) {
                    Avisite.add(voisin[i]);
                }
                return Avisite.size() > 0 ? createPocheVoisinProfondeur(Avisite.get(0), poche, matrice, visited, Avisite) : poche;
            }
        }
        return Avisite.size() > 0 ? createPocheVoisinProfondeur(Avisite.get(0), poche, matrice, visited, Avisite) : poche;
    }

    /**
     * fonction (static) qui crée une nouvelle poche et fais savoir poche qui ont changé de poche leurs nouvelle poche
     * @param poche
     * @param matrice
     * @param tuile
     * @return ModelPoche
     */
    public static ModelPoche createNouvellePoche(ModelPoche poche, ModelMatrice matrice, ModelTuile tuile) {
        ModelPoche nouvellepoche = new ModelPoche(poche.getCouleur(), poche.getTuiles().get(0));
        nouvellepoche = createPocheVoisinProfondeur(poche.getTuiles().get(0), nouvellepoche, matrice, tuile);

        if (poche.getTuiles().get(0).getPoche()[0].getCouleur() == poche.getCouleur()) {
            poche.getTuiles().get(0).setPoche1(nouvellepoche);
        }
        if (poche.getTuiles().get(0).getPoche()[1].getCouleur() == poche.getCouleur()) {
            poche.getTuiles().get(0).setPoche2(nouvellepoche);
        }
        poche.getTuiles().remove(0);

        for (int i = poche.getTuiles().size() - 1; i >= 0; i--) {
            if (nouvellepoche.getTuiles().contains(poche.getTuiles().get(i))) {
                if (poche.getTuiles().get(i).getPoche()[0].getCouleur() == poche.getCouleur()) {
                    poche.getTuiles().get(i).setPoche1(nouvellepoche);
                }
                if (poche.getTuiles().get(i).getPoche()[1].getCouleur() == poche.getCouleur()) {
                    poche.getTuiles().get(i).setPoche2(nouvellepoche);
                }
                poche.getTuiles().remove(i);
            }
        }
        return nouvellepoche;
    }

    /**
     * fonction (static) qui met toutes les tuiles des poches contenu dans "couleur" dans la poche "poche"
     * @param couleur
     * @param poche
     * @param indexcouleur
     */
    public static void changementPoche(ArrayList<ModelPoche> couleur, ModelPoche poche, int indexcouleur) {
        for (ModelPoche changementPoche : couleur) {
            for (ModelTuile tuileChangement : changementPoche.getTuiles()) {
                if (tuileChangement.getPoche()[0].getCouleur() == indexcouleur) {
                    tuileChangement.setPoche1(poche);
                    poche.addTuile(tuileChangement);
                }
                if (tuileChangement.getPoche()[1].getCouleur() == indexcouleur) {
                    tuileChangement.setPoche2(poche);
                    if (tuileChangement.getPoche()[0].getCouleur() != tuileChangement.getPoche()[1].getCouleur()) {
                        poche.addTuile(tuileChangement);
                    }
                }
            }
        }
    }
}
