package fr.iutfbleau.SAE31_2024_LTA.Bdd;

/**
 * Représente une partie jouée dans la base de données, avec un identifiant unique,
 * un nom de joueur, un score et une référence à la liste de tuiles associée à la partie.
 *
 * <p>Cette classe encapsule les informations d'une partie jouée pour faciliter l'accès
 * et la manipulation des données lors de l'affichage et du classement des parties.
 *
 * <p>Exemple d'utilisation :
 * <pre>{@code
 * BddListeTuiles listeTuiles = new BddListeTuiles(1, 12345, 200);
 * BddPartieJouer partie = new BddPartieJouer(1, "Louis", 300, listeTuiles);
 * }</pre>
 *
 * @see BddListeTuiles
 */
public class BddPartieJouer {

    private final int id;
    private final String playerName;
    private final int score;
    private final BddListeTuiles listeTuile;

    /**
     * Construit une instance de {@code BddPartieJouer} avec l'identifiant, le nom du joueur,
     * le score, et la liste de tuiles associée.
     *
     * @param id l'identifiant unique de la partie
     * @param playerName le nom du joueur ayant joué la partie
     * @param score le score obtenu dans la partie
     * @param listeTuile la liste de tuiles associée à cette partie
     */
    public BddPartieJouer(int id, String playerName, int score, BddListeTuiles listeTuile) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.listeTuile = listeTuile;
    }

    /**
     * Retourne l'identifiant unique de la partie.
     *
     * @return l'identifiant de la partie
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le nom du joueur ayant joué cette partie.
     *
     * @return le nom du joueur
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Retourne le score obtenu dans cette partie.
     *
     * @return le score de la partie
     */
    public int getScore() {
        return score;
    }

    /**
     * Retourne la liste de tuiles associée à cette partie.
     *
     * @return l'instance de {@link BddListeTuiles} associée à la partie
     */
    public BddListeTuiles getListeTuile() {
        return listeTuile;
    }
}