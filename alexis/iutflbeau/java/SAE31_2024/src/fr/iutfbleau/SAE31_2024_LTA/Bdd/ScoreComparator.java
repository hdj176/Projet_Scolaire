package fr.iutfbleau.SAE31_2024_LTA.Bdd;

import java.util.Comparator;

/**
 * Classe {@code ScoreComparator} implémente {@link Comparator} pour comparer des instances de {@link BddPartieJouer}
 * en fonction de leur score.
 *
 * <p>Cette classe compare deux parties jouées selon un ordre décroissant de score, permettant ainsi
 * de trier les scores du plus élevé au plus bas.
 *
 * <p>Exemple d'utilisation :
 * <pre>{@code
 * List<BddPartieJouer> partiesTriees = listeParties.stream()
 *         .sorted(new ScoreComparator())
 *         .collect(Collectors.toList());
 * }</pre>
 *
 * @see java.util.Comparator
 * @see BddPartieJouer
 */
public class ScoreComparator implements Comparator<BddPartieJouer> {

    /**
     * Compare deux instances de {@link BddPartieJouer} en fonction de leur score.
     *
     * @param p1 la première instance de {@code BddPartieJouer}
     * @param p2 la deuxième instance de {@code BddPartieJouer}
     * @return un nombre négatif si le score de {@code p2} est supérieur à celui de {@code p1},
     *         zéro si les scores sont égaux, ou un nombre positif si le score de {@code p1} est supérieur
     */
    @Override
    public int compare(BddPartieJouer p1, BddPartieJouer p2) {
        return Integer.compare(p2.getScore(), p1.getScore());
    }
}
