package fr.iutfbleau.SAE31_2024_LTA;

import fr.iutfbleau.SAE31_2024_LTA.Bdd.ModelBDD;
import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;
import fr.iutfbleau.SAE31_2024_LTA.jeux.model.ModelJeux;
import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;
import fr.iutfbleau.SAE31_2024_LTA.media.MediaPlayerManager;
import fr.iutfbleau.SAE31_2024_LTA.media.ModelMediaLoader;
import fr.iutfbleau.SAE31_2024_LTA.menu.ModelMenu;
import fr.iutfbleau.SAE31_2024_LTA.partieJouer.ModelPartieJouer;
import fr.iutfbleau.SAE31_2024_LTA.popup.ControllerInputMap;
import fr.iutfbleau.SAE31_2024_LTA.popup.ControllerPopup;

/**
 * La classe ModelPrincipale gère le modèle principal de l'application,
 * coordonnant différents composants tels que les gestionnaires de médias,
 * la gestion de la base de données, et les interactions avec l'interface utilisateur.
 * Elle est responsable de la création des instances de jeu, de menu, et de la
 * gestion des paramètres de configuration.
 */
public class ModelPrincipale {
    private final ConfigManager configManager;
    private final MediaPlayerManager mediaPlayerManager;
    private final ModelMediaLoader modelMediaLoader;

    private final ModelBDD bdd;

    private ModelPartieJouer modelPartieJouer;
    private final ModelMenu modelMenu;
    private ModelJeux modelJeux;

    private final VuePrincipale vuePrincipale;

    private String playerName;
    private int selectedSeed;
    private int seedIndex;

    private final ControllerPopup controllerPopup;
    private final ControllerInputMap controllerInputMap;

    /**
     * Constructeur de la classe ModelPrincipale.
     * Initialise tous les composants nécessaires et crée la vue principale.
     */
    public ModelPrincipale() {
        vuePrincipale = createView();
        mediaPlayerManager = new MediaPlayerManager(this);
        controllerPopup = new ControllerPopup(vuePrincipale);
        configManager = new ConfigManager(controllerPopup);

        modelMediaLoader = new ModelMediaLoader();

        controllerInputMap = new ControllerInputMap(this, "main");
        controllerPopup.createSettings();
        bdd = new ModelBDD(getVuePrincipale());
        modelMenu = new ModelMenu(this);
        vuePrincipale.setVisible(true);
    }

    /**
     * Crée une nouvelle partie jouée.
     */
    public void createPartieJouer() {
        modelPartieJouer = new ModelPartieJouer(this);
    }

    /**
     * Crée et retourne la vue principale de l'application.
     *
     * @return La vue principale (VuePrincipale).
     */
    private VuePrincipale createView() {
        return new VuePrincipale(this);
    }

    /**
     * Retourne la vue principale de l'application.
     *
     * @return La vue principale (VuePrincipale).
     */
    public VuePrincipale getVuePrincipale() {
        return vuePrincipale;
    }

    /**
     * Retourne le modèle de la base de données.
     *
     * @return Le modèle de la base de données (ModelBDD).
     */
    public ModelBDD getBdd() {
        return this.bdd;
    }

    /**
     * Retourne le modèle de menu de l'application.
     *
     * @return Le modèle de menu (ModelMenu).
     */
    public ModelMenu getModelMenu() {
        return modelMenu;
    }

    /**
     * Retourne le modèle de jeux de l'application.
     *
     * @return Le modèle de jeux (ModelJeux).
     */
    public ModelJeux getModelJeux() {
        return modelJeux;
    }

    /**
     * Crée une nouvelle instance de jeu avec la graine sélectionnée.
     */
    public void createJeux() {
        this.modelJeux = new ModelJeux(this, selectedSeed);
    }

    /**
     * Retourne la graine sélectionnée pour la génération de jeux.
     *
     * @return La graine sélectionnée (int).
     */
    public int getSelectedSeed() {
        return selectedSeed;
    }

    /**
     * Définit la graine sélectionnée pour la génération de jeux.
     *
     * @param seed La graine à définir (int).
     */
    public void setSelectedSeed(int seed) {
        selectedSeed = seed;
    }

    /**
     * Définit le nom du joueur.
     *
     * @param playerName Le nom du joueur à définir (String).
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Retourne le modèle de partie jouée.
     *
     * @return Le modèle de partie jouée (ModelPartieJouer).
     */
    public ModelPartieJouer getModelPartieJouer() {
        return modelPartieJouer;
    }

    /**
     * Retourne le gestionnaire de médias.
     *
     * @return Le gestionnaire de médias (MediaPlayerManager).
     */
    public MediaPlayerManager getMediaPlayerManager() {
        return mediaPlayerManager;
    }

    /**
     * Retourne le chargeur de médias.
     *
     * @return Le chargeur de médias (ModelMediaLoader).
     */
    public ModelMediaLoader getModelMediaLoader() {
        return modelMediaLoader;
    }

    /**
     * Retourne le nom du joueur.
     *
     * @return Le nom du joueur (String).
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Définit l'index de la graine.
     *
     * @param index L'index à définir (int).
     */
    public void setSeedIndex(int index) {
        this.seedIndex = index;
    }

    /**
     * Retourne l'index de la graine.
     *
     * @return L'index de la graine (int).
     */
    public int getSeedIndex() {
        return this.seedIndex;
    }

    /**
     * Retourne le gestionnaire de configuration.
     *
     * @return Le gestionnaire de configuration (ConfigManager).
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * Retourne le contrôleur de popup.
     *
     * @return Le contrôleur de popup (ControllerPopup).
     */
    public ControllerPopup getControllerPopup() {
        return controllerPopup;
    }

    /**
     * Retourne le contrôleur de la carte d'entrée.
     *
     * @return Le contrôleur de la carte d'entrée (ControllerInputMap).
     */
    public ControllerInputMap getControllerInputMap() {
        return controllerInputMap;
    }
}
