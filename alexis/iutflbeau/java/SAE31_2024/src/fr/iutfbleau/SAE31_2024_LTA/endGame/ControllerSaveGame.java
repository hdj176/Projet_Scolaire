package fr.iutfbleau.SAE31_2024_LTA.endGame;

import fr.iutfbleau.SAE31_2024_LTA.Bdd.ModelBDD;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * La classe {@code ControllerSaveGame} gère la logique de sauvegarde du jeu.
 * Elle permet de sauvegarder le score du joueur dans la base de données
 * lorsqu'un événement d'action est déclenché (par exemple, un clic sur un bouton).
 */
public class ControllerSaveGame implements ActionListener {
    private final ModelBDD modelBDD;
    private final ModelPrincipale modelPrincipale;
    private final VueScoreScreen vueScoreScreen;
    boolean saved = false;

    /**
     * Crée une nouvelle instance de {@code ControllerSaveGame}.
     *
     * @param modelPrincipale le modèle principal du jeu
     * @param vueScoreScreen  l'interface graphique de l'écran des scores
     */
    public ControllerSaveGame(ModelPrincipale modelPrincipale, VueScoreScreen vueScoreScreen) {
        this.vueScoreScreen = vueScoreScreen;
        this.modelBDD = modelPrincipale.getBdd();
        this.modelPrincipale = modelPrincipale;
        try {
            saveInBdd();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Méthode appelée lorsque l'utilisateur interagit avec le bouton de sauvegarde.
     *
     * @param e l'événement d'action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            saveInBdd();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Sauvegarde le score du joueur dans la base de données.
     *
     * @throws SQLException si une erreur survient lors de l'accès à la base de données
     */
    public void saveInBdd() throws SQLException {
        // Vérifie si le jeu a déjà été sauvegardé
        if (saved) {
            return;
        }

        // Tente de sauvegarder le jeu dans la base de données
        if (modelBDD.saveGame(modelPrincipale.getPlayerName(), modelPrincipale.getModelJeux().getScore(), modelPrincipale.getSeedIndex() - 1)) {
            saved = true;
            vueScoreScreen.getSaveBddButton().setText("Sauvegarder !");
            vueScoreScreen.getSaveBddButton().setEnabled(false);
            vueScoreScreen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            return;
        }

        // Si la sauvegarde échoue, configure le bouton pour réessayer
        vueScoreScreen.getSaveBddButton().setText("Re-essayer");
        vueScoreScreen.getSaveBddButton().setEnabled(true);
        vueScoreScreen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        vueScoreScreen.getSaveBddButton().addActionListener(this);
    }
}