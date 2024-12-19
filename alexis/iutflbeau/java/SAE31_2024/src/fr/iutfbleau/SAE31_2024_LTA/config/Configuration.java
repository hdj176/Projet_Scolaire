package fr.iutfbleau.SAE31_2024_LTA.config;

/**
 * La classe {@code Configuration} représente les paramètres de configuration de l'application.
 * Elle permet de stocker et de gérer des options telles que le volume des effets sonores,
 * le volume de la musique, le nom du joueur, ainsi que des options d'affichage.
 * <p>
 * Les valeurs par défaut sont définies lors de l'initialisation de l'objet.
 * </p>
 */
public class Configuration {

    private int volumeEffet;
    private int volumeMusique;
    private String playerName;
    private boolean tuto;
    private boolean AA; // anti aliasing

    /**
     * Crée une nouvelle instance de {@code Configuration} avec des valeurs par défaut.
     * <ul>
     * <li>Volume des effets : 80</li>
     * <li>Volume de la musique : 80</li>
     * <li>Nom du joueur : "player"</li>
     * <li>Tutoriel : activé (true)</li>
     * <li>Anti-aliasing : désactivé (false)</li>
     * </ul>
     */
    public Configuration() {
        // valeurs par défaut
        this.volumeEffet = 80;
        this.volumeMusique = 80;
        this.playerName = "player";
        this.tuto = true;
        this.AA = false;
    }

    /**
     * Récupère le volume des effets sonores.
     *
     * @return le volume des effets sonores
     */
    public int getVolumeEffet() {
        return volumeEffet;
    }

    /**
     * Définit le volume des effets sonores.
     *
     * @param volumeGroup1 le nouveau volume des effets sonores
     */
    public void setVolumeEffet(int volumeGroup1) {
        this.volumeEffet = volumeGroup1;
    }

    /**
     * Récupère le volume de la musique.
     *
     * @return le volume de la musique
     */
    public int getVolumeMusique() {
        return volumeMusique;
    }

    /**
     * Définit le volume de la musique.
     *
     * @param volumeGroup2 le nouveau volume de la musique
     */
    public void setVolumeMusique(int volumeGroup2) {
        this.volumeMusique = volumeGroup2;
    }

    /**
     * Récupère le nom du joueur.
     *
     * @return le nom du joueur
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Définit le nom du joueur.
     *
     * @param playerName le nouveau nom du joueur
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Vérifie si le tutoriel est activé.
     *
     * @return {@code true} si le tutoriel est activé, {@code false} sinon
     */
    public boolean isTuto() {
        return tuto;
    }

    /**
     * Définit l'état d'affichage du tutoriel.
     *
     * @param showTutorialPopup {@code true} pour activer le tutoriel, {@code false} pour le désactiver
     */
    public void setTuto(boolean showTutorialPopup) {
        this.tuto = showTutorialPopup;
    }

    /**
     * Vérifie si l'anti-aliasing est activé.
     *
     * @return {@code true} si l'anti-aliasing est activé, {@code false} sinon
     */
    public boolean isAA() {
        return AA;
    }

    /**
     * Définit l'état de l'anti-aliasing.
     *
     * @param AA {@code true} pour activer l'anti-aliasing, {@code false} pour le désactiver
     */
    public void setAA(boolean AA) {
        this.AA = AA;
    }
}
