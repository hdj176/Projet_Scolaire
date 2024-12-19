package fr.iutfbleau.SAE31_2024_LTA.partieJouer;

import fr.iutfbleau.SAE31_2024_LTA.Bdd.BddPartieJouer;
import fr.iutfbleau.SAE31_2024_LTA.Bdd.PlayerNameFilter;
import fr.iutfbleau.SAE31_2024_LTA.Bdd.ScoreComparator;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Modèle pour la gestion des parties jouées.
 * Cette classe permet de récupérer et filtrer les données de parties jouées
 * et de créer la vue associée.
 */
public class ModelPartieJouer {
    private final ModelPrincipale modelPrincipale;

    private VuePartieJouer vuePartieJouer;


    /**
     * Constructeur du modèle de partie jouée.
     *
     * @param modelPrincipale le modèle principal de l'application.
     */
    public ModelPartieJouer(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
        createVue();
    }


    /**
     * Récupère toutes les parties jouées en les triant par score décroissant.
     *
     * @return une liste de toutes les parties jouées triées par score,
     * ou  null si la mise à jour de la base de données a échoué.
     */
    public List<BddPartieJouer> getAllParties() {
        if (modelPrincipale.getBdd().updateBdd()) {
            List<BddPartieJouer> listParties = modelPrincipale.getBdd().getAllPartieJouer().stream()
                    .sorted(new ScoreComparator())
                    .collect(Collectors.toList());
            return listParties;
        }
        return null;
    }

    /**
     * Récupère les parties jouées correspondant au nom de joueur spécifié,
     * triées par score décroissant.
     *
     * @param playerName le nom du joueur à filtrer.
     * @return une liste de parties filtrées et triées par score pour le joueur spécifié,
     * ou null si la mise à jour de la base de données a échoué.
     */
    public List<BddPartieJouer> getFilteredParties(String playerName) {
        if (modelPrincipale.getBdd().updateBdd()) {
            return modelPrincipale.getBdd().getAllPartieJouer().stream()
                    .filter(new PlayerNameFilter(playerName))
                    .sorted(new ScoreComparator())
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Crée la vue associée aux parties jouées et l'ajoute au panneau principal.
     */
    private void createVue(){
        vuePartieJouer = new VuePartieJouer(modelPrincipale, this);
        modelPrincipale.getVuePrincipale().getPrincipaleLayeredPane().getMainPanel().add(vuePartieJouer, "partieJouer");
    }


    /**
     * @return l'instance de {@link VuePartieJouer}.
     */
    public VuePartieJouer getVuePartieJouer() {
        return vuePartieJouer;
    }
}
