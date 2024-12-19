package fr.iutfbleau.SAE31_2024_LTA.partieJouer;

import fr.iutfbleau.SAE31_2024_LTA.Bdd.BddPartieJouer;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controleur pour la recherche et la gestion des parties jouées.
 * Cette classe permet de gérer les actions de recherche et de réinitialisation des parties jouées,
 * ainsi que de rechercher le score maximal d'un joueur pour un seed spécifique.
 */
public class ControllerSearchPartieJouer implements ActionListener {

    private final ModelPrincipale modelPrincipale;
    private String search;

    /**
     * Constructeur de ControllerSearchPartieJouer.
     *
     * @param modelPrincipale le modèle principal de l'application.
     */
    public ControllerSearchPartieJouer(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
    }

    /**
     * Gère l'action de recherche lorsque l'utilisateur clique sur le bouton de recherche.
     * Joue un son de clic, vérifie le champ de recherche et effectue une recherche ou réinitialise les parties.
     *
     * @param actionEvent l'événement de l'action déclenchée.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.search = modelPrincipale.getModelPartieJouer().getVuePartieJouer().getSearchField().getText();

        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClicAudioClip(), false);

        if (search.isEmpty() || search.equals("Entrer le nom du joueur")) {
            resetPartie();
        } else {
            searchPartie();
        }
    }

    /**
     * Rest l'affichage des parties jouées,
     * Met à jour la base de données et affiche toutes les parties dans le tableau.
     */
    private void resetPartie() {
        if (modelPrincipale.getBdd().updateBdd()) {
            List<BddPartieJouer> allParties = modelPrincipale.getModelPartieJouer().getAllParties();

            modelPrincipale.getModelPartieJouer().getVuePartieJouer().getTableModel().setRowCount(0);
            for (BddPartieJouer partie : allParties) {
                modelPrincipale.getModelPartieJouer().getVuePartieJouer().getTableModel().addRow(new Object[]{partie.getPlayerName(), partie.getListeTuile().getId(), partie.getScore()});
            }

        }
    }

    /**
     * Effectue une recherche des parties jouées en fonction du nom de joueur entré dans le champ de recherche.
     * Filtre les parties en fonction du nom et les affiche dans la table.
     */
    private void searchPartie() {
        if (modelPrincipale.getBdd().updateBdd()) {
            List<BddPartieJouer> filteredParties = modelPrincipale.getModelPartieJouer().getVuePartieJouer().getModelPartieJouer().getFilteredParties(search);

            modelPrincipale.getModelPartieJouer().getVuePartieJouer().getTableModel().setRowCount(0);
            for (BddPartieJouer partie : filteredParties) {
                modelPrincipale.getModelPartieJouer().getVuePartieJouer().getTableModel().addRow(new Object[]{partie.getPlayerName(), partie.getListeTuile().getId(), partie.getScore()});
            }
        }
    }

    /**
     * Recherche le score maximum d'un joueur pour une partie spécifique définie par un seed.
     *
     * @param playerName le nom du joueur.
     * @param seed le seed de la partie.
     * @return le score maximum du joueur pour le seed donné, ou 0 si aucun score n'est trouvé.
     */
    public int searchPartieOfPlayer(String playerName, int seed) {
        if (modelPrincipale.getBdd().updateBdd()) {
            List<BddPartieJouer> filteredParties = modelPrincipale.getModelPartieJouer().getVuePartieJouer().getModelPartieJouer().getFilteredParties(playerName);
            int score = 0;
            for (BddPartieJouer partie : filteredParties) {
                if (partie.getListeTuile().getSeed() == seed) {
                    if (partie.getScore() > score) {
                        score = partie.getScore();
                    }
                }
            }
            return score;
        }

        return 0;
    }
}
