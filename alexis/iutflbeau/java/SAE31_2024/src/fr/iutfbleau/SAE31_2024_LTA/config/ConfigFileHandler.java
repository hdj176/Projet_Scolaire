package fr.iutfbleau.SAE31_2024_LTA.config;

import java.io.*;
import java.util.Properties;

/**
 * La classe {@code ConfigFileHandler} gère la sauvegarde et le chargement des paramètres de configuration
 * de l'application dans un fichier de propriétés.
 * <p>
 * Exemple d'utilisation :
 * <pre>{@code
 * ConfigFileHandler configHandler = new ConfigFileHandler();
 * Configuration config = new Configuration();
 * configHandler.saveConfiguration(config);
 * Configuration loadedConfig = configHandler.loadConfiguration();
 * }</pre>
 */
public class ConfigFileHandler {

    /**
     * Chemin du fichier de configuration utilisé pour stocker les paramètres de l'application.
     */
    private static final String CONFIG_FILE_PATH = "configDorfJavatik.properties";

    /**
     * Sauvegarde la configuration fournie dans un fichier de propriétés.
     *
     * @param configuration l'instance de {@code Configuration} contenant les paramètres à sauvegarder
     */
    public void saveConfiguration(Configuration configuration) {
        Properties properties = new Properties();

        properties.setProperty("volumeEffet", Integer.toString(configuration.getVolumeEffet()));
        properties.setProperty("volumeMusique", Integer.toString(configuration.getVolumeMusique()));
        properties.setProperty("playerName", configuration.getPlayerName());
        properties.setProperty("tuto", Boolean.toString(configuration.isTuto()));
        properties.setProperty("AntiAliasing", Boolean.toString(configuration.isAA()));

        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(output, "Configuration Settings by Louis for SAE31_2024_LTA");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement de la configuration : " + e.getMessage());
        }
    }

    /**
     * Charge la configuration depuis un fichier de propriétés.
     * Si le fichier n'existe pas ou qu'il y a une erreur de lecture,
     * des valeurs par défaut sont appliquées à l'instance {@code Configuration} retournée.
     *
     * @return une instance de {@code Configuration} contenant les paramètres chargés
     */
    public Configuration loadConfiguration() {
        Properties properties = new Properties();
        Configuration config = new Configuration();

        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);

            // Lecture des paramètres de configuration
            config.setVolumeEffet(Integer.parseInt(properties.getProperty("volumeEffet", "80")));
            config.setVolumeMusique(Integer.parseInt(properties.getProperty("volumeMusique", "80")));
            config.setPlayerName(properties.getProperty("playerName", "Player Name..."));
            config.setTuto(Boolean.parseBoolean(properties.getProperty("tuto", "true")));
            config.setAA(Boolean.parseBoolean(properties.getProperty("AntiAliasing", "false")));

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la configuration : " + e.getMessage());
        }

        return config;
    }
}

