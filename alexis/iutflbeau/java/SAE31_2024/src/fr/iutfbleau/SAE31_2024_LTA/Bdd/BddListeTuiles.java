package fr.iutfbleau.SAE31_2024_LTA.Bdd;

/**
 * Représente une liste de tuiles stockée dans la base de données, contenant un identifiant unique,
 * une valeur de seed pour générer les tuiles, et un score maximal enregistré pour cette configuration.
 *
 * <p>Cette classe encapsule les informations d'une liste de tuiles pour faciliter la récupération
 * et le classement des configurations de tuiles.
 *
 * <p>Exemple d'utilisation :
 * <pre>{@code
 * BddListeTuiles listeTuiles = new BddListeTuiles(1, 12345, 200);
 * }</pre>
 */
public class BddListeTuiles {

    private final int id;
    private final int seed;
    private final Integer bestScore;

    /**
     * Construit une instance de {@code BddListeTuiles} avec un identifiant, un seed, et un score maximal.
     *
     * @param id l'identifiant unique de la liste de tuiles
     * @param seed la valeur de seed pour générer la configuration de tuiles
     * @param bestScore le meilleur score enregistré pour cette liste de tuiles, ou {@code null} s'il n'existe pas encore de score
     */
    public BddListeTuiles(int id, int seed, Integer bestScore) {
        this.id = id;
        this.seed = seed;
        this.bestScore = bestScore;
    }

    /**
     * Retourne l'identifiant unique de cette liste de tuiles.
     *
     * @return l'identifiant de la liste de tuiles
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne la valeur du seed utilisé pour générer cette liste de tuiles.
     *
     * @return le seed de la liste de tuiles
     */
    public int getSeed() {
        return seed;
    }

    /**
     * Retourne le meilleur score enregistré pour cette liste de tuiles.
     *
     * @return le meilleur score pour cette configuration de tuiles, ou {@code null} s'il n'existe pas de score
     */
    public Integer getBestScore() {
        return bestScore;
    }
}