package fr.iutfbleau.SAE31_2024_LTA.jeux.controller;

import fr.iutfbleau.SAE31_2024_LTA.Bdd.BddListeTuiles;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;
import fr.iutfbleau.SAE31_2024_LTA.menu.VueMenu;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

/**
 * La classe {@code ControllerPlayCard} gère les actions liées à la carte de jeu.
 * Elle est responsable de la vérification de l'entrée du nom du joueur,
 * de la sélection de la suite, et de la préparation du jeu.
 */
public class ControllerPlayCard implements ActionListener {
    private final VuePrincipale vuePrincipale;
    private final List<BddListeTuiles> listeTuiles;
    private final ModelPrincipale modelPrincipale;

    /**
     * Constructeur de la classe {@code ControllerPlayCard}.
     *
     * @param modelPrincipale le modèle principal du jeu
     * @param listeTuiles    la liste des tuiles provenant de la base de données
     */
    public ControllerPlayCard(ModelPrincipale modelPrincipale, List<BddListeTuiles> listeTuiles) {
        this.modelPrincipale = modelPrincipale;
        this.vuePrincipale = modelPrincipale.getVuePrincipale();
        this.listeTuiles = listeTuiles;
    }

    /**
     * Gère l'événement d'action lorsque le joueur interagit avec la carte de jeu.
     * Vérifie si un nom de joueur est fourni, valide la sélection de la suite,
     * génère une graine pour le jeu, et commence le jeu en affichant
     * la vue correspondante.
     *
     * @param e l'événement d'action déclenché
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        VueMenu vueMenu = modelPrincipale.getModelMenu().getVueMenu();
        String playerName = modelPrincipale.getModelMenu().getVueMenu().getPlayerNameInput().getText();
        modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getClicAudioClip(), false);

        if (playerName.isEmpty() || playerName.equals("Player Name...")) {
            JOptionPane.showMessageDialog(vueMenu, "Veuillez entrer un nom de joueur !");
        } else {
            int selectedIndex = vueMenu.getSuiteSelector().getSelectedIndex();

            if (selectedIndex == 0) {
                JOptionPane.showMessageDialog(vueMenu, "Veuillez choisir une suite");
            } else {
                int seed;
                if (listeTuiles != null) {
                    if (selectedIndex == 1) {
                        Random rand = new Random();
                        seed = rand.nextInt();
                        modelPrincipale.setSeedIndex(-1);
                    } else {
                        seed = listeTuiles.get(selectedIndex - 1).getSeed(); // liste de tuiles de la BDD
                        modelPrincipale.setSeedIndex(selectedIndex);
                    }
                } else {
                    Random rand = new Random();
                    seed = rand.nextInt();
                    modelPrincipale.setSeedIndex(-1);
                }

                modelPrincipale.setPlayerName(playerName);
                modelPrincipale.getConfigManager().setPlayerName(playerName);
                modelPrincipale.setSelectedSeed(seed);

                modelPrincipale.getMediaPlayerManager().stopClip(modelPrincipale.getModelMediaLoader().getMenuMusicClip());
                modelPrincipale.getMediaPlayerManager().startClip(modelPrincipale.getModelMediaLoader().getGameMusicClips(), 0);

                modelPrincipale.createJeux();
                vuePrincipale.getPrincipaleLayeredPane().getMainPanel().getCardLayout().show(vuePrincipale.getPrincipaleLayeredPane().getMainPanel(), "jeux");
            }
        }
        vuePrincipale.setTitle("DorfJavatik - Jeux en cours...  0 points");
    }
}
