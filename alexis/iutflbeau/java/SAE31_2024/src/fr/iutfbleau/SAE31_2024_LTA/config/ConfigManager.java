package fr.iutfbleau.SAE31_2024_LTA.config;

import fr.iutfbleau.SAE31_2024_LTA.popup.ControllerPopup;

/**
 * La classe {@code ConfigManager} gère la configuration de l'application,
 * en utilisant un {@code ConfigFileHandler} pour charger et sauvegarder les paramètres
 * dans un fichier de configuration.
 * <p>
 * Cette classe facilite l'accès et la modification des paramètres de configuration tels que
 * le volume des effets sonores, le volume de la musique, le nom du joueur, et d'autres
 * options de configuration.
 * </p>
 */
public class ConfigManager {

    private final Configuration configuration;
    private final ConfigFileHandler fileHandler;

    /**
     * Crée une instance de {@code ConfigManager} et charge la configuration à partir du fichier.
     *
     * @param controllerPopup le contrôleur pour gérer les pop-ups de l'application
     */
    public ConfigManager(ControllerPopup controllerPopup) {
        this.fileHandler = new ConfigFileHandler();
        this.configuration = fileHandler.loadConfiguration();
    }

    /**
     * Récupère le volume des effets sonores.
     *
     * @return le volume des effets sonores
     */
    public int getVolumeEffet() {
        return configuration.getVolumeEffet();
    }

    /**
     * Définit le volume des effets sonores et sauvegarde la configuration.
     *
     * @param volume le nouveau volume des effets sonores
     */
    public void setVolumeEffet(int volume) {
        configuration.setVolumeEffet(volume);
        fileHandler.saveConfiguration(configuration);
    }

    /**
     * Récupère le volume de la musique.
     *
     * @return le volume de la musique
     */
    public int getVolumeMusique() {
        return configuration.getVolumeMusique();
    }

    /**
     * Définit le volume de la musique et sauvegarde la configuration.
     *
     * @param volume le nouveau volume de la musique
     */
    public void setVolumeMusique(int volume) {
        configuration.setVolumeMusique(volume);
        fileHandler.saveConfiguration(configuration);
    }

    /**
     * Récupère le nom du joueur.
     *
     * @return le nom du joueur
     */
    public String getPlayerName() {
        return configuration.getPlayerName();
    }

    /**
     * Définit le nom du joueur et sauvegarde la configuration.
     *
     * @param userName le nouveau nom du joueur
     */
    public void setPlayerName(String userName) {
        configuration.setPlayerName(userName);
        fileHandler.saveConfiguration(configuration);
    }

    /**
     * Vérifie si le tutoriel est activé.
     *
     * @return {@code true} si le tutoriel est activé, {@code false} sinon
     */
    public boolean isTuto() {
        return configuration.isTuto();
    }

    /**
     * Définit l'état d'affichage du tutoriel et sauvegarde la configuration.
     *
     * @param show {@code true} pour activer le tutoriel, {@code false} pour le désactiver
     */
    public void setTuto(boolean show) {
        configuration.setTuto(show);
        fileHandler.saveConfiguration(configuration);
    }

    /**
     * Vérifie si l'anti-aliasing est activé.
     *
     * @return {@code true} si l'anti-aliasing est activé, {@code false} sinon
     */
    public boolean isAA() {
        return configuration.isAA();
    }

    /**
     * Définit l'état de l'anti-aliasing.
     *
     * @param isActivate {@code true} pour activer l'anti-aliasing, {@code false} pour le désactiver
     */
    public void setAA(boolean isActivate) {
        configuration.setAA(isActivate);
        fileHandler.saveConfiguration(configuration);
    }
}