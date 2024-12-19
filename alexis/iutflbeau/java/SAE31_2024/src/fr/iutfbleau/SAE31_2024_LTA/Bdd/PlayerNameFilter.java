package fr.iutfbleau.SAE31_2024_LTA.Bdd;

import java.util.function.Predicate;

/**
 * Classe {@code PlayerNameFilter} implémente {@link Predicate} pour filtrer les parties jouées
 * en fonction du nom du joueur spécifié.
 *
 * <p>Cette classe permet de créer un prédicat qui peut être utilisé pour tester si une instance
 * de {@link BddPartieJouer} correspond à un nom de joueur donné, en effectuant une comparaison
 * insensible à la casse.
 *
 * <p>Exemple d'utilisation :
 * <pre>{@code
 * List<BddPartieJouer> partiesFiltrees = listeParties.stream()
 *         .filter(new PlayerNameFilter("NomJoueur"))
 *         .collect(Collectors.toList());
 * }</pre>
 *
 * @see java.util.function.Predicate
 * @see BddPartieJouer
 */
public class PlayerNameFilter implements Predicate<BddPartieJouer> {

    /**
     * Le nom du joueur à rechercher.
     */
    private final String playerName;

    /**
     * Construit un filtre de nom de joueur basé sur le nom spécifié.
     *
     * @param playerName le nom du joueur pour lequel filtrer les parties
     */
    public PlayerNameFilter(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Teste si la partie fournie correspond au nom du joueur.
     *
     * @param partie l'instance de {@link BddPartieJouer} à tester
     * @return {@code true} si le nom du joueur dans la partie correspond au nom recherché (sans casse),
     *         {@code false} sinon
     */
    @Override
    public boolean test(BddPartieJouer partie) {
        return partie.getPlayerName().equalsIgnoreCase(playerName);
    }
}